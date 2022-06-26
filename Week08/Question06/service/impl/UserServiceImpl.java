package com.maccoy.sharding.project.service.impl;

import com.maccoy.sharding.project.domain.User;
import com.maccoy.sharding.project.mapper.UserMapper;
import com.maccoy.sharding.project.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void commit() {
        userMapper.insertSelective(User.builder().id(2000L).userId(2000L).build());
        userMapper.insertSelective(User.builder().id(2001L).userId(2001L).build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    public void rollback() {
        userMapper.insertSelective(User.builder().id(2000L).userId(2000L).build());
        int i = 1 / 0;
        userMapper.insertSelective(User.builder().id(2001L).userId(2001L).build());

    }
}
