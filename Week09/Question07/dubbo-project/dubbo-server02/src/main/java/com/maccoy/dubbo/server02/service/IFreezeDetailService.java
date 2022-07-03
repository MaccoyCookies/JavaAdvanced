package com.maccoy.dubbo.server02.service;

import com.maccoy.dubbo.server02.domain.FreezeDetail;

public interface IFreezeDetailService {


    int deleteByPrimaryKey(Long id);

    int insert(FreezeDetail record);

    int insertSelective(FreezeDetail record);

    FreezeDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FreezeDetail record);

    int updateByPrimaryKey(FreezeDetail record);

    int handleFreezeDetail(Long userId, Long amount, Integer accountType);

}
