package com.maccoy.advanced.spring;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class SpringBean03 {

    private String name = "Configuration";

    @Bean
    public SpringBean04 getBean() {
        return new SpringBean04();
    }

}
