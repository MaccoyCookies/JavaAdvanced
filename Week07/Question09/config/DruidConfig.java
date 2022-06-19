package com.maccoy.springboot.project.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.maccoy.springboot.project.config.prop.DataSourceProperties;
import com.maccoy.springboot.project.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class DruidConfig {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    public DataSource master() {
        DruidDataSource druidDataSource = new DruidDataSource();
        Map<String, String> master = dataSourceProperties.getMaster();
        druidDataSource.setUsername(master.get("username"));
        druidDataSource.setPassword(master.get("password"));
        druidDataSource.setUrl(master.get("url"));
        return druidDataSource;
    }

    @Bean
    public DataSource slave1() {
        DruidDataSource druidDataSource = new DruidDataSource();
        Map<String, String> slave = dataSourceProperties.getSlave1();
        druidDataSource.setUsername(slave.get("username"));
        druidDataSource.setPassword(slave.get("password"));
        druidDataSource.setUrl(slave.get("url"));
        return druidDataSource;
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource master, DataSource slave) {
        Map<Object, Object> map = new HashMap<>(4);
        map.put("master", master);
        map.put("slave1", slave);
        return new DynamicDataSource(master, map);
    }

}
