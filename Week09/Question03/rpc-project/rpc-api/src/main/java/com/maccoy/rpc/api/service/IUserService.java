package com.maccoy.rpc.api.service;

import com.maccoy.rpc.api.domain.User;
import org.springframework.stereotype.Component;

public interface IUserService {

    User selectUserById(Integer userId);

}
