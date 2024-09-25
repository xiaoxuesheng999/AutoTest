package com.autotest.utils;

import com.alibaba.fastjson.JSONObject;
import com.autotest.entity.YamlModel;
import io.qameta.allure.Allure;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestructureData {
    public static Logger logger = Logger.getLogger(RestructureData.class);
    private static String filePath = "D:\\workspace\\Idea-workspace\\xiaohe\\MyApiTest\\src\\test\\resources\\enviroments.properties";
    public YamlModel yamlTransformObj(String yamlpath) throws IOException {
        String url = ReadProperties.getValue(filePath, "url");
        List<YamlModel> caseData = YamlToObj.getData(yamlpath, YamlModel.class);
        YamlModel data = caseData.get(0);
        //重组全地址
        String new_url=url+data.getUrl();
        Allure.attachment("请求地址：",new_url);
        data.setUrl(new_url);
        return data;
    }
    public YamlModel replace(YamlModel data, HashMap<String, String> hashMap,String[] str) throws IOException {
        int i=0;
        //重组数据
        String jsonString = JSONObject.toJSONString(data);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
            if(stringObjectEntry.getValue().toString().contains("${")){
                String s = JSONObject.toJSONString(stringObjectEntry.getValue().toString());
                for (Map.Entry<String, Object> objectEntry : JSONObject.parseObject(s).entrySet()) {
                    if(i<str.length){
                    String new_data = jsonString.replace(objectEntry.getValue().toString(),hashMap.get(str[i]) );
                    logger.info("替换后的数据："+new_data);
                    data = JSONObject.parseObject(new_data, YamlModel.class);
                    i++;}
                }
            }
        }
        return data;
    }
    public static void write(String filePath,String str) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write(str);
        bw.flush();
        bw.close();
    }
}
