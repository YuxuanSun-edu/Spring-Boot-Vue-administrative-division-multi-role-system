package com.fast.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import java.util.TimeZone;

/**
 * 程序基础配置类
 * springboot应用程序的基础配设置中心
 * 负责配置一些通用的全局应用程序设置
 */
@Configuration // 告诉Spring: "我是配置类，你启动的时候要读取我的配置"
@MapperScan("com.fast.**.mapper") // 告诉MyBatis 去哪里找数据库接口
public class ApplicationConfig {

    /**
     * 时区配置
     * 解决数据库查询出来的时间和前端显示的时间不一致的问题（差8小时）
     */
    @Bean // 告诉spring把返回的对象放到容器里，其他地方可以拿来用
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        return jacksonObjectMapperBuilder -> {
            // 设置时区为系统默认时区 (即 Asia/Shanghai)
            jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault()); //获取当前服务器的默认时区
        };
    }
}