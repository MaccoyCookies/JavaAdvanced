package com.maccoy.advanced.spring.single;

public class Single02 {

    private static Single02 INSTANCE;

    private Single02() {
    }

    public synchronized static Single02 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Single02();
        }
        return INSTANCE;
    }

}
