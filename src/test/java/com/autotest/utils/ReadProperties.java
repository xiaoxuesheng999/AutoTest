package com.autotest.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
        /**
         * 读取properties文件
         *
         * @param filePath properties文件的路径
         * @return Properties对象，包含文件中的所有键值对
         * @throws IOException 如果读取文件时发生错误
         */
        public static Properties loadProperties(String filePath) throws IOException {
            Properties properties = new Properties();
            try (InputStream inputStream = new FileInputStream(filePath)) {
                // 从输入流中加载属性列表（键和元素对）
                properties.load(inputStream);
            }
            return properties;
        }

        /**
         * 读取并获取指定键的值
         *
         * @param filePath properties文件的路径
         * @param key 要获取的值的键
         * @return 对应键的值，如果键不存在则返回null
         * @throws IOException 如果读取文件时发生错误
         */
        public static String getValue(String filePath, String key) throws IOException {
            Properties properties = loadProperties(filePath);
            return properties.getProperty(key);
        }
/*        public static void main(String[] args) {
            String filePath = "D:\\workspace\\Idea-workspace\\xiaohe\\MyApiTest\\src\\main\\resources\\extract.properties"; // 修改为你的properties文件路径
            try {
                // 读取整个Properties对象
                Properties properties = loadProperties(filePath);
                System.out.println(properties);

                // 读取指定键的值
                String someValue = getValue(filePath, "accessToken");
                System.out.println("Value of 'someKey': " + someValue);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
}

