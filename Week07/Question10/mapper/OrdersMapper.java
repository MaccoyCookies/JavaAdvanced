package com.maccoy.sharding.project.mapper;


import com.maccoy.sharding.project.domain.Orders;

import java.util.List;

public interface OrdersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    int insertBatch(List<Orders> ordersList);

    List<Orders> list();

}