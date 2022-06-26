package com.maccoy.sharding.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.maccoy.sharding.project.mapper")
public class ShardingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingProjectApplication.class, args);
    }

}
