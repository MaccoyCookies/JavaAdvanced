package com.maccoy.springboot.project.service;

import com.maccoy.springboot.project.domain.Orders;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public interface IOrdersService {

    int insertOne(Orders orders);

    Orders queryById(Long id);
}
