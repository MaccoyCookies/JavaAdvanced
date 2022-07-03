package com.maccoy.dubbo.api.service;

import org.dromara.hmily.annotation.Hmily;

public interface IAccountServer01Service {

    @Hmily
    Boolean transfer(String tid, Long userId, Long targetId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType);

}
