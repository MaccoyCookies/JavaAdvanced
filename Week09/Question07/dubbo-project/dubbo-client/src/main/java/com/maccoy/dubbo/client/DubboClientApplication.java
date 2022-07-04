package com.maccoy.dubbo.client;

import cn.hutool.core.util.IdUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.fastjson.JSON;
import com.maccoy.dubbo.service.IAccountServer01Service;
import com.maccoy.dubbo.service.IAccountServer02Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDubbo
@RestController
@SpringBootApplication
public class DubboClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboClientApplication.class, args);
    }

    @Reference
    private IAccountServer01Service accountServer01Service;

    @Reference
    private IAccountServer02Service accountServer02Service;

    @GetMapping("/test")
    public String test() {
        String tid = IdUtil.fastSimpleUUID();
        // 用户ID: 1, 外汇交易1美元. 转换为7人民币
        // 用户ID: 2, 外汇交易7人民币, 转换为1美元
        Boolean transfer01 = accountServer01Service.transfer(tid, 1L, 2L, 1L, 1, 7L, 2);
        return JSON.toJSONString(transfer01);
    }

    @GetMapping("/test2")
    public String test2() {
        String tid = IdUtil.fastSimpleUUID();
        Boolean transfer02 = accountServer02Service.transfer(tid, 2L, 1L, 1, 7L, 2);
        return JSON.toJSONString(transfer02);
    }

    @GetMapping("/test1")
    public String test1() {
        accountServer02Service.test();
        return "1";
    }

}
