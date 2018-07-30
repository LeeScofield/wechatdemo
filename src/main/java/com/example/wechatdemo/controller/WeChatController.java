package com.example.wechatdemo.controller;

import com.example.wechatdemo.model.Signature;
import com.example.wechatdemo.util.SHA1Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@PropertySource("classpath:profile.properties")
public class WeChatController {

    @Value("${token}")
    private String token;

    /**
     * 微信接口测试验证
     * @param signature
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "wechat",method = RequestMethod.GET)
    @ResponseBody
    public String weChat(Signature signature) throws Exception{

        String[] arr = new String[]{this.token, signature.getTimestamp(), signature.getNonce()};

        //对token,timestamp,nonce排序后拼接
        String arrData = Stream.of(arr).sorted().collect(Collectors.joining());

        //获取生成签名
        String sgin = SHA1Util.getSignForSHA1(arrData);

        if (sgin.equalsIgnoreCase(signature.getSignature())) {
            return signature.getEchostr();
        }else{
            return null;
        }

    }



}
