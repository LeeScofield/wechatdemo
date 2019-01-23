package com.example.wechatdemo.controller;

import com.example.wechatdemo.model.*;
import com.example.wechatdemo.util.SHA1Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Handler;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@PropertySource("classpath:profile.properties")
public class WeChatController {

    @Value("${token}")
    private String token;

    /**
     * 微信接口测试验证(为GTI请求)
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

    @RequestMapping(value = "wechat",method = RequestMethod.POST)
    public Object weChatMessage(@RequestBody MessageIn messageIn) throws Exception {

        MsgType msgType = Stream.of(MsgType.values()).filter(m -> m.getCode().equals(messageIn.getMsgType()))
                .collect(Collectors.toList()).get(0);

        System.out.println(msgType.getName());
//        this.getClass().getDeclaredAnnotation()

        MessageOut messageOut = (MessageOut)this.getClass().getDeclaredMethod("handler_"+msgType.getCode(), MessageIn.class).invoke(this,messageIn);

        return messageOut;
    }




    private MessageOut handler_text(MessageIn messageIn){
        MessageOut messageOut = new MessageOut();
        messageOut.setFromUserName("aaa");
        messageOut.setToUserName("bbb");
        System.out.println(messageOut);
        return messageOut;
    }



    @ResponseBody
    @RequestMapping(value = "receiveMessage",method = RequestMethod.POST)
    public String receiveMessage(@RequestBody MessageIn messageIn){
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
    public MessageOut webXml(){
        MessageOut messageOut = new MessageOut();
        messageOut.setFromUserName("aaa");
        messageOut.setToUserName("bbb");
        System.out.println(messageOut);
        return messageOut;
    }

    @RequestMapping(value = "/demo",produces = { "application/xml;charset=UTF-8" })
    @ResponseBody
    public DemoUser demo() {
        DemoUser demoUser = new DemoUser();
        demoUser.setName("name");
        demoUser.setPassword("password");
        System.out.println(66666);
        return demoUser;
    }

}
