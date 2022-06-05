package com.maccoy;

import com.maccoy.domain.School;
import com.maccoy.prop.SpringBootPropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties(SpringBootPropertiesConfiguration.class)
@ConditionalOnProperty(prefix = "custom.starter", value = "enabled", havingValue = "true", matchIfMissing = true)
public class CustomStarterAutoConfiguration {

    @Autowired
    private SpringBootPropertiesConfiguration springBootPropertiesConfiguration;

    @Bean("school")
    public School getSchool() {
        return springBootPropertiesConfiguration.getSchool();
    }
}
