package com.autotest.utils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.apache.log4j.Logger;

import java.util.HashMap;


public class ExtractVariables {
    static Logger logger = Logger.getLogger(ExtractVariables.class);
    public static HashMap<String, String> extract(String res, HashMap<String, String> extr){
        HashMap<String, String> kvmap = new HashMap<>();
        for (String s : extr.keySet()) {
            Object o = extr.get(s);
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(res);
            logger.info("需要提取变量的原值:"+document);
            String variables = JsonPath.read(document, o.toString());
            kvmap.put(s,variables);
        }
        logger.info("提取的变量和值:"+kvmap);
        return kvmap;
    }
}
