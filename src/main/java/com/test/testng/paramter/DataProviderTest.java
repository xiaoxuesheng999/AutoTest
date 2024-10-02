package com.test.testng.paramter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {
    //传参,普通方法
    @Test(dataProvider = "providerData")
    public void testDataProvider(String name, int age) {
        System.out.println("name=" + name + "  " + "age=" + age);
    }

    @DataProvider
    public Object[][] providerData() {
        Object[][] o = new Object[][]{
                {"xiaohe", 10},
                {"fangting", 20},
                {"lala", 30}
        };
        return o;
    }


    //根据不同方法，传不同参
    @Test(dataProvider = "methodData")
    public void test1(String name, int age) {
        System.out.println("test1方法 name=" + name + ";age=" + age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name, int age) {
        System.out.println("test2方法 name=" + name + ";age=" + age);
    }

    @DataProvider(name = "methodData")
    public Object[][] methodDataTest(Method method) {
        Object[][] result = null;
        if (method.getName().equals("test1")) {
            result=new Object[][]{
                    {"zhangsan",20},
                    {"lisi",30},
            };
        } else if (method.getName().equals("test2")) {
            result=new Object[][]{
                    {"wangwu",40},
                    {"liuliu",50},
            };
        }
        return result;
    }
}
