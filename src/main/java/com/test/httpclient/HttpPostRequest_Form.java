package com.test.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpPostRequest_Form {
    public static void main(String[] args) {
        String url = "http://127.0.0.1:8888/postwithparam";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name","xiaohe");
        params.put("gender", "man");
        String res = postRequest(url, params);
        System.out.println("获取到的结果为：" + res);
    }

    public static String postRequest(String url, Map<String,String> params){
        String res = "";
        HttpPost httpPost = new HttpPost(url);
        ArrayList<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            basicNameValuePairs.add(new BasicNameValuePair(key,params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(basicNameValuePairs,"utf-8"));
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            res = EntityUtils.toString(httpResponse.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}

