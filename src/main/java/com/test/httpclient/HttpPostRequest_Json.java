package com.test.httpclient;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Set;

public class HttpPostRequest_Json {
    // 测试方法
    public static void main(String[] args) {
        // post：json
        String url2 = "http://127.0.0.1:8888/postwithjson";
        String parameters = "{\"name\":\"xiaohe\",\"age\":\"34\"}";
        String headers = "{\"Content-Type\":\"application/json\"}";
        JSONObject paramJsonObject = JSONObject.parseObject(parameters);
        JSONObject headersJsonObject = JSONObject.parseObject(headers);
        String res2 = postRequest(url2, paramJsonObject,headersJsonObject);
        System.out.println("获取到的结果为：" + res2);
    }
    public static String postRequest(String url, JSONObject jsonObject, JSONObject headers){
        String res = "";
        HttpPost httpPost = new HttpPost(url);
        // 通过形参设置请求头
        Set<String> headerkeys = headers.keySet();
        for (String headerkey : headerkeys) {
            httpPost.addHeader(headerkey.trim(),headers.getString(headerkey).trim());
        }

        // 发送 json 类型数据
        httpPost.setEntity(new StringEntity(jsonObject.toString(),"UTF-8"));

        HttpClient httpClient = HttpClients.createDefault();
        // 发送请求
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            System.out.println("状态码：" + httpResponse.getStatusLine().getStatusCode());
            res = EntityUtils.toString(httpResponse.getEntity());
            // res = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


}
