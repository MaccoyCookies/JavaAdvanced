package com.maccoy.springboot.project;

import com.maccoy.domain.School;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootProjectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootProjectApplication.class, args);
        School school = (School) applicationContext.getBean("school");
        System.out.println("school = " + school);
    }

}
