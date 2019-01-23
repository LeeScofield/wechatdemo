package com.example.wechatdemo.model.message;

import com.example.wechatdemo.model.MsgOut;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lee
 * date:2019-01-24
 */
@XmlRootElement(name = "xml") //根元素
@XmlAccessorType(XmlAccessType.FIELD) //XML元素映射类字段，大小写敏感
@Setter@Getter@ToString
public class ImageMsgOut extends MsgOut {
    @XmlElementWrapper(name = "Image")
    private String[] MediaId;  //通过素材管理中的接口上传多媒体文件，得到的id。
}
