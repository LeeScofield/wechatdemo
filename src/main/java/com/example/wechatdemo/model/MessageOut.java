package com.example.wechatdemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
@XmlRootElement(name = "xml") //根元素
@XmlAccessorType(XmlAccessType.FIELD) //XML元素映射类字段，大小写敏感
public class MessageOut {
//    @XmlElement(name = "FromUserName")
    private String FromUserName; //发送方帐号（一个OpenID）
    private String CreateTime;	 //消息创建时间 （整型）
    private String ToUserName;	 //开发者微信号
    private String MsgType; //[text,image,voice,video,shortvideo,location,link]
    private String MsgId;	 //消息id，64位整型

    //--------文本
    private String Content; //文本消息内容


    private String MediaId;  //图片消息媒体id，可以调用多媒体文件下载接口拉取数据。

    //--------图片
    private String PicUrl; //图片链接（由系统生成）

    //--------语音消息
    private String Format;	//语音格式，如amr，speex等
    private String Recognition;	//语音识别结果，UTF8编码

    //--------视频消息
    private String ThumbMediaId;	//视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。

    //--------小视频消息   ThumbMediaId

    //--------地理位置
    private String Location_X;      //地理位置维度
    private String Location_Y;      //地理位置经度
    private String Scale; //地图缩放大小
    private String Label; //地理位置信息

    // --------语音消息
    private String Title; //消息标题
    private String Description; //消息描述
    private String Url; //消息链接



}
