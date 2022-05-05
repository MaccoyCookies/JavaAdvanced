package com.maccoy.advanced.jvm.homework;

public class Hello {

    public static void main(String[] args) {
        int a = 0;
        int b = 2;
        int c = a + b;
        if (c > 3) {
            c = 0;
        } else {
            c = 5;
        }
        while (c > 1) {
            a ++;
            c --;
        }
    }

}
