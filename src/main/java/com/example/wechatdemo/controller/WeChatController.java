package com.example.wechatdemo.controller;

import com.example.wechatdemo.constant.BaseConstant;
import com.example.wechatdemo.constant.enumConstant.EventType;
import com.example.wechatdemo.constant.enumConstant.MsgType;
import com.example.wechatdemo.model.*;
import com.example.wechatdemo.model.message.ImageMsgOut;
import com.example.wechatdemo.model.message.NewsItem;
import com.example.wechatdemo.model.message.NewsMsgOut;
import com.example.wechatdemo.model.message.TextMsgOut;
import com.example.wechatdemo.util.SHA1Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@PropertySource("classpath:profile.properties")
public class WeChatController {

    @Value("${token}")
    private String token;

    /**
     * 微信接口测试验证(为GET请求)
     * @param signature
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "wechat",method = RequestMethod.GET)
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
    @RequestMapping(value = "wechat",method = RequestMethod.POST,produces = MediaType.APPLICATION_XML_VALUE)
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

        textMsgOut.setFromUserName(msgIn.getToUserName());
        textMsgOut.setToUserName(msgIn.getFromUserName());
        textMsgOut.setCreateTime(System.currentTimeMillis());
        textMsgOut.setMsgType(msgIn.getMsgType());

        textMsgOut.setContent("hello[微笑]:" + msgIn.getContent());
        System.out.println("处理文本消息");
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
        newsItem.setPicUrl("https://upload-images.jianshu.io/upload_images/14715425-a69dcd608265e3e4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/833/format/webp");
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



    @ResponseBody
    @RequestMapping(value = "receiveMessage",method = RequestMethod.POST)
    public String receiveMessage(@RequestBody MsgIn messageIn){
        System.out.println(messageIn);
        return "hello";
    }

    @ResponseBody
    @RequestMapping("/webTest")
    public String webTest(){
        return "hello world";
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

}
