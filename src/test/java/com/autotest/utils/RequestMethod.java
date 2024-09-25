package com.autotest.utils;

import com.alibaba.fastjson.JSONObject;
import com.autotest.entity.YamlModel;
import io.qameta.allure.Allure;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RequestMethod {
    public static Logger logger = Logger.getLogger(RequestMethod.class);

    public static String Get(YamlModel data) throws Exception {
        HttpGet request=null;
        String str="";
        // 创建HttpClient实例
        HttpClient client = HttpClients.createDefault();
        // 创建HttpGet实例，指定请求的URL
        if(data.getParameters()!=null){
            for (Map.Entry<String, String> stringObjectEntry : data.getParameters().entrySet()) {
                str+=stringObjectEntry.getKey()+"="+stringObjectEntry.getValue()+"&";
            }
            Allure.attachment("请求参数",str.substring(0,str.length()-1));
            str = "?"+str.substring(0,str.length()-1);
        }
        request = new HttpGet(data.getUrl()+str);
        //设置请求头
        Allure.attachment("请求头:",data.getHeaders().toString());
        for (Map.Entry<String, String> header : data.getHeaders().entrySet()) {
            request.setHeader(header.getKey(), header.getValue().toString());
        }
        // 执行请求并获取响应
        HttpResponse response = client.execute(request);
        // 检查响应状态是否为200
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            throw new Exception("Failed : HTTP error code : " + statusCode);
        }
        // 读取响应体内容
        String responseBody = EntityUtils.toString(response.getEntity());
        Allure.attachment("响应体:",responseBody);
        // 释放连接
        EntityUtils.consume(response.getEntity());
        logger.info("response:"+responseBody);
        return responseBody;
    }

    public static String Post(YamlModel data) throws Exception{
        String result="";
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            String url = data.getUrl();
            // 创建HttpPost实例
            HttpPost httpPost = new HttpPost(url);
            // 判断是发送表单数据还是JSON数据
            Allure.attachment("请求参数",data.getParameters().toString());
            Allure.attachment("请求头:",data.getHeaders().toString());
            if (data.getHeaders().getOrDefault("Content-Type", "application/x-www-form-urlencoded").equals("application/x-www-form-urlencoded")) {
                // 创建表单参数列表
                List<NameValuePair> formParams = new ArrayList<>();
                for (String s: data.getParameters().keySet()) {
                    formParams.add(new BasicNameValuePair(s, data.getParameters().get(s).toString()));
                }
                logger.info("表单参数-----:"+formParams);
                // 设置表单参数
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8);
                httpPost.setEntity(entity);
            } else if (data.getHeaders().getOrDefault("Content-Type", "").equals("application/json")) {
                // 转换为JSON字符串
                JSONObject params = new JSONObject();
                for (String s : data.getParameters().keySet()) {
                    params.put(s,data.getParameters().get(s));
                }
                logger.info("json参数" +params);
                StringEntity entity = new StringEntity(params.toString(), StandardCharsets.UTF_8);
                logger.info("entity:"+entity);
                httpPost.setEntity(entity);
            }
            //请求头设置
            for (Map.Entry<String, String> header : data.getHeaders().entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
            // 发送POST请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            // 检查响应状态是否为200
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new Exception("Failed : HTTP error code : " + statusCode);
            }
            // 获取响应体内容
            try {
                // 获取响应体
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    result=EntityUtils.toString(responseEntity);
                    logger.info("response:"+result);
                    Allure.attachment("响应体:",result);
                    return result ;
                }
            }finally {
                // 关闭响应
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                // 关闭HttpClient
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return result;
    }

    public static String Put(YamlModel data)throws Exception{
        String result="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = data.getUrl();
        HttpPut httpPut = new HttpPut(url);
        try {
            //请求头设置
            for (Map.Entry<String, String> header : data.getHeaders().entrySet()) {
                httpPut.setHeader(header.getKey(), header.getValue());
            }
            // 设置请求体
            JSONObject params = new JSONObject();
            for (String s : data.getParameters().keySet()) {
                params.put(s,data.getParameters().get(s));
            }
            logger.info("json参数" +params);
            Allure.attachment("json参数",params.toString());
            StringEntity entity = new StringEntity(params.toString(), StandardCharsets.UTF_8);
            httpPut.setEntity(entity);
            // 执行请求
            CloseableHttpResponse response = httpClient.execute(httpPut);
            // 检查响应状态是否为200
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new Exception("Failed : HTTP error code : " + statusCode);
            }
            try {
                // 获取响应体
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    result=EntityUtils.toString(responseEntity);
                    logger.info("response:"+result);
                    Allure.attachment("响应体:",result);
                    return result ;
                }
            } finally {
                // 关闭响应
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭HttpClient
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String Delete(YamlModel data)throws Exception{
        String str="";
        // 创建HttpGet实例，指定请求的URL
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if(data.getParameters()!=null){
            for (Map.Entry<String, String> stringObjectEntry : data.getParameters().entrySet()) {
                str+=stringObjectEntry.getKey()+"="+stringObjectEntry.getValue()+"&";
            }
            Allure.attachment("请求参数",str.substring(0,str.length()-1));
            str =data.getUrl()+ "?"+str.substring(0,str.length()-1);
        }
        logger.info("请求路径:" +str);
        Allure.attachment("请求路径",str);
        HttpDelete httpDelete = new HttpDelete(str);
        //请求头设置
        for (Map.Entry<String, String> header : data.getHeaders().entrySet()) {
            httpDelete.setHeader(header.getKey(), header.getValue());
        }
        try {
            // 执行DELETE请求
            CloseableHttpResponse response = httpClient.execute(httpDelete);
            // 检查响应状态是否为200
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new Exception("Failed : HTTP error code : " + statusCode);
            }
            try {
                // 获取响应体内容，注意：对于DELETE请求，服务器可能不返回响应体
                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("response:"+responseBody);
                Allure.attachment("响应体:",responseBody);
                return responseBody;
            } finally {
                // 关闭响应
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭HttpClient
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 如果没有返回响应体或者发生异常，返回null或错误信息
        return null;
    }
}

//    public static void main(String[] args) {
//    }

