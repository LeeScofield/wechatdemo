package com.example.wechatdemo.model.message;

import com.example.wechatdemo.model.MsgOut;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Lee on 2019-01-24.
 *
 * @author Lee
 */
@XmlRootElement(name = "xml") //根元素
@XmlAccessorType(XmlAccessType.FIELD) //XML元素映射类字段，大小写敏感
@Setter@Getter@ToString
public class NewsMsgOut extends MsgOut {

    private String ArticleCount;    //图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息

    @XmlElementWrapper(name = "Articles")
    private NewsItem[] item;

}
