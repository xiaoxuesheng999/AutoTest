package com.test.fastjson;

import com.alibaba.fastjson.JSONObject;

public class JsonStringTransformObject {
    public static void main(String[] args) {
        String parameters="{\"name\":\"root\",\"age\":\"12\"}";
        Person preson = JSONObject.parseObject(parameters, Person.class);
        System.out.println(preson);
    }
}
