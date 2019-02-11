package com.example.wechatdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Lee on 2019-01-30.
 *
 * @author Lee
 */
@Configuration
public class ExecutorConfig {

    @Bean
    public TaskExecutor taskExecutor(){

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(25);

        return threadPoolTaskExecutor;
    }
}
