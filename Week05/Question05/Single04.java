package com.maccoy.advanced.spring.single;

public class Single04 {

    // 静态内部类
    private static class InnerClass {
        // 初始化实例
        private final static Single04 INSTANCE = new Single04();
    }

    private Single04() {
    }

    public static Single04 getInstance() {
        return InnerClass.INSTANCE;
    }

}
