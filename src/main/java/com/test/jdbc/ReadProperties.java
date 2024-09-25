package com.test.jdbc;

import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            /*
            获取方法一：
            FileInputStream fi = new FileInputStream("D:\\workspace\\Idea-workspace\\xiaohe\\MyApiTest\\src\\main\\java\\com\\test\\jdbc\\jdbc.properties");
            properties.load(fi);
            fi.close();
            */
            //获取方法二：
            properties.load(ReadProperties.class.getClassLoader().getResourceAsStream("test/properties/jdbc.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
        String url=properties.getProperty("jdbc.url");
        String username=properties.getProperty("jdbc.username");
        String password=properties.getProperty("jdbc.password");
        System.out.println("username:"+username);
        System.out.println("url:"+url);
        System.out.println("password:"+password);
    }
}
