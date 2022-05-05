package com.maccoy.advanced.jvm.homework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomerClassLoader extends ClassLoader {

    private static final String CLASS_NAME = "Hello";
    private static final String METHOD_NAME = "hello";
    private static final String FILE_SUFFIX = ".xlass";

    public static void main(String[] args) {
        try {
            Class<?> clazz = new CustomerClassLoader().loadClass(CLASS_NAME);
            for (Method m : clazz.getDeclaredMethods()) {
                System.out.println(clazz.getSimpleName() + "." + m.getName());
            }
            // 创建对象
            Object instance = clazz.getDeclaredConstructor().newInstance();
            // 调用实例方法
            Method method = clazz.getMethod(METHOD_NAME);
            method.invoke(instance);
            Thread.sleep(500000);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(name + FILE_SUFFIX);
        FileInputStream fis = null;
        // InputStream fis = this.getClass().getClassLoader().getResourceAsStream(name + FILE_SUFFIX);
        try {
            fis = new FileInputStream(file);
            int available = fis.available();
            byte[] bytes = new byte[available];
            fis.read(bytes);
            decode(bytes);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private byte[] decode(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return bytes;
    }
}
