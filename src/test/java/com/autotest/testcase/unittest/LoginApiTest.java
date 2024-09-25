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
@Feature("登录接口")
public class LoginApiTest {
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
    @DataProvider
    public static Object[][] providerData() {
        Object[][] o = new Object[][]{
                {"admin","123456","A8b52"},
                {"fangting","123456","A8b52"},
                {"admin", "123","A8b52"},
                {"admin", "123456","Abc"}
        };
        return o;
    }
    @Story("正反用例")
    @Description("获取token令牌")
    @Test(dataProvider = "providerData")
    public static void login(String name,String password,String verification) throws InterruptedException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("username",name);
        stringStringHashMap.put("password",password);
        stringStringHashMap.put("verification",verification);
                //获取yaml数据对象data
                YamlModel data = new RestructureData().yamlTransformObj("apicase/login.yaml");
                //更换参数
                data.setParameters(stringStringHashMap);
                //传入对象请求接口，获取结果
                Object res = reflect(data);
                //提取变量
                if (data.getExtractVariables() != null) {
                    HashMap<String, String> extractVariables = data.getExtractVariables();
                    HashMap<String, String> extractstr = ExtractVariables.extract(res.toString(), extractVariables);
                    LoginApiTest.accessToken = extractstr.get("accessToken");
                }
                //断言结果
                AssertUtil.AssertRes(data, res.toString());
        }
    }

