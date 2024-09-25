package com.test.testng;

import org.testng.annotations.*;

public class BasicAnnotation {
    @Test
    public void  testCase1(){
        System.out.println("这是测试用例1");
    }
    @Test
    public void testCase2(){
        System.out.println("这是测试用例2");
    }
    @BeforeMethod
    public void beforeMethod(){
        System.out.println("BeforeMethod这是在方法之前运行的");
    }
    @AfterMethod
    public void afterMethod(){
        System.out.println("AfterMethod这是在方法之后运行的");
    }
    @BeforeClass
    public void beforeClass(){
        System.out.println("beforeClass这是在类运行之前运行的方法");
    }
    @AfterClass
    public void afterClass(){
        System.out.println("afterClass这是在类之后运行的方法");
    }
    /*在运行类之前运行的测试套件*/
    @BeforeSuite
    public void beforeSuite(){
        System.out.println("BeforeSuite测试套件");
    }
    @AfterSuite
    public void afterSuite(){
        System.out.println("AfterSuite测试套件");
    }
}
