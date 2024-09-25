package com.test.fastjson;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class JsonStringTransformMap {
    public static void main(String[] args) {

        String parameters="{\"username\":\"root\",\"password\":\"123456\"}";
        HashMap<String, String> params = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(parameters);
        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            params.put(key,jsonObject.get(key).toString());
        }
        Set<String> username = params.keySet();
        for (String s : username) {
            System.out.println("key="+s+",value="+params.get(s));
        }
    }
}
