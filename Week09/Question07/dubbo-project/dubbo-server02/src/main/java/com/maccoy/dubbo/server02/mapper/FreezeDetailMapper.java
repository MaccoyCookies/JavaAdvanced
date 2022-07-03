package com.maccoy.dubbo.server02.mapper;

import com.maccoy.dubbo.server02.domain.FreezeDetail;
import org.apache.ibatis.annotations.Param;

public interface FreezeDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FreezeDetail record);

    int insertSelective(FreezeDetail record);

    FreezeDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FreezeDetail record);

    int updateByPrimaryKey(FreezeDetail record);

    int handleFreezeDetail(@Param("userId") Long userId, @Param("amount") Long amount, @Param("accountType") Integer accountType);
}