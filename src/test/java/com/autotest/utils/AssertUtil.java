package com.autotest.utils;

import com.autotest.entity.YamlModel;
import io.qameta.allure.Allure;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;

public class AssertUtil {
    public static Logger logger= Logger.getLogger(AssertUtil.class);
    // 断言字段
    public static void AssertRes(YamlModel data, String result) {

        if (data.getExpectedResults() != null && data.getActualResults() != null) {
            String expectedResults = data.getExpectedResults();
            HashMap<String, String> actualResults = data.getActualResults();
            logger.info(result);
            logger.info(actualResults);
            HashMap<String, String> extract = ExtractVariables.extract(result, actualResults);
            for (String s : extract.keySet()) {
                String s1 = extract.get(s);
                logger.info("actualResults:"+s1);
                Allure.attachment("预期结果",expectedResults);
                Allure.attachment("实际结果",expectedResults);
                if(expectedResults.contains(s1)) {
                    Allure.attachment("断言结果", "测试通过");
                }else {
                    Allure.attachment("断言结果", "预期与实际不一致");
                }
//                Assert.assertEquals(expectedResults, s1);
                //包含判断适用范围更广
                Assert.assertTrue(s1.contains(expectedResults));
            }
        } else {
            Assert.fail("expectedResults or actualResults is null or empty");
        }

    }
}

