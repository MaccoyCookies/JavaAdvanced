package com.maccoy.advanced.spring.single;

public class TestSingleDemo {

    public static void main(String[] args) throws InterruptedException {
        // 静态实现(饿汉式)
        // 1. 在没有使用的时候造成浪费
        // 2. 基于 classloader 机制避免了多线程的同步问题，线程安全；
        // 3. 没有加锁，调用效率高。
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Single01 instance = Single01.getInstance();
                System.out.println(instance);
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("------------------------------");

        // 懒汉式
        // 1. 延迟加载，没有内存浪费
        // 2. 线程安全
        // 3. 加锁，效率不高
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Single03 instance = Single03.getInstance();
                System.out.println(instance);
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("------------------------------");

        // 双重检测锁
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Single03 instance = Single03.getInstance();
                System.out.println(instance);
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("------------------------------");

        // 静态内部类
        // 1. 利用了classloader机制来保证初始化 instance 时只有一个线程，线程安全；
        // 2. 显示调用getInstance方法时，才会装载静态内部类(延迟加载)
        // 3. 没有加锁, 调用效率高
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Single04 instance = Single04.getInstance();
                System.out.println(instance);
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("------------------------------");

        // 枚举
        // 1. 这是实现单例模式的最佳方法。它更简洁.
        // 2. 避免多线程同步问题
        // 3. 自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化
        // 4. 但是不是延迟加载的。
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Single05 instance = Single05.INSTANCE;
                System.out.println(instance);
            }).start();
        }
    }

}
