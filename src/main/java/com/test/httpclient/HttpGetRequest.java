package com.test.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpGetRequest {
    public static void main(String[] args) {
        String url = "http://127.0.0.1:8888/getwithparam";
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "xiaohe");
        params.put("age","34");
        String res = getRequest(url, params);
        System.out.println("获取到的结果为：" + res);

    }
    public static String getRequest(String url, Map<String,String> params){
        String res = "";
        boolean flag = true;
        Set<String> keys = params.keySet();
        for (String key : keys) {
            if (flag){
                url += "?" + key + "=" + params.get(key);
                flag = false;
            }else {
                url += "&" + key + "=" + params.get(key);
            }
        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpResponse response = httpClient.execute(httpGet);
            res = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
