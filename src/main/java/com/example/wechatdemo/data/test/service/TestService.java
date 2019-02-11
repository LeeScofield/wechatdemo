package com.example.wechatdemo.data.test.service;

import com.example.wechatdemo.data.baseCommon.BaseService;
import com.example.wechatdemo.data.baseCommon.impl.BaseServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by Lee on 2019-01-25.
 *
 * @author Lee
 */
@Service
public class TestService extends BaseServiceImpl implements BaseService {


    @Cacheable(value = "test",key = "#userId")
    public String getUserName(String userId){
        System.out.println("获取userName:" + userId);
        return userId.equals("1") ? "张三" : "李四";
    }

}
