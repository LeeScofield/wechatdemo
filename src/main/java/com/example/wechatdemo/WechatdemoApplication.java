package com.example.wechatdemo;

import com.example.wechatdemo.util.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class WechatdemoApplication {

	private final static Logger logger = LoggerFactory.getLogger(WechatdemoApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(WechatdemoApplication.class, args);

		ApplicationUtil.setApplicationContext(applicationContext);

		logger.info("启动完成！");

	}
}
