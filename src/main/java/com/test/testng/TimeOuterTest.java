package com.test.testng;

import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

public class TimeOuterTest {
    @Test(timeOut = 3000)//单位为毫秒
    public void testSuccess() throws InterruptedException {
        sleep(2000);
    }
    @Test(timeOut = 2000)//单位为毫秒
    public void testFail() throws InterruptedException {
        sleep(3000);
    }
}
