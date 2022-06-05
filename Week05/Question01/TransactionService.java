package com.maccoy.advanced.spring;

public class TransactionService implements ITransaction {

    @SimpleAop
    public void testAop() {
        System.out.println("这是执行方法");
    }

}
