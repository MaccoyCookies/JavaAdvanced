package io.kimmking.kmq.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "message")
public class MessageProperties {

    private String consumerId;

    private Integer length;

    private List<String> topicName;

}