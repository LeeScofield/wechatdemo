package com.example.wechatdemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Lee on 2018-07-31.
 *
 * @author Lee
 */
@Getter@Setter@ToString
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
//    @XmlElement(name = "FromUserName")
    private String FromUserName; //发送方帐号（一个OpenID）
    private String CreateTime;	 //消息创建时间 （整型）
    private String ToUserName;	 //开发者微信号
    private String MsgType	   ; //text
    private String Content	   ; //文本消息内容
    private String MsgId    ;	 //消息id，64位整型

}
