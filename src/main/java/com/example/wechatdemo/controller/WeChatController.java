package com.example.wechatdemo.controller;

import com.example.wechatdemo.constant.BaseConstant;
import com.example.wechatdemo.model.*;
import com.example.wechatdemo.model.message.ImageMsgOut;
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

    @ResponseBody
    @RequestMapping(value = "wechat",method = RequestMethod.POST,produces = MediaType.APPLICATION_XML_VALUE)
    public MsgOut weChatMessage(@RequestBody MsgIn messageIn) throws Exception {

        //得到用户传入的msgType
        MsgType msgType = Stream.of(MsgType.values()).filter(m -> m.getCode().equals(messageIn.getMsgType())).findFirst().get();

        System.out.println(msgType.getName());

        //如果用户传入的msgType为可用的消息类型，则进行消息处理，否则不处理
        MsgOut messageOut = Stream.of(BaseConstant.MSGTYPE_ENABLED).filter(m -> msgType.getCode().equals(m))
                .findFirst().map(curMsgTypeCode -> {
                    try {
                        return (MsgOut) this.getClass().getDeclaredMethod("handler_" + curMsgTypeCode, MsgIn.class).invoke(this, messageIn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new MsgOut();
                }).orElseGet(() -> null);

        return messageOut;
    }

    //处理文本消息
    private MsgOut handler_text(MsgIn messageIn){
        TextMsgOut textMsgOut = new TextMsgOut();
        textMsgOut.setFromUserName(messageIn.getToUserName());
        textMsgOut.setToUserName(messageIn.getFromUserName());
        textMsgOut.setCreateTime(System.currentTimeMillis());
        textMsgOut.setMsgType(messageIn.getMsgType());

        textMsgOut.setContent("hello:" + messageIn.getContent());
        System.out.println("处理文本消息");
        return textMsgOut;
    }

    //处理图片消息
    private MsgOut handler_image(MsgIn messageIn){
        ImageMsgOut imageMsgOut = new ImageMsgOut();
        imageMsgOut.setFromUserName(messageIn.getToUserName());
        imageMsgOut.setToUserName(messageIn.getFromUserName());
        imageMsgOut.setCreateTime(System.currentTimeMillis());
        imageMsgOut.setMsgType(messageIn.getMsgType());
        //原样返回图片
        imageMsgOut.setMediaId(new String[]{messageIn.getMediaId()});
        System.out.println("处理图片消息");
        return imageMsgOut;
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
