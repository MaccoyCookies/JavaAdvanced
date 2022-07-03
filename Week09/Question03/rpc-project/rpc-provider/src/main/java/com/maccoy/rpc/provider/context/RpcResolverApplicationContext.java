// package com.maccoy.rpc.provider.context;
//
// import com.maccoy.rpc.core.frame.RpcResolver;
// import lombok.Getter;
// import org.springframework.beans.BeansException;
// import org.springframework.context.ApplicationContext;
// import org.springframework.context.ApplicationContextAware;
//
// import java.util.Map;
//
// @Getter
// public class RpcResolverApplicationContext implements RpcResolver, ApplicationContextAware {
//
//     private ApplicationContext applicationContext;
//
//     public Object resolver(String serviceClass) {
//         try {
//             Class<?> aClass = Class.forName(serviceClass);
//             Map<String, ?> beansOfType = applicationContext.getBeansOfType(aClass);
//             for (Map.Entry<String, ?> stringEntry : beansOfType.entrySet()) {
//                 return stringEntry.getValue();
//             }
//         } catch (ClassNotFoundException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
//
//     public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//         this.applicationContext = applicationContext;
//     }
// }
