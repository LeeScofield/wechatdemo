package com.example.wechatdemo.constant.enumConstant;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2019-01-22.
 * 消息类型
 *
 * @author Lee
 */
public enum MsgType {
    text("text", "文本消息"),
    image("image", "图片消息"),
    voice("voice", "语音消息"),
    video("video", "视频消息"),
    shortvideo("shortvideo", "小视频消息"),
    location("location", "地理位置消息"),
    link("link", "链接消息"),
    music("music","音乐消息"),
    news("news","图文消息"),
    event("event","事件推送")
    ;

    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String name;

    MsgType(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
