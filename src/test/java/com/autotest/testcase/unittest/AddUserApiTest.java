package com.autotest.testcase.unittest;

import com.autotest.entity.YamlModel;
import com.autotest.testcase.Integrationtest.UserModuleTest;
import com.autotest.utils.AssertUtil;
import com.autotest.utils.ExtractVariables;
import com.autotest.utils.RequestMethod;
import com.autotest.utils.RestructureData;
import io.qameta.allure.*;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
@Epic("接口单元测试")
@Feature("添加用户接口")
public class AddUserApiTest {
    public static Logger logger = Logger.getLogger(UserModuleTest.class);
    private static String accessToken;
    private static Object reflect(YamlModel data) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String requestType = data.getRequestType();
        Allure.attachment("请求方法",requestType);
        requestType=requestType.substring(0, 1).toUpperCase() + requestType.substring(1);
        Method method = RequestMethod.class.getMethod(requestType,YamlModel.class);
        RequestMethod requestMethod = RequestMethod.class.getConstructor().newInstance();
        Object res = method.invoke(requestMethod, data);
        return res;
    }
    @Description("获取token令牌")
    @Test
    public static void login() throws InterruptedException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //获取yaml数据对象data
        YamlModel data = new RestructureData().yamlTransformObj("apicase/login.yaml");
        //传入对象请求接口，获取结果
        Object res = reflect(data);
        //提取变量
        if (data.getExtractVariables() != null) {
            HashMap<String, String> extractVariables = data.getExtractVariables();
            HashMap<String, String> extractstr = ExtractVariables.extract(res.toString(), extractVariables);
            AddUserApiTest.accessToken = extractstr.get("accessToken");
        }
        //断言结果
        AssertUtil.AssertRes(data, res.toString());
    }

    @Story("正反用例")
    @Test(dependsOnMethods = {"login"},dataProvider = "providerData")
    public static void adduser(String id,String name,String age,String gender,String addr) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        HashMap<String, String> newHashMap = new HashMap<>();
        newHashMap.put("id",id);
        newHashMap.put("name",name);
        newHashMap.put("age",age);
        newHashMap.put("gender",gender);
        newHashMap.put("addr",addr);
        //创建重组对象
        RestructureData dataTransforms = new RestructureData();
        //获取yaml数据对象data
        YamlModel data = dataTransforms.yamlTransformObj("apicase/adduser.yaml");
        //更换参数
        data.setParameters(newHashMap);
        //替换提取变量
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("accessToken", AddUserApiTest.accessToken);
        String[] str={"accessToken"};
        YamlModel updateData = dataTransforms.replace(data,stringStringHashMap,str);
        //传入对象请求接口，获取结果
        Object res = reflect(updateData);
        //断言结果
        AssertUtil.AssertRes(data,res.toString());
    }

    @DataProvider
    public static Object[][] providerData() {
        Object[][] o = new Object[][]{
                {"10","xiaojin","30","1","nanhai"},
                {"xx","><:","abc","1","why"},
                {"select * from adduser","xiaojin","30","1","nanhai"},
                {"A","中文","??","4","nanhai"},
                {"10","xiaojin","30","nanhai",""},
        };
        return o;
    }
}
