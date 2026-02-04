package com.fast.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 配置读取器，负责读取application.yml
 * 配置文件中的自定义配置项
 */
@Component //告诉spring这是一个组件
@ConfigurationProperties(prefix = "fast") //告诉spring读取项目配置文件中以fast开头的配置项
public class fastConfig {
    /**
     * 文件上传路径配置
     * 对应application.yml中的fast.profile
     * 使用静态变量，可通过类名直接访问
     */
    private String profile; //存储上传文件的基本路径

    public String getProfile() {
        return profile;
    }

    /**
     * 设置上传路径
     * spring自动调用这个方法，将配置文件中值传进来
     * @param profile 从配置文件中读取到的上传路径
     */
    public void setProfile(String profile) {
        //把配置值设置到静态变量中
        this.profile = profile;
    }
}
