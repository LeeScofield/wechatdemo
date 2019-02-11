package com.example.wechatdemo.util;

import org.springframework.context.ApplicationContext;

/**
 * Created by Lee on 2019-01-29.
 *
 * @author Lee
 */
public class ApplicationUtil {

    private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext applicationContext){
        ApplicationUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

}
