package com.zzycreate.zzz.utils;

import com.zzycreate.zzz.utils.model.TestBean;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zzycreate
 * @date 2019/9/26
 */
public class LambdaHelperTest {

    @Test
    public void testDistinctByKey() {
        List<TestBean> listData = listData();
        List<TestBean> distinctData =
                listData.stream().filter(LambdaHelper.distinctByKey(TestBean::getStr)).collect(Collectors.toList());
        Set<String> strSet = distinctData.stream().map(TestBean::getStr).collect(Collectors.toSet());
        Assert.assertEquals(5, strSet.size());
        for (int i = 0; i < 4; i++) {
            String testStr = "" + i;
            Assert.assertTrue(strSet.contains(testStr));
        }
    }

    private static List<TestBean> listData() {
        List<TestBean> list = new ArrayList<>();
        list.add(TestBean.builder().str("0").build());
        list.add(TestBean.builder().str("1").build());
        list.add(TestBean.builder().str("1").build());
        list.add(TestBean.builder().str("2").build());
        list.add(TestBean.builder().str("2").build());
        list.add(TestBean.builder().str("3").build());
        list.add(TestBean.builder().str("4").build());
        list.add(TestBean.builder().str("2").build());
        return list;
    }

}
