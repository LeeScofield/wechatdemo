package com.example.wechatdemo.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lee on 2019-01-30.
 *
 * @author Lee
 */
@Component
public class AsyncService {

    @PostConstruct
    public void init(){
        System.out.println("初始化。。。");
    }

    @Async
    public void taskMethod(){
        System.out.println(Thread.currentThread().getName() + ":" + "进方法3");
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + "出方法3");
    }
}
