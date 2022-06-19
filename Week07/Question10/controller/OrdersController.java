package com.maccoy.sharding.project.controller;

import com.maccoy.sharding.project.domain.Orders;
import com.maccoy.sharding.project.mapper.OrdersMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersMapper ordersMapper;

    @PostMapping("/list")
    public List<Orders> list() {
        return ordersMapper.list();
    }

    @PostMapping("/add")
    public String add(@RequestBody Orders orders) {
        ordersMapper.insertSelective(orders);
        return "success";
    }

    @PostMapping("/addAndList")
    public List<Orders> addAndList(@RequestBody Orders orders) {
        ordersMapper.insertSelective(orders);
        return ordersMapper.list();
    }

    @PostMapping("/masterList")
    public List<Orders> masterList() {
        try (
            HintManager hintManager = HintManager.getInstance();
        ) {
            hintManager.setMasterRouteOnly();
            return ordersMapper.list();
        }
    }

}
