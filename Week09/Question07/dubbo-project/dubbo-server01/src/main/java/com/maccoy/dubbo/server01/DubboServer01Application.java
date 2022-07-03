package com.maccoy.dubbo.server01;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@EnableDubboConfiguration
@SpringBootApplication
@MapperScan("com.maccoy.dubbo.server01.mapper")
public class DubboServer01Application {


    public static void main(String[] args) {
        SpringApplication.run(DubboServer01Application.class, args);
    }

}


