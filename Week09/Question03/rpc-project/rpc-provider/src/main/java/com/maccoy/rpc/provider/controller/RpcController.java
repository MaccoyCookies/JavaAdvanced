package com.maccoy.rpc.provider.controller;

import com.maccoy.rpc.core.domain.RpcRequest;
import com.maccoy.rpc.core.domain.RpcResponse;
import com.maccoy.rpc.core.frame.RpcServerInvoker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/rpc")
@RestController
public class RpcController {

    @Autowired
    private RpcServerInvoker invoker;

    @PostMapping("/invoke")
    public RpcResponse invoke(@RequestBody RpcRequest rpcRequest) {
        return invoker.invoke(rpcRequest);
    }

}
