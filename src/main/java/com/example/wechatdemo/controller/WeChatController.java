package com.example.wechatdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.wechatdemo.async.AsyncService;
import com.example.wechatdemo.constant.BaseConstant;
import com.example.wechatdemo.constant.enumConstant.EventType;
import com.example.wechatdemo.constant.enumConstant.MsgType;
import com.example.wechatdemo.data.test.service.TestService;
import com.example.wechatdemo.model.MsgIn;
import com.example.wechatdemo.model.MsgOut;
import com.example.wechatdemo.model.Signature;
import com.example.wechatdemo.model.message.ImageMsgOut;
import com.example.wechatdemo.model.message.NewsItem;
import com.example.wechatdemo.model.message.NewsMsgOut;
import com.example.wechatdemo.model.message.TextMsgOut;
import com.example.wechatdemo.util.HttpUtil;
import com.example.wechatdemo.util.SHA1Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class WeChatController {

    Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @Value("${token}")
    private String token;

    @Autowired
    private TestService testService;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private HttpUtil httpUtil;
    /**
     * 微信接口测试验证(为GET请求)
     * @param signature
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wechat",method = RequestMethod.GET)
    @ResponseBody
    public String weChatValidate(Signature signature) throws Exception{

        //对token,timestamp,nonce排序后拼接
        String arrData = Stream.of(this.token, signature.getTimestamp(), signature.getNonce()).sorted().collect(Collectors.joining());

        //获取生成签名
        String sgin = SHA1Util.getSignForSHA1(arrData);

        if (sgin.equalsIgnoreCase(signature.getSignature())) {
            return signature.getEchostr();
        }else{
            return null;
        }
    }

    /**
     * 微信消息处理(为POST请求)
     * @param msgIn
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/wechat",method = RequestMethod.POST,produces = MediaType.APPLICATION_XML_VALUE)
    public MsgOut weChatMessage(@RequestBody MsgIn msgIn) throws Exception {

        //得到用户传入的msgType
        MsgType msgType = Stream.of(MsgType.values()).filter(m -> m.getCode().equals(msgIn.getMsgType())).findFirst()
                .map(curMsgType -> curMsgType).orElseGet(() -> null);

        Optional.ofNullable(msgType).ifPresent(System.out::println);

        //如果用户传入的msgType为可用的消息类型，则进行消息处理，否则不处理
        MsgOut msgOut = Stream.of(BaseConstant.MSGTYPE_ENABLED).filter(m -> msgType !=null && msgType.getCode().equals(m))
                .findFirst().map(curMsgTypeCode -> {
                    try {
                        return (MsgOut) this.getClass().getDeclaredMethod("handler_" + curMsgTypeCode, MsgIn.class).invoke(this, msgIn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new MsgOut();
                }).orElseGet(() -> null);

        return msgOut;
    }

    //处理事件推送
    private MsgOut handler_event(MsgIn msgIn){
        //得到用户传入的msgType
        EventType eventType = Stream.of(EventType.values()).filter(m -> m.getCode().equals(msgIn.getEvent())).findFirst()
                .map(curMsgType -> curMsgType).orElseGet(() -> null);

        MsgOut msgOut = Stream.of(BaseConstant.EVENT_ENABLED).filter(m -> eventType !=null && eventType.getCode().equals(m))
                .findFirst().map(curMsgTypeCode -> {
                    try {
                        return (MsgOut) this.getClass().getDeclaredMethod("handler_event_" + curMsgTypeCode, MsgIn.class).invoke(this, msgIn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new MsgOut();
                }).orElseGet(() -> null);

        return msgOut;
    }

    //处理文本消息
    private MsgOut handler_text(MsgIn msgIn){
        TextMsgOut textMsgOut = new TextMsgOut();

        //如果发送消息中带模板关键字，则回复模板消息
        if (msgIn.getContent().contains("模板")) {
            String openId = msgIn.getFromUserName();
            //如果是模板消息，异步处理
            taskExecutor.execute(() -> {
                try {
                    JSONObject jsonObject = httpUtil.sendTempleteMsg(openId);
                    logger.info(jsonObject.toJSONString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            textMsgOut = null;
        }else{
            textMsgOut.setFromUserName(msgIn.getToUserName());
            textMsgOut.setToUserName(msgIn.getFromUserName());
            textMsgOut.setCreateTime(System.currentTimeMillis());
            textMsgOut.setMsgType(msgIn.getMsgType());

            textMsgOut.setContent("hello[微笑]:" + msgIn.getContent());
            System.out.println("处理文本消息");
        }

        return textMsgOut;
    }

    //处理图片消息
    private MsgOut handler_image(MsgIn msgIn){
        ImageMsgOut imageMsgOut = new ImageMsgOut();

        imageMsgOut.setFromUserName(msgIn.getToUserName());
        imageMsgOut.setToUserName(msgIn.getFromUserName());
        imageMsgOut.setCreateTime(System.currentTimeMillis());
        imageMsgOut.setMsgType(msgIn.getMsgType());
        //原样返回图片
        imageMsgOut.setMediaId(new String[]{msgIn.getMediaId()});
        System.out.println("处理图片消息");
        return imageMsgOut;
    }

    //处理订阅事件推送
    private MsgOut handler_event_subscribe(MsgIn msgIn){
        NewsMsgOut newsMsgOut = new NewsMsgOut();

        newsMsgOut.setFromUserName(msgIn.getToUserName());
        newsMsgOut.setToUserName(msgIn.getFromUserName());
        newsMsgOut.setCreateTime(System.currentTimeMillis());
        newsMsgOut.setMsgType(MsgType.news.getCode());

        newsMsgOut.setArticleCount("2");

        NewsItem newsItem = new NewsItem();
        newsItem.setDescription("这是我的文章，仅用于测试");
//        newsItem.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548828499305&di=6a3db7cd80001050f314c213689e1656&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2F331fd2a2c0759abfae396b55d4fc1658939641cc.jpg");
        newsItem.setPicUrl("http://b197.photo.store.qq.com/psb?/V13aCDZF2zg2ID/PdK1yP*AgwOdAd2TUvSyKokD9bkfcIU3H3iIl0FcV*k!/b/dO70cXWTNAAA&bo=vwOAAgAAAAABBx4!&rf=viewer_4&t=5");
        newsItem.setTitle("测试一个文章");
        newsItem.setUrl("https://new.qq.com/cmsn/20190124/20190124004474.html");

        NewsItem newsItem1 = new NewsItem();
        newsItem1.setDescription("这是我的第2个文章");
        newsItem1.setPicUrl("https://upload.jianshu.io/admin_banners/web_images/4601/3f4d6684a208a27d92ea7b5b9759c9dc5049d4c0.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/1250/h/540");
        newsItem1.setTitle("测试二个文章");
        newsItem1.setUrl("https://new.qq.com/cmsn/20190124/20190124004474.html");

        newsMsgOut.setItem(new NewsItem[]{newsItem,newsItem1});

        return newsMsgOut;
    }



//----------------------以下为测试区域-----------------------

    @ResponseBody
    @RequestMapping(value = "receiveMessage",method = RequestMethod.POST)
    public String receiveMessage(@RequestBody MsgIn messageIn){
        System.out.println(messageIn);


        return "hello";
    }


    //    @Cacheable(value = "test", key = "'user_'.concat(#root.args[0])")
    @Cacheable(value = "test", key = "'user_'.concat(#root.args[0])")
    public String getStr(String userId){
        System.out.println("得到userId");
        return "hello:"+userId;
    }


    @ResponseBody
    @RequestMapping("/webTest")
    public String webTest(@RequestParam String userId){

        if (!CollectionUtils.isEmpty(cacheManager.getCacheNames())) {
            System.out.println(cacheManager.getCacheNames().iterator().next());

            ((Map)cacheManager.getCache("test").getNativeCache()).forEach((k,v) -> {
                System.out.println("缓存值：" + k + ":" + v);
            });

        }
        String name = testService.getUserName(userId);

        return name;
//        return "";
    }

    @ResponseBody
    @RequestMapping("/urlTest")
    public String urlTest(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String result = restTemplate.exchange("http://www.weather.com.cn/data/cityinfo/101200101.html", HttpMethod.GET, entity, String.class).getBody();

        return result;
    }

    @ResponseBody
    @RequestMapping("/redisTest")
    public String redisTest(){
//        UserInfo user = new UserInfo("1","张三");
//        redisTemplate.opsForValue().set("u",user);
//
//
//        UserInfo u = (UserInfo) redisTemplate.opsForValue().get("u");
//        System.out.println(u);

//        Map<String,String> maps = new HashMap<String, String>();
//        maps.put("multi1","multi1");
//        maps.put("multi2","multi2");
//        maps.put("multi3","multi3");
//        redisTemplate.opsForValue().multiSet(maps);
//        List<String> keys = new ArrayList<String>();
//        keys.add("multi1");
//        keys.add("multi2");
//        keys.add("multi3");
//        System.out.println(redisTemplate.opsForValue().multiGet(keys));

        System.out.println(token+"...");

        return "hello";
    }


    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String result = restTemplate.exchange("https://www.hao123.com/", HttpMethod.GET, entity, String.class).getBody();
        System.out.println(result);
    }



    @ResponseBody
    @RequestMapping("/webXml")
    public MsgOut webXml(){
        MsgOut messageOut = new MsgOut();
        messageOut.setFromUserName("aaa");
        messageOut.setToUserName("bbb");
        System.out.println(messageOut);
        return messageOut;
    }

    @ResponseBody
    @RequestMapping(value = "/task")
    public String task(){
        System.out.println(Thread.currentThread().getName() + ":" + "进方法1");

        taskExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + "进方法2");
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":" + "出方法2");
        });

        asyncService.taskMethod();

        System.out.println(Thread.currentThread().getName() + ":" + "出方法1");
        return "task test";
    }

}

