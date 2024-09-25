package com.test.yaml;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadDateFormYamlUtil {
    public static  <T> List<T> getData(String path, Class<T> elementClass){
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, elementClass);
            List<T> list = objectMapper.readValue(ReadDataFromYaml.class.getClassLoader().getResourceAsStream(path), javaType);
            return list;
        } catch (IOException e) {
            System.out.println("读取异常：" + e);
        }
        return null;
    }

    public static void main(String[] args) {
        List<Person> plist = new ArrayList<Person>();
        List<Person> plist1 = getData("test/yaml/person.yaml",Person.class);

        if ( plist1!=null){

            plist.addAll(plist1);
        }
        List<Person> plist2 = getData("test/yaml/person2.yaml",Person.class);
        if ( plist2!=null){
            plist.addAll(plist2);
        }
        System.out.println(plist.get(0).getName());
    }

}

