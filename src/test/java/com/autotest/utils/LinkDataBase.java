package com.autotest.utils;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Properties;

public class LinkDataBase {

    // 私有构造函数，防止实例化
    private LinkDataBase() {
    }

    // 获取数据库连接
    public static Connection getConnection(String path) throws SQLException, IOException {
        // 数据库URL、用户名和密码
        Properties properties = new Properties();
        properties.load(LinkDataBase.class.getClassLoader().getResourceAsStream(path));
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }


    public static String executeQuery(String sql, String path, ResultSetHandler handler) throws IOException {
        String result="";
        try (Connection conn = getConnection(path);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             result=handler.handle(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // ResultSet处理器接口
    public interface ResultSetHandler {
        String handle(ResultSet rs) throws SQLException;
    }
    // 使用示例
    public static void main(String[] args) throws IOException {
        String sql = "SELECT * FROM user_info WHERE username='xiaohe'";
        LinkDataBase.executeQuery(sql, "enviroments.properties", rs -> {
            try {
                LinkedHashMap<String, String> stringStringHashMap = new LinkedHashMap<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String sid = Integer.toString(id);
                    String name = rs.getString("username");
                    int age = rs.getInt("age");
                    String sage=Integer.toString(age);
                    String gender = rs.getString("gender");
                    String addr = rs.getString("addr");
                    stringStringHashMap.put("id",sid);
                    stringStringHashMap.put("name",name);
                    stringStringHashMap.put("age",sage);
                    stringStringHashMap.put("gender",gender);
                    stringStringHashMap.put("addr",addr);
                    System.out.println(stringStringHashMap);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "ok";
        });
    }
}