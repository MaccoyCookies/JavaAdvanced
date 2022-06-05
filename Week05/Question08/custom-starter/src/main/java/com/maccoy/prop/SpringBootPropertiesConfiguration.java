package com.maccoy.prop;

import com.maccoy.domain.School;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "custom.starter")
public class SpringBootPropertiesConfiguration {

    private School school;

    private String testName;

}
