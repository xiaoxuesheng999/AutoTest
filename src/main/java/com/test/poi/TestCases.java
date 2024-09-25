package com.test.poi;

public class TestCases {
        private String caseId;
        private String describe;
        private String url;
        private String method;
        private String parameters;
        private String expect;
        private String actual;

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getParameters() {
            return parameters;
        }

        public void setParameters(String parameters) {
            this.parameters = parameters;
        }

        public String getExpect() {
            return expect;
        }

        public void setExpect(String expect) {
            this.expect = expect;
        }

        public String getActual() {
            return actual;
        }

        public void setActual(String actual) {
            this.actual = actual;
        }

        @Override
        public String toString() {
            return "TestCase{" +
                    "caseId='" + caseId + '\'' +
                    ", describe='" + describe + '\'' +
                    ", url='" + url + '\'' +
                    ", method='" + method + '\'' +
                    ", parameters='" + parameters + '\'' +
                    ", expect='" + expect + '\'' +
                    ", actual='" + actual + '\'' +
                    '}';
        }
}

