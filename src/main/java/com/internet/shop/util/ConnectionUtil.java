package com.internet.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException("Can not find MySql driver ", exception);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("username", "root");
        dbProperties.put("password", "root");
        String url = "jdbc:mysql://localhost:3306";
        try {
            return DriverManager.getConnection(url, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can not establish connection to DB ", throwables);
        }
    }
}
