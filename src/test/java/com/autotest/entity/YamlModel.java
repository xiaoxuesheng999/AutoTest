package com.autotest.entity;

import lombok.Data;

import java.util.HashMap;
@Data
public class YamlModel {
        private String caseId;
        private String apiName;
        private String describe;
        private String url;
        private String requestType;
        private HashMap<String,String> headers;
        private String cookies;
        private HashMap<String,String> parameters;
        private String uploadFile;
        private String initSql;
        private HashMap<String,String> extractVariables;
        private String expectedResults;
        private HashMap<String, String> actualResults;

}

