package com.maccoy.springboot.project.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DataSourceProperties {

    private Map<String, String> master;

    private Map<String, String> slave1;

}
