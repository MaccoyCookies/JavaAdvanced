package com.maccoy.dubbo.server02.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    /**
    * 主键 自增列
    */
    private Long id;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 账户类型：1 人民币账户，2 美元账户
    */
    private Byte accountType;

    /**
    * 余额 单位 分
    */
    private Long balance;

    /**
    * 用户注册时间
    */
    private Date createTm;

    /**
    * 最后修改时间
    */
    private Date updateTm;
}