package com.example.wechatdemo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by Lee on 2019-01-25.
 *
 * @author Lee
 */
@Component
public class HttpUtil {
    Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    //获取access_token接口
    private final static String access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //生成menu接口
    private final static String menu_create = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    //删除menu接口
    private final static String menu_delete = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    //发送模板消息
    private final static String send_templete_msg = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //网页端请求的access_token接口
    private final static String web_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    //获取用户信息
    private final static String sasapi_userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    //JS-SDK票据接口，用于生成分享页面的签名
    private final static String jsapi_ticket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    protected StringRedisTemplate redisTemplate;

    @Value("${redis.domain}")
    private String redisDomain;    //获取本项目在redis中的域

    /**
     * 从redis中获取配置
     * @return
     */
    public String getWechatConfig(){
        return redisTemplate.opsForValue().get(redisDomain+":appConfig");
    }

    /**
     * 获取后端该问接口accessToken
     * @return
     */
    public String getAccessToken() throws Exception{

        Object accessToken = redisTemplate.opsForValue().get(redisDomain+":accessToken");

        //如果redis中不存在accessToken，重新获取accessToken并保存，否则直接返回
        if (accessToken == null) {
            Object appConfig = getWechatConfig();
            if(appConfig == null){
                throw new Exception("请检查redis中是否配置了appConfig");
            }

            JSONObject json = JSONObject.parseObject(appConfig.toString());

            String result = restTemplate.getForEntity(access_token.replace("APPID", json.getString("appID")).replace("APPSECRET", json.getString("appsecret")), String.class).getBody();

            logger.info("重新查询accessToken结果：" + result);
            JSONObject jsonObject = JSON.parseObject(result);

            Long expire = jsonObject.getLong("expires_in") - 2000;

            //返回值存入redis
            redisTemplate.opsForValue().set(redisDomain+":accessToken",result,expire, TimeUnit.SECONDS);

            return jsonObject.getString("access_token");
        }

        return JSON.parseObject(accessToken.toString()).getString("access_token");
    }

    /**
     * 获取JSON文件
     * @return
     * @throws Exception
     */
    private String getFileForJson(String fileName)throws Exception{
        Resource resource = ApplicationUtil.getApplicationContext().getResource("classpath:" + fileName);
        String content = "";

        try (ResourceUtil resourceUtil = new ResourceUtil(resource)){
            content = resourceUtil.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * 创建菜单
     * @return
     * @throws Exception
     */
    public JSONObject menuCreate() throws Exception{

        String json = restTemplate.postForEntity(menu_create.replace("ACCESS_TOKEN", getAccessToken()),getFileForJson("menu.json") , String.class).getBody();

        return JSONObject.parseObject(json);
    }

    /**
     * 删除菜单
     * @return
     * @throws Exception
     */
    public JSONObject menuDelete() throws Exception {
        String json = restTemplate.getForEntity(menu_delete.replace("ACCESS_TOKEN", getAccessToken()),String.class).getBody();
        return JSONObject.parseObject(json);
    }

    /**
     * 发送模板消息
     * @param openId
     * @return
     * @throws Exception
     */
    public JSONObject sendTempleteMsg(String openId) throws Exception{
        //获取模板消息(此处模拟发送，正常情况应从数据库中取数据)
        String postBody = getFileForJson("templeteMsg.json").replace("OPENID",openId);

        String json = restTemplate.postForEntity(send_templete_msg.replace("ACCESS_TOKEN", getAccessToken()),postBody,String.class).getBody();

        return JSONObject.parseObject(json);
    }

    /**
     * 获取网页端accessToken
     * @param code
     * @return
     * @throws Exception
     */
    public JSONObject getWebAccessToken(String code) throws Exception{
        Object webAccessToken = redisTemplate.opsForValue().get(redisDomain+":webAccessToken");

        //如果redis中不存在accessToken，重新获取accessToken并保存，否则直接返回
        if (webAccessToken == null) {
            Object appConfig = getWechatConfig();
            if(appConfig == null){
                throw new Exception("请检查redis中是否配置了appConfig");
            }

            JSONObject json = JSONObject.parseObject(appConfig.toString());

            String result = restTemplate.getForEntity(web_access_token.replace("APPID", json.getString("appID"))
                    .replace("SECRET", json.getString("appsecret")).replace("CODE",code), String.class).getBody();

            logger.info("重新查询webAccessToken结果：" + result);

            JSONObject jsonObject = JSON.parseObject(result);

            Long expire = jsonObject.getLong("expires_in") - 2000;

            //返回值存入redis
            redisTemplate.opsForValue().set(redisDomain+":webAccessToken",result,expire, TimeUnit.SECONDS);

            return jsonObject;
        }

        return JSON.parseObject(webAccessToken.toString());
    }

    /**
     * 获取用户信息
     * @param code
     * @return
     * @throws Exception
     */
    public String getUserinfo(String code) throws Exception{
        //根据code获取网页access_token
        JSONObject jsonObject = getWebAccessToken(code);
        //根据网页access_token得到用户信息
        return restTemplate.getForEntity(sasapi_userinfo.replace("ACCESS_TOKEN", jsonObject.getString("access_token"))
                .replace("OPENID",jsonObject.getString("openid")), String.class).getBody();
    }

    /**
     * 获得生成分享页面签名的ticket票据
     * @return
     * @throws Exception
     */
    public String getJsapiTicket() throws Exception{
        Object jsapiTicket = redisTemplate.opsForValue().get(redisDomain+":jsapiTicket");

        //如果redis中不存在accessToken，重新获取accessToken并保存，否则直接返回
        if (jsapiTicket == null) {
            Object appConfig = getWechatConfig();
            if(appConfig == null){
                throw new Exception("请检查redis中是否配置了appConfig");
            }

            JSONObject json = JSONObject.parseObject(appConfig.toString());

            String result = restTemplate.getForEntity(jsapi_ticket.replace("ACCESS_TOKEN", getAccessToken()), String.class).getBody();

            logger.info("重新查询accessToken结果：" + result);
            JSONObject jsonObject = JSON.parseObject(result);

            Long expire = jsonObject.getLong("expires_in") - 2000;

            //返回值存入redis
            redisTemplate.opsForValue().set(redisDomain+":jsapiTicket",result,expire, TimeUnit.SECONDS);

            return jsonObject.getString("ticket");
        }
        return JSON.parseObject(jsapiTicket.toString()).getString("ticket");
    }


}
