package com.maccoy.springboot.project.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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