package com.test.fastjson;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class JsonStringTransformMap {
    public static void main(String[] args) {

        String parameters="{\"username\":\"root\",\"password\":\"123456\"}";
        //params是一个双列唯一集合
        HashMap<String, String> params = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(parameters);
        System.out.println("jsonObject对象:"+jsonObject);
        Set<String> keys = jsonObject.keySet();
        System.out.println("关于只有keys的Set集合:"+keys);
        for (String key : keys) {
            params.put(key,jsonObject.get(key).toString());
        }
        Set<String> username = params.keySet();
        System.out.println("双列集合params:"+params);
        for (String s : username) {
            System.out.println("key="+s+",value="+params.get(s));
        }
    }
}
