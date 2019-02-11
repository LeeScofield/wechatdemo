package com.example.wechatdemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Lee on 2019-01-28.
 *
 * @author Lee
 */
@Getter
@Setter
@ToString
public class UserInfo {
    private String country;
    private String province;
    private String city;
    private String openid;
    private String sex;
    private String nickname;
    private String headimgurl;
    private String language;
    private String privilege;

}