package com.fast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//排除数据源自动配置
public class SpringbootApplication {

    //main方法：java程序入口点
    public static void main(String[] args)

    //启动Springboot应用
    {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
