package com.george.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseUtil {
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            // 加载数据库配置
            Properties props = new Properties();
            InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (input != null) {
                props.load(input);
                url = props.getProperty("jdbc.url");
                user = props.getProperty("jdbc.user");
                password = props.getProperty("jdbc.password");
            }
            // 加载MySQL驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}