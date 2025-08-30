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
            // 首先检查环境变量（Docker 部署时使用）
            String jdbcUrl = System.getenv("JDBC_URL");
            String jdbcUser = System.getenv("JDBC_USER");
            String jdbcPassword = System.getenv("JDBC_PASSWORD");

            if (jdbcUrl != null && jdbcUser != null && jdbcPassword != null) {
                // 使用环境变量配置
                url = jdbcUrl;
                user = jdbcUser;
                password = jdbcPassword;
                System.out.println("使用环境变量数据库配置: " + url);
            } else {
                // 使用配置文件（本地开发时使用）
                Properties props = new Properties();
                InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties");
                if (input != null) {
                    props.load(input);
                    url = props.getProperty("jdbc.url");
                    user = props.getProperty("jdbc.user");
                    password = props.getProperty("jdbc.password");
                    System.out.println("使用配置文件数据库配置: " + url);
                }
            }

            // 加载MySQL驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }
    public static Connection getConnection() throws SQLException {
        System.out.println("尝试连接数据库: " + url);
        System.out.println("使用用户: " + user);
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功!");
            return conn;
        } catch (SQLException e) {
            System.err.println("数据库连接失败: " + e.getMessage());
            throw e;
        }
    }
}