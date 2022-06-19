package com.maccoy.sharding.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Long id;

    private Long userId;

    private Integer status;

    private BigDecimal totalPrice;

    private Date createTm;

    private Date updateTm;
}