package com.gl.util;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PayFastUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/payfast?currentSchema=java-kafka-integration";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    static AtomicInteger counter = new AtomicInteger();
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
    public static int generateUniqueId(String columnName, String tableName, int initialValue) {
        Connection conn = getConnection();
        int uniqueId = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(" + columnName + ") AS max_id FROM " + tableName);
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                counter = new AtomicInteger(maxId > 0 ? maxId : initialValue);
            } else {
                counter = new AtomicInteger(initialValue);
            }
            uniqueId = counter.incrementAndGet();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uniqueId;
    }
}
