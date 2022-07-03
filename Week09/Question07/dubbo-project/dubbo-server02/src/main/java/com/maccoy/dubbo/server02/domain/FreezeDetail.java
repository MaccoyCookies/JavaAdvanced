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
public class FreezeDetail {
    /**
    * 主键 自增列
    */
    private Long id;

    /**
    * 冻结记录编码
    */
    private String freezeCode;

    /**
    * 用户编号
    */
    private Long userId;

    /**
    * 账户类型：1 人民币账户，2 美元账户
    */
    private Byte accountType;

    /**
    * 冻结状态 1冻结 2解冻
    */
    private Byte freezeStatus;

    /**
    * 冻结金额 单位 分
    */
    private Long amount;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 最后修改时间
    */
    private Date updateTime;
}