package com.maccoy.dubbo.server01.service;

public interface IAccountService {

    void transfer(String tid, Long userId, Long targetId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType);

}
