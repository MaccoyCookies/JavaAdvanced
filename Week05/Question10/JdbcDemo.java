package com.maccoy.advanced.spring.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcDemo {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    private void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mall", "root", "root");
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Connection failed!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
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
            JdbcDemo jdbcDemo = new JdbcDemo();
            jdbcDemo.createConnection();
            String insertSql = "insert into student values(?, ?, ?)";
            jdbcDemo.insert(insertSql, null, "张三", "20");

            String selectSql = "select * from student";
            List<Map<String, Object>> results = jdbcDemo.query(selectSql, columns, null);
            for (Map<String, Object> r : results) {
                System.out.println(r.toString());
            }
            jdbcDemo.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
