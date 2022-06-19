package com.maccoy.springboot.project.service.impl;

import com.maccoy.springboot.project.annotation.MasterSlave;
import com.maccoy.springboot.project.domain.Orders;
import com.maccoy.springboot.project.mapper.OrdersMapper;
import com.maccoy.springboot.project.service.IOrdersService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @MasterSlave(name = "master")
    @Override
    public int insertOne(Orders orders) {
        return ordersMapper.insertSelective(orders);
    }

    @MasterSlave(name = "slave1")
    @Override
    public Orders queryById(Long id) {
        return ordersMapper.selectByPrimaryKey(id);
    }

}
