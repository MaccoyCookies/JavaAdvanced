package com.maccoy.dubbo.server01.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.maccoy.dubbo.service.IAccountServer02Service;
import com.maccoy.dubbo.server01.mapper.AccountMapper;
import com.maccoy.dubbo.server01.service.IAccountService;
import com.maccoy.dubbo.server01.service.IFreezeDetailService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

    @Resource
    private AccountMapper accountMapper;

    @Autowired
    private IFreezeDetailService freezeDetailService;

    @Reference
    private IAccountServer02Service accountServer02Service;

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public void transfer(String tid, Long userId, Long targetId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType) {
        // 幂等处理, 判断tryLog表是否有记录
        // 悬挂处理, 判断confirm/cancel表是否有记录

        // 扣减金额
        int accountCount = accountMapper.handleAccountBalance(userId, -amount, accountType);
        if (accountCount == 0) throw new HmilyException("余额不足");
        // 生成冻结记录
        int freezeCount = freezeDetailService.handleFreezeDetail(userId, amount, accountType);
        if (freezeCount == 0) throw new HmilyException("系统异常，请稍后再试!");
        // 插入tryLog记录
        // 调用server02发起转账
        accountServer02Service.transfer(tid, targetId, targetAmount, targetAccountType, amount, accountType);
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirm(String tid, Long userId, Long targetId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType) {
        // 幂等处理，判断confirm表是否有记录
        // 悬挂, 只有try处理完了，并且cancel没有记录，才能处理confirm

        // 解除冻结记录
        int freezeCount = freezeDetailService.handleFreezeDetail(userId, -amount, accountType);
        if (freezeCount == 0) throw new RuntimeException("解除冻结失败");
        // 添加到另一个账户金额
        int accountCount = accountMapper.handleAccountBalance(userId, targetAmount, targetAccountType);
        if (accountCount == 0) throw new RuntimeException("系统异常，请稍后再试!");

        DateTime parse = DateUtil.parse("2022-07-04 22:58:00", DatePattern.NORM_DATETIME_PATTERN);
        if (DateUtil.date().compareTo(parse) < 0) {
            int i = 1 / 0;
        }
        // 写入confirm日志
    }


    @Transactional(rollbackFor = Exception.class)
    public void cancel(String tid, Long userId, Long targetId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType) {
        // 幂等处理，判断confirm表是否有记录
        // 悬挂, 只有try处理完了 才能处理cancel

        // 增加金额
        int accountCount = accountMapper.handleAccountBalance(userId, amount, accountType);
        if (accountCount == 0) throw new HmilyException("系统异常，请稍后再试!");
        // 扣减冻结
        int freezeCount = freezeDetailService.handleFreezeDetail(userId, -amount, accountType);
        if (freezeCount == 0) throw new HmilyException("系统异常，请稍后再试!");

        // 写入cancel日志
    }
}

