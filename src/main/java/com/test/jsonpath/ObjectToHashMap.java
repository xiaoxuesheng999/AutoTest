package com.test.jsonpath;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.util.HashMap;
import java.util.List;

public class ObjectToHashMap {
    public static void main(String[] args) {
        String res = "{\"code\":9420, \"msg\":\"恭喜qzcsbj，登录成功\",\"token\":\"538bbaba44be5d3d3856718e6c637d02\"}";
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(res);
        String token = JsonPath.read(document, "$.token");
        System.out.println("token: " + token);

        String res2 = "{\"code\":\"0\",\"msg\":\"sucess\",\"data\":[{\"username\":\"韧\",\"realname\":\"tester1\",\"sex\":\"1\",\"phone\":\"13800000001\"},{\"username\":\"全栈测试笔记\",\"realname\":\"tester2\",\"sex\":\"1\",\"phone\":\"13800000002\"}]}";
        Object document2 = Configuration.defaultConfiguration().jsonProvider().parse(res2);
        String phone = JsonPath.read(document2, "$.data[0].phone");
        System.out.println("phone: " + phone);

        List<Object> arr = JsonPath.read(document2, "$.data[*].phone");  // jsonpath不能写成：$.data[*].phone[?(@.username=='韧')]
        System.out.println("arr: " + arr.toString());

        List<Object> arr2 = JsonPath.read(document2, "$.data[*].['username','phone']");
        boolean flag = true;
        System.out.println("arr2: " + arr2.toString());

        List<HashMap> maps = JSONObject.parseArray(JSONObject.toJSONString(arr2),HashMap.class);  // 第一个参数也可以写为：arr2.toString()；第二个参数是字节码，说明源码用到了反射
        for (HashMap map : maps) {
            if ("韧".equals(map.get("username"))){
                System.out.println(map.get("username")+"的phone是：" + map.get("phone"));
                flag = false;
                break;
            }
        }

        if (flag){
            System.out.println("未获取到【韧】的phone");
        }

    }
}

