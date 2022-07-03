package com.maccoy.dubbo.server01.mapper;

import com.maccoy.dubbo.server01.domain.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    int handleAccountBalance(@Param("userId") Long userId, @Param("amount") Long amount, @Param("accountType") Integer accountType);

}