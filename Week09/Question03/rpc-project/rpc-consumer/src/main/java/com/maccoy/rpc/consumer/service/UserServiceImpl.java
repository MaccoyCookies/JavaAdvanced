package com.maccoy.rpc.consumer.service;

import com.maccoy.rpc.api.domain.User;
import com.maccoy.rpc.api.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Override
    public User selectUserById(Integer userId) {
        return null;
    }
}
