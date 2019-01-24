package com.example.wechatdemo.constant.enumConstant;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Lee on 2019-01-24.
 *
 * @author Lee
 *
 * 事件类型
 */
public enum EventType {
    subscribe("subscribe","订阅"),
    unsubscribe("unsubscribe","取消订阅"),
    SCAN("SCAN","已关注后的扫描二维码"),
    LOCATION("LOCATION","上报地理位置事件"),
    CLICK("CLICK","自义定菜单事件"),
    VIEW("VIEW","点击菜单跳转链接")
    ;

    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String name;

    EventType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
