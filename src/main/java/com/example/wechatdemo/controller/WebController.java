package com.example.wechatdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.wechatdemo.model.UserInfo;
import com.example.wechatdemo.util.HttpUtil;
import com.example.wechatdemo.util.SHA1Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Lee on 2019-02-01.
 *
 * @author Lee
 */
@Controller
public class WebController {
    @Autowired
    private HttpUtil httpUtil;

    @Value("${webUrl}")
    private String webUrl;

    @Autowired
    private RedisTemplate redisTemplate;

    //跳转页面
    @RequestMapping(value = "/index")
    public ModelAndView index(String code,String state) throws Exception{

        ModelAndView modelAndView = new ModelAndView("index");
        //得到用户信息并转为bean对象
        String userInfoStr = httpUtil.getUserinfo(code);
        UserInfo userInfo = JSON.parseObject(userInfoStr, UserInfo.class);

        String sex = userInfo.getSex();

        userInfo.setSex(sex == null ? "男" : sex.equals("1") ? "男" : "女");

        modelAndView.addObject("userInfo", userInfo);

        return modelAndView;
    }

    //分享
    @RequestMapping(value = "/fenxiang")
    public ModelAndView fenxiang(HttpServletRequest request) throws Exception{
        System.out.println(request.getQueryString());
        ModelAndView modelAndView = new ModelAndView("fenxiang");

        Map<String,String> map = new HashMap();
        map.put("noncestr",UUID.randomUUID().toString().replace("-",""));
        map.put("jsapi_ticket",httpUtil.getJsapiTicket());
        map.put("timestamp",String.valueOf(System.currentTimeMillis()).substring(0,10));
        String url = webUrl+"/fenxiang";

        //需要根据当前请求URL全路径进行签名
        String queryStr = request.getQueryString();
        if (queryStr != null && queryStr.equals("") ) {
            url = url + "?";
        }else if(queryStr != null && !queryStr.equals("")){
            url = url + "?"+queryStr;
        }

        map.put("url", url);

        //字典排序
        String jsSdkSignatureStr = map.keySet().stream().sorted().map(k -> k + "=" + map.get(k) + "&").collect(Collectors.joining());


                jsSdkSignatureStr = jsSdkSignatureStr.substring(0, jsSdkSignatureStr.length() - 1);
        System.out.println(jsSdkSignatureStr);
        //生成签名
        String signature = SHA1Util.getSignForSHA1(jsSdkSignatureStr);
        map.put("signature",signature);

        String wechatConfig = httpUtil.getWechatConfig();
        map.put("appId", JSONObject.parseObject(wechatConfig).getString("appID"));

        modelAndView.addObject("jsSDK", map);

        System.out.println(map);

        return modelAndView;
    }

    public static void main(String[] args) {

        System.out.println(UUID.randomUUID().toString().replace("-",""));
    }
}
