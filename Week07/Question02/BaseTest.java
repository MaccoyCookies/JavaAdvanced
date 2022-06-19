package com.maccoy.springboot.project;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.maccoy.springboot.project.domain.Orders;
import com.maccoy.springboot.project.mapper.OrdersMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {


    @Autowired
    private OrdersMapper ordersMapper;


    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @Test
    public void test() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(128),
                new ThreadPoolExecutor.CallerRunsPolicy());
        int batch = 50_0000;
        long method01Start = System.currentTimeMillis();
        // method01
        for (int i = 0; i < batch; i++) {
            ordersMapper.insertSelective(Orders.builder()
                    .userId(RandomUtil.randomLong(1, 100 + 1))
                    .status(RandomUtil.randomInt(1, 2 + 1))
                    .totalPrice(RandomUtil.randomBigDecimal(BigDecimal.ONE, new BigDecimal("10000")))
                    .createTm(DateUtil.date())
                    .updateTm(DateUtil.date()).build());
        }
        log.info("method01: {}", System.currentTimeMillis() - method01Start);

        // method02
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        OrdersMapper sqlSessionMapper = sqlSession.getMapper(OrdersMapper.class);
        long method02Start = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            sqlSessionMapper.insertSelective(Orders.builder()
                    .userId(RandomUtil.randomLong(1, 100 + 1))
                    .status(RandomUtil.randomInt(1, 2 + 1))
                    .totalPrice(RandomUtil.randomBigDecimal(BigDecimal.ONE, new BigDecimal("10000")))
                    .createTm(DateUtil.date())
                    .updateTm(DateUtil.date()).build());
            if (i % 10000 == 0) {
                sqlSession.commit();
            }
        }
        sqlSession.commit();
        log.info("method02: {}", System.currentTimeMillis() - method02Start);

        // method03
        long method03Start = System.currentTimeMillis();
        List<Orders> ordersList = new ArrayList<>(50_0000);
        for (int i = 0; i < batch; i++) {
            ordersList.add(Orders.builder()
                    .userId(RandomUtil.randomLong(1, 100 + 1))
                    .status(RandomUtil.randomInt(1, 2 + 1))
                    .totalPrice(RandomUtil.randomBigDecimal(BigDecimal.ONE, new BigDecimal("10000")))
                    .createTm(DateUtil.date())
                    .updateTm(DateUtil.date()).build());
            if (ordersList.size() % 2000 == 0) {
                ordersMapper.insertBatch(ordersList);
                ordersList.clear();
            }
        }
        if (CollUtil.isNotEmpty(ordersList)) {
            ordersMapper.insertBatch(ordersList);
        }
        log.info("method03: {}", System.currentTimeMillis() - method03Start);

        // method04
        long method04Start = System.currentTimeMillis();
        List<CompletableFuture<Integer>> taskList = new ArrayList<>(16);
        List<Orders> method04OrdersList = new ArrayList<>(100_0000);
        for (int i = 0; i < batch; i++) {
            method04OrdersList.add(Orders.builder()
                    .userId(RandomUtil.randomLong(1, 100 + 1))
                    .status(RandomUtil.randomInt(1, 2 + 1))
                    .totalPrice(RandomUtil.randomBigDecimal(BigDecimal.ONE, new BigDecimal("10000")))
                    .createTm(DateUtil.date())
                    .updateTm(DateUtil.date()).build());
            if (method04OrdersList.size() % 2000 == 0) {
                CompletableFuture.supplyAsync(() -> {
                    ordersMapper.insertBatch(Lists.newArrayList(method04OrdersList));
                    method04OrdersList.clear();
                    return 1;
                });
            }
        }
        if (CollUtil.isNotEmpty(method04OrdersList)) {
            ordersMapper.insertBatch(method04OrdersList);
        }
        try {
            for (CompletableFuture<Integer> integerCompletableFuture : taskList) {
                integerCompletableFuture.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        log.info("method04: {}", System.currentTimeMillis() - method04Start);



    }
}
