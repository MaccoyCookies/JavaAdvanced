package com.maccoy.dubbo.server02;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@EnableDubbo
@SpringBootApplication
@RestController
@MapperScan("com.maccoy.dubbo.server02.mapper")
public class DubboServer02Application {

    public static void main(String[] args) {
        SpringApplication.run(DubboServer02Application.class, args);
    }

}