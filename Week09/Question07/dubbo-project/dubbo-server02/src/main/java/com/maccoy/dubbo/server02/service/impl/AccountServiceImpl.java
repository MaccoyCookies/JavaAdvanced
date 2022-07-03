package com.maccoy.dubbo.server02.service.impl;

import com.maccoy.dubbo.server02.mapper.AccountMapper;
import com.maccoy.dubbo.server02.service.IAccountService;
import com.maccoy.dubbo.server02.service.IFreezeDetailService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements IAccountService {

    @Resource
    private AccountMapper accountMapper;

    @Autowired
    private IFreezeDetailService freezeDetailService;

    @Override
    @Transactional
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public void transfer(String tid, Long userId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType) {

        // 扣减金额
        int accountCount = accountMapper.handleAccountBalance(userId, -amount, accountType);
        if (accountCount == 0) throw new HmilyException("余额不足");
        // 生成冻结记录
        int freezeCount = freezeDetailService.handleFreezeDetail(userId, amount, accountType);
        if (freezeCount == 0) throw new HmilyException("系统异常，请稍后再试!");

    }

    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(String tid, Long userId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType) {
        // 幂等处理，判断confirm表是否有记录
        // 悬挂, 只有try处理完了，并且cancel没有记录，才能处理confirm

        // 解除冻结记录
        int freezeCount = freezeDetailService.handleFreezeDetail(userId, -amount, accountType);
        if (freezeCount == 0) throw new RuntimeException("解除冻结失败");
        // 添加到另一个账户金额
        int accountCount = accountMapper.handleAccountBalance(userId, targetAmount, targetAccountType);
        if (accountCount == 0) throw new RuntimeException("系统异常，请稍后再试!");

        // 写入confirm日志
        return Boolean.TRUE;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(String tid, Long userId, Long amount, Integer accountType, Long targetAmount, Integer targetAccountType) {
        // 幂等处理，判断confirm表是否有记录
        // 悬挂, 只有try处理完了 才能处理cancel

        // 增加金额
        int accountCount = accountMapper.handleAccountBalance(userId, amount, accountType);
        if (accountCount == 0) throw new HmilyException("系统异常，请稍后再试!");
        // 扣减冻结
        int freezeCount = freezeDetailService.handleFreezeDetail(userId, -amount, accountType);
        if (freezeCount == 0) throw new HmilyException("系统异常，请稍后再试!");

        // 写入cancel日志
        return Boolean.TRUE;
    }
}
