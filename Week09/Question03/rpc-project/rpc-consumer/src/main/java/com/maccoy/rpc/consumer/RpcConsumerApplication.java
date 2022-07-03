package com.maccoy.rpc.consumer;

import com.maccoy.rpc.api.domain.User;
import com.maccoy.rpc.api.service.IUserService;
import com.maccoy.rpc.core.frame.RpcClientInvoker;

public class RpcConsumerApplication {

    public static void main(String[] args) {
        IUserService userService = RpcClientInvoker.create(IUserService.class, "http://localhost:8080/");
        User user = userService.selectUserById(1);
        System.out.println("find user id=1 from server: " + user.getName());
    }

}
