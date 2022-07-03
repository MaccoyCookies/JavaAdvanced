package com.maccoy.rpc.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer userId;

    private String name;

    private Integer age;

    private Integer departId;


}

