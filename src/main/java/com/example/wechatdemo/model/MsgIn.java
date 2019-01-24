package com.example.wechatdemo.model;

import javafx.scene.transform.Scale;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sun.security.krb5.internal.Ticket;

import javax.xml.bind.annotation.*;

/**
 * Created by Lee on 2018-07-31.
 *
 * @author Lee
 *
 * 接收普通消息
 * 参考 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453
 */
@Getter@Setter@ToString
@XmlRootElement(name = "xml") //根元素
@XmlAccessorType(XmlAccessType.FIELD) //XML元素映射类字段，大小写敏感
public class MsgIn {
//    @XmlElement(name = "FromUserName")
    private String FromUserName; //发送方帐号（一个OpenID）
    private Long CreateTime;	 //消息创建时间 （整型）
    private String ToUserName;	 //开发者微信号
    private String MsgType; //[text,image,voice,video,shortvideo,location,link,event]
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



    //---------------事件--------------
    private String Event;	//事件类型，subscribe(订阅)、unsubscribe(取消订阅)

    //--------扫描带参数二维码事件
    private String EventKey;	//1.事件KEY值，qrscene_为前缀，后面为二维码的参数值 2.事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
    private String Ticket;      //二维码的ticket，可用来换取二维码图片

    //--------上报地理位置事件
    private String Latitude;	//地理位置纬度
    private String Longitude;	//地理位置经度
    private String Precision;	//地理位置精度

    //--------自定义菜单事件 EventKey 1.事件KEY值，与自定义菜单接口中KEY值对应 2.事件KEY值，设置的跳转URL
}
