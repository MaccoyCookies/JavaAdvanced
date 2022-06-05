package com.maccoy.advanced.spring;

import org.aspectj.lang.ProceedingJoinPoint;

public class SimpleAopAspect {

    public void prefix() {
        System.out.println("aop前置");
    }

    public void suffix() {
        System.out.println("aop后置");
    }

    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around 前环绕");
        joinPoint.proceed();
        System.out.println("around 后环绕");
    }

}
