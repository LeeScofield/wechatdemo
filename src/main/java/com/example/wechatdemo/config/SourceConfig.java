package com.example.wechatdemo.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by Lee on 2019-02-02.
 * 资源文件配置，只用引入一次
 * @author Lee
 */
@Component
@PropertySource("classpath:profile.properties")
public class SourceConfig {
}
