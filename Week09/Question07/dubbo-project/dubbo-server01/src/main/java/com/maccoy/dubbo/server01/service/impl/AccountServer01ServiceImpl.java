package com.maccoy.dubbo.server01.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.maccoy.dubbo.api.service.IAccountServer01Service;
import com.maccoy.dubbo.server01.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class AccountServer01ServiceImpl implements IAccountServer01Service {

    @Autowired
    private IAccountService accountService;

    @Override
    public Boolean transfer(String tid, Long userId, Long targetId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType) {
        log.info("transfer ....");
        accountService.transfer(tid, userId, targetId, amount, accountType, targetAmount, targetAccountType);
        return true;
    }
}
