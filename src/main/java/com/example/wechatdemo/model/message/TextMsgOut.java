package com.example.wechatdemo.model.message;

import com.example.wechatdemo.model.MsgOut;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lee
 * date:2019-01-23
 */
@XmlRootElement(name = "xml") //根元素
@XmlAccessorType(XmlAccessType.FIELD) //XML元素映射类字段，大小写敏感
@Setter@Getter@ToString
public class TextMsgOut extends MsgOut{
    private String Content; //文本消息内容
}
