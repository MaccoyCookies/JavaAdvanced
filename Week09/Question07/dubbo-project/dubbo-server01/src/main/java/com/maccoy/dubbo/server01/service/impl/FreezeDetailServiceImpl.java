package com.maccoy.dubbo.server01.service.impl;

import com.maccoy.dubbo.server01.domain.FreezeDetail;
import com.maccoy.dubbo.server01.mapper.FreezeDetailMapper;
import com.maccoy.dubbo.server01.service.IFreezeDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class FreezeDetailServiceImpl implements IFreezeDetailService {

    @Resource
    private FreezeDetailMapper freezeDetailMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return freezeDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(FreezeDetail record) {
        return freezeDetailMapper.insert(record);
    }

    @Override
    public int insertSelective(FreezeDetail record) {
        return freezeDetailMapper.insertSelective(record);
    }

    @Override
    public FreezeDetail selectByPrimaryKey(Long id) {
        return freezeDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(FreezeDetail record) {
        return freezeDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(FreezeDetail record) {
        return freezeDetailMapper.updateByPrimaryKey(record);
    }

    @Override
    public int handleFreezeDetail(Long userId, Long amount, Integer accountType) {
        return freezeDetailMapper.handleFreezeDetail(userId, amount, accountType);
    }
}
