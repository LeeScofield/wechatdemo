package com.example.wechatdemo.model.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Lee on 2019-01-24.
 *
 * @author Lee
 */
@Getter@Setter@ToString
@XmlAccessorType(XmlAccessType.FIELD) //XML元素映射类字段，大小写敏感
public class NewsItem {
    private String Title;	//视频消息的标题
    private String Description; //视频消息的描述
    private String PicUrl;	       	//图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    private String Url;		        //点击图文消息跳转链接

}
