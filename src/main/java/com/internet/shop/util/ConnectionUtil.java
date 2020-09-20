package com.internet.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException("Can not find MySql driver ", exception);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "shopDB");
        dbProperties.put("password", "shopPassword");
        String url = "jdbc:mysql://localhost:3306";
        try {
            Connection connection = DriverManager.getConnection(url, dbProperties);
            return connection;
        } catch (SQLException throwables) {
            throw new RuntimeException("Can not establish connection to DB ", throwables);
        }
    }
}
