package io.kimmking.kmq.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@Builder
@ToString
@AllArgsConstructor
public class KmqMessage<T> {

    private Map<String, Object> headers;

    private T body;

}