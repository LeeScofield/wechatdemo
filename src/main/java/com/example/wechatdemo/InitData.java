package com.example.wechatdemo;

import com.example.wechatdemo.util.ResourceUtil;
import redis.clients.jedis.Jedis; /**
 * Created by Lee on 2019-01-29.
 *
 * @author Lee
 */

/**
 * 初始化数据，如redis配置
 */
public class InitData{

    public static void main(String[] args) throws Exception{

        //获取配置信息，这里存放在本地
        String appConfig = "";
        try (ResourceUtil wechatConfig = new ResourceUtil("D:/wechat.json")) {
            appConfig = wechatConfig.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取redis中hash域
        String redisDomain = "";
        try (ResourceUtil redisDemoConfig = new ResourceUtil(InitData.class.getClassLoader().getResourceAsStream(".properties"))) {
            redisDomain = redisDemoConfig.getProperties().getProperty("redis.domain");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(appConfig);
        System.out.println(redisDomain);
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set(redisDomain+":appConfig", appConfig);

        System.out.println("初始化数据完成！");


    }

}
