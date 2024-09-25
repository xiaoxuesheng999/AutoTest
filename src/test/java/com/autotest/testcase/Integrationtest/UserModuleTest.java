package com.autotest.testcase.Integrationtest;

import com.autotest.entity.YamlModel;
import com.autotest.utils.*;
import io.qameta.allure.*;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;

@Epic("业务集成测试")
@Feature("用户模块")
public class UserModuleTest {
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
    @BeforeTest
    public static void prepareTest(){
        System.out.println("----测试开始----");
        Allure.attachment("连接数据库","开始数据初始化");
    }

    @Story("登录")
    @Description("获取token令牌")
    @Test
    public static void login() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        int retries = 0;
        int MAX_RETRIES=3;
        while (retries < MAX_RETRIES) {
            try {
                //获取yaml数据对象data
                YamlModel data = new RestructureData().yamlTransformObj("apicase/login.yaml");
                //传入对象请求接口，获取结果
                Object res = reflect(data);
                //提取变量
                if (data.getExtractVariables() != null) {
                    HashMap<String, String> extractVariables = data.getExtractVariables();
                    HashMap<String, String> extractstr = ExtractVariables.extract(res.toString(), extractVariables);
                    UserModuleTest.accessToken = extractstr.get("accessToken");
                }
                //断言结果
                AssertUtil.AssertRes(data, res.toString());
                break;
            } catch (Exception e) {
            retries++;
            if (retries >= MAX_RETRIES) {
                throw new AssertionError("Failed after " + retries + " retries");
            }
            // 这里添加等待逻辑
                Thread.sleep(1000);
        }
    }
    }

    @Story("获取用户信息")
    @Description("获取用户信息")
    @Test(dependsOnMethods = {"login"})
    public static void userinfo() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        String assertStr="";
        //创建重组对象
        RestructureData dataTransforms = new RestructureData();
        //获取yaml数据对象data
        YamlModel data = dataTransforms.yamlTransformObj("apicase/userinfo.yaml");
        //替换提取变量
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("accessToken", UserModuleTest.accessToken);
        String[] str={"accessToken"};
        YamlModel updateData = dataTransforms.replace(data,stringStringHashMap,str);
        //传入对象请求接口，获取结果
        Object res = reflect(updateData);
        //查询数据库,组装数据,替换预期结果
        assertStr= LinkDataBase.executeQuery(data.getInitSql().toString(), "enviroments.properties", rs -> {
            String restr="";
            try {
                while (rs.next()) {
                    restr = rs.getString("addr");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return restr;
        });
        data.setExpectedResults(assertStr);
        //断言结果
        AssertUtil.AssertRes(data,res.toString());

    }

    @Story("新增用户")
    @Description("新增用户")
    @Test(dependsOnMethods = {"login"})
    public static void adduser() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //创建重组对象
        RestructureData dataTransforms = new RestructureData();
        //获取yaml数据对象data
        YamlModel data = dataTransforms.yamlTransformObj("apicase/adduser.yaml");
        //替换提取变量
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("accessToken", UserModuleTest.accessToken);
        String[] str={"accessToken"};
        YamlModel updateData = dataTransforms.replace(data,stringStringHashMap,str);
        //传入对象请求接口，获取结果
        Object res = reflect(updateData);
        //断言结果
        AssertUtil.AssertRes(data,res.toString());
    }

    @Story("更新用户")
    @Description("更新用户")
    @Test(dependsOnMethods = {"login"})
    public static void updateuser() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //创建重组对象
        RestructureData dataTransforms = new RestructureData();
        //获取yaml数据对象data
        YamlModel data = dataTransforms.yamlTransformObj("apicase/updateuser.yaml");
        //替换提取变量
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("accessToken", UserModuleTest.accessToken);
        String[] str={"accessToken"};
        YamlModel updateData = dataTransforms.replace(data,stringStringHashMap,str);
        //传入对象请求接口，获取结果
        Object res = reflect(updateData);
        //断言结果
        AssertUtil.AssertRes(data,res.toString());
    }
    @Story("删除用户")
    @Description("删除用户")
    @Test(dependsOnMethods = {"login"})
    public static void deleteuser() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //创建重组对象
        RestructureData dataTransforms = new RestructureData();
        //获取yaml数据对象data
        YamlModel data = dataTransforms.yamlTransformObj("apicase/deleteuser.yaml");
        //替换提取变量
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("accessToken", UserModuleTest.accessToken);
        String[] str={"accessToken"};
        YamlModel updateData = dataTransforms.replace(data,stringStringHashMap,str);
        //传入对象请求接口，获取结果
        Object res = reflect(updateData);
        //断言结果
        AssertUtil.AssertRes(data,res.toString());
    }
    @Story("上传")
    @Description("文件上传")
    @Test(dependsOnMethods = {"login"})
    public static void upload() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //创建重组对象
        RestructureData dataTransforms = new RestructureData();
        //获取yaml数据对象data
        YamlModel data = dataTransforms.yamlTransformObj("apicase/upload.yaml");
        //替换提取变量
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("accessToken", UserModuleTest.accessToken);
        String[] str={"accessToken"};
        YamlModel updateData = dataTransforms.replace(data,stringStringHashMap,str);
        //传入对象请求接口，获取结果
        Object res = reflect(updateData);
        //断言结果
        AssertUtil.AssertRes(data,res.toString());
    }

    @AfterTest
    public static void closeTest(){
        System.out.println("----测试结束----");
        Allure.attachment("清理数据","关闭数据库");
    }



//    public static void main(String[] args) throws IOException {
////        readTestData();
//    }
}
