package com.test.yaml;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

//public class ReadDataFromYaml {
//
//    public static void main(String[] args) {
//
//        Preson p = new Preson();
//        Preson result = getData("D:\\workspace\\Idea-workspace\\xiaohe\\MyApiTest\\src\\main\\java\\com\\test\\yaml\\person.yaml", p);
//        System.out.println(result);
//        if (result != null) {
//            System.out.println(result.getId());
//            System.out.println(result.getName());
//            System.out.println(result.getAge());
//        }
//    }
//
//        public static Preson getData (String path, Preson p){
//            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
//
//            try {
//                FileInputStream fileInputStream = new FileInputStream(path);
//                return objectMapper.readValue(fileInputStream, p.getClass());
//            } catch (IOException e) {
//                System.out.println("读取异常：" + e);
//            }
//            return null;
//        }
//    }
public class ReadDataFromYaml {
    public static void main(String[] args) {
        Person p = getData("test/yaml/person.yaml", Person.class);
        System.out.println(p);
        if (p!=null){
            System.out.println(p.getId());
            System.out.println(p.getName());
            System.out.println(p.getAge());
        }
    }
    public static <T> T getData(String path, Class<T> clazz){
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            T obj = objectMapper.readValue(ReadDataFromYaml.class.getClassLoader().getResourceAsStream(path), clazz);
            return obj;
        } catch (Exception e) {
            System.out.println("异常：" + e);
        }
        return null;
    }


}

