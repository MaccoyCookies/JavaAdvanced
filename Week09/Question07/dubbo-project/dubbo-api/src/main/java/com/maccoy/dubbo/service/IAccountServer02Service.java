package com.maccoy.dubbo.service;

import org.dromara.hmily.annotation.Hmily;

public interface IAccountServer02Service {

    @Hmily
    Boolean transfer(String tid, Long userId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType);

    @Hmily
    void test();
}
