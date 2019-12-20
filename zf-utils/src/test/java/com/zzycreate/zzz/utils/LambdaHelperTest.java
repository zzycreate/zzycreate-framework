package com.zzycreate.zzz.utils;

import com.zzycreate.zzz.utils.model.TestBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author zzycreate
 * @date 2019/9/26
 */
@Slf4j
public class LambdaHelperTest {

    @Test
    public void testDistinctByKey() {
        List<TestBean> listData = listData();
        List<TestBean> distinctData =
                listData.stream().filter(LambdaHelper.distinctByKey(TestBean::getStr)).collect(Collectors.toList());
        Set<String> strSet = distinctData.stream().map(TestBean::getStr).collect(Collectors.toSet());
        assertEquals(5, strSet.size());
        for (int i = 0; i < 4; i++) {
            String testStr = "" + i;
            assertTrue(strSet.contains(testStr));
        }
    }

    @Test
    public void groupMapList() {
        List<TestBean> listData = listData();
        Map<String, List<Integer>> listMap = LambdaHelper.groupMapList(listData, TestBean::getStr, TestBean::getI);
        log.info("{}", listMap);
        assertEquals(5, listMap.size());
        assertEquals(1, listMap.get("0").size());
        assertEquals(2, listMap.get("1").size());
        assertEquals(3, listMap.get("2").size());
        assertArrayEquals(new Integer[]{2, 2, 2}, listMap.get("2").toArray());
        assertEquals(1, listMap.get("3").size());
        assertEquals(1, listMap.get("4").size());
    }

    @Test
    public void groupMapSet() {
        List<TestBean> listData = listData();
        Map<String, Set<Integer>> mapSet = LambdaHelper.groupMapSet(listData, TestBean::getStr, TestBean::getI);
        log.info("{}", mapSet);
        assertEquals(5, mapSet.size());
        assertEquals(1, mapSet.get("0").size());
        assertEquals(1, mapSet.get("1").size());
        assertEquals(1, mapSet.get("2").size());
        assertArrayEquals(new Integer[]{2}, mapSet.get("2").toArray());
        assertEquals(1, mapSet.get("3").size());
        assertEquals(1, mapSet.get("4").size());
    }

    private static List<TestBean> listData() {
        List<TestBean> list = new ArrayList<>();
        list.add(TestBean.builder().str("0").i(0).build());
        list.add(TestBean.builder().str("1").i(1).build());
        list.add(TestBean.builder().str("1").i(1).build());
        list.add(TestBean.builder().str("2").i(2).build());
        list.add(TestBean.builder().str("2").i(2).build());
        list.add(TestBean.builder().str("3").i(3).build());
        list.add(TestBean.builder().str("4").i(4).build());
        list.add(TestBean.builder().str("2").i(2).build());
        return list;
    }
}
