package com.maccoy.dubbo.server02.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.maccoy.dubbo.service.IAccountServer02Service;
import com.maccoy.dubbo.server02.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class AccountServer02ServiceImpl implements IAccountServer02Service {

    @Autowired
    private IAccountService accountService;

    @Override
    public Boolean transfer(String tid, Long userId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType) {
        log.info("transfer ....");
        accountService.transfer(tid, userId, amount, accountType, targetAmount, targetAccountType);
        return true;
    }

    @Override
    public void test() {
        log.info("info");
    }
}
