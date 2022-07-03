package com.maccoy.dubbo.server02.service;

public interface IAccountService {

    void transfer(String tid, Long userId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType);

}
