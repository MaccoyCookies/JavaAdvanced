package com.maccoy.advanced.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContext {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ITransaction transactionService = (ITransaction) applicationContext.getBean("transactionService");
        transactionService.testAop();
        System.out.println(transactionService);
        System.out.println(transactionService.getClass());

        // ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // // method01
        // SpringBean01 springBean01 = (SpringBean01) applicationContext.getBean("springBean01");
        // System.out.println("@Component: " + springBean01);
        //
        // // method05
        // SpringBean05 springBean05 = (SpringBean05) applicationContext.getBean("springBean05");
        // System.out.println("xmlConfig: " + springBean05);
        //
        // // method06
        // SpringBean06 springBean06 = (SpringBean06) applicationContext.getBean("springBean06");
        // System.out.println("Configuration: " + springBean06);
        //
        // // method07
        // System.out.println("getBean: " + springBean06.getBean());
        //
        // Student student01 = (Student) applicationContext.getBean("student01");
        // Student student02 = (Student) applicationContext.getBean("student02");
        // System.out.println("student01 = " + student01);
        // System.out.println("student02 = " + student02);


    }

}
