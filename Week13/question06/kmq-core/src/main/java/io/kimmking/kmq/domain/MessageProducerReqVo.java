package io.kimmking.kmq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageProducerReqVo<T> {

    private String topic;

    private T body;

}