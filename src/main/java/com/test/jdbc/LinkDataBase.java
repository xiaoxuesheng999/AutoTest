package com.test.jdbc;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class LinkDataBase {
    public static void main(String[] args) {
//        String sql="insert user_info(id,username,age,gender,addr)values(7,'小兹',50,'男','上海')";
        String sql="SELECT * FROM user_info WHERE username=\"xiaohe\"";
        Properties properties = new Properties();
        try {
            properties.load(LinkDataBase.class.getClassLoader().getResourceAsStream("test/properties/jdbc.properties"));
            String url=properties.getProperty("jdbc.url");
            String username=properties.getProperty("jdbc.username");
            String password=properties.getProperty("jdbc.password");
            //获取Connection
            Connection connection = DriverManager.getConnection(url,username, password);
            //获取PrepareStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
//            while (resultSet.next()){
//                String usernameValue = resultSet.getObject("username").toString();
//                System.out.println("username:"+usernameValue);
//            }
            connection.close();
        }catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }
}
