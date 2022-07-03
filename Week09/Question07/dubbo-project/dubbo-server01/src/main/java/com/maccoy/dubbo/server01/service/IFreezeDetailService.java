package com.maccoy.dubbo.server01.service;

import com.maccoy.dubbo.server01.domain.FreezeDetail;
public interface IFreezeDetailService {


    int deleteByPrimaryKey(Long id);

    int insert(FreezeDetail record);

    int insertSelective(FreezeDetail record);

    FreezeDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FreezeDetail record);

    int updateByPrimaryKey(FreezeDetail record);

    int handleFreezeDetail(Long userId, Long amount, Integer accountType);


}
