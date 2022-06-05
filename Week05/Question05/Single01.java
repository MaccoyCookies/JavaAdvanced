package com.maccoy.advanced.spring.single;

public class Single01 {

    private final static Single01 INSTANCE = new Single01();

    private Single01() {}

    public static Single01 getInstance() {
        return INSTANCE;
    }

}
