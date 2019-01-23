package com.example.wechatdemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Lee on 2018-07-31.
 *
 * @author Lee
 *
 * 被动回复用户消息
 * 参考 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453
 */
@Getter@Setter@ToString
@XmlAccessorType(XmlAccessType.FIELD) //XML元素映射类字段，大小写敏感
public class MsgOut {
//    @XmlElement(name = "FromUserName")
    private String FromUserName; //开发者微信号
    private Long CreateTime;	 //消息创建时间 （整型）
    private String ToUserName;	 //发送方帐号（一个OpenID）
    private String MsgType; //[text,image,voice,video,shortvideo,location,link]

    //--------文本
//    private String Content; //文本消息内容
//
//    //--------图片
//    @XmlElementWrapper(name = "Image")
//    private String[] MediaId;  //通过素材管理中的接口上传多媒体文件，得到的id。
//
//    //--------语音消息 MediaId
//
//    //--------视频消息 MediaId
//    private String Title;	//视频消息的标题
//    private String Description; //视频消息的描述
//
//    //--------音乐消息   MsgType Title Title
//    private String MusicURL; //音乐链接
//    private String HQMusicUrl; //高质量音乐链接，WIFI环境优先使用该链接播放音乐
//    private String ThumbMediaId; //缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
//
//    //--------图文消息  Description
//    private String ArticleCount;    //图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
//    private String Articles;	    //图文消息信息，注意，如果图文数超过限制，则将只发限制内的条数
//    private String PicUrl;	       	//图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
//    private String Url;		        //点击图文消息跳转链接

}
