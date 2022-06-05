package com.maccoy.advanced.spring.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HikariDemo {

    private Connection connection;
    private PreparedStatement preparedStatement;

    private void initDataSource() {
        try {
            HikariConfig config = new HikariConfig("/hikari.properties");
            HikariDataSource dataSource = new HikariDataSource(config);
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insert(String sql, String ... insertParams) throws SQLException {
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql, insertParams);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
            System.out.println("Insert successfully");
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        return false;
    }

    public List<Map<String, Object>> query(String sql, List<String> columns, String ... queryParams) {
        try {
            preparedStatement = connection.prepareStatement(sql, queryParams);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> list = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> result = new HashMap<>();
                for (String column : columns) {
                    result.put(column, resultSet.getObject(column));
                }
                list.add(new HashMap<>(result));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void close() throws SQLException {
        preparedStatement.close();
        connection.close();
        System.out.println("Connection close");
    }

    public static void main(String[] args) {
        List<String> columns = Arrays.asList("id", "name", "age");
        try {
            HikariDemo hikariDemo = new HikariDemo();
            hikariDemo.initDataSource();
            String insertSql = "insert into student values(?, ?, ?)";
            hikariDemo.insert(insertSql, null, "张三", "20");

            String selectSql = "select * from student";
            List<Map<String, Object>> results = hikariDemo.query(selectSql, columns, null);
            for (Map<String, Object> r : results) {
                System.out.println(r.toString());
            }
            hikariDemo.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
