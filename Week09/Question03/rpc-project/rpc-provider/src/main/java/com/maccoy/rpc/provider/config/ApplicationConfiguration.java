// package com.maccoy.rpc.provider.config;
//
// import com.maccoy.rpc.core.frame.RpcServerInvoker;
// import com.maccoy.rpc.provider.context.RpcResolverApplicationContext;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class ApplicationConfiguration {
//
//     @Bean
//     public RpcResolverApplicationContext rpcResolverApplicationContext() {
//         return new RpcResolverApplicationContext();
//     }
//
//     @Bean
//     public RpcServerInvoker rpcInvoker(RpcResolverApplicationContext rpcResolverApplicationContext) {
//         return new RpcServerInvoker(rpcResolverApplicationContext);
//     }
//
// }
