package com.autotest.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class YamlToObj {
    public static Logger logger = Logger.getLogger(YamlToObj.class);
    public static  <T> List<T> getData(String path, Class<T> elementClass){
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, elementClass);
            List<T> list = objectMapper.readValue(YamlToObj.class.getClassLoader().getResourceAsStream(path), javaType);
            logger.info("返回的Yaml对象:"+list);
            return list;
        } catch (IOException e) {
            System.out.println("读取异常：" + e);
        }
        return null;
    }
    //将提取数据写入extract.properties的方法
/*
    public static void writeData(ArrayList<BasicNameValuePair> extract) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\workspace\\Idea-workspace\\xiaohe\\MyApiTest\\src\\main\\resources\\extract.properties"));
        for (BasicNameValuePair basicNameValuePair : extract) {
            bw.write(basicNameValuePair.toString());
            bw.flush();
        }
        bw.close();
    }*/

}

