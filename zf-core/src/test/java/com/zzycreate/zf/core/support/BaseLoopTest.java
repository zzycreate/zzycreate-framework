package com.zzycreate.zf.core.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.Assert.*;

/**
 * @author zzycreate
 * @date 2019/12/19
 */
@Slf4j
public class BaseLoopTest {

    @Test
    public void test() {
        List<Integer> list = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        assertEquals(100, list.size());

        BaseLoop<Integer> loop = new BaseLoop<Integer>() {
            @Override
            public void loop(List<Integer> rows, int page, int pageSize, int start, int end) {
                log.info("page {}, pageSize {}, start {}, end {}", page, pageSize, start, end);
                log.info("rows: {}", rows);
                assertNotNull(rows);
                assertTrue(CollectionUtils.isNotEmpty(rows));
            }
        };
        loop.setList(list);
        loop.setPageSize(30);
        loop.process();

        log.info("=============");
        new BaseLoop<Integer>() {
            @Override
            public void loop(List<Integer> rows, int page, int pageSize, int start, int end) {
                log.info("page {}, pageSize {}, start {}, end {}", page, pageSize, start, end);
                log.info("rows: {}", rows);
                assertNotNull(rows);
                assertTrue(CollectionUtils.isNotEmpty(rows));
            }
        }.list(list).pageSize(30).process();

        log.info("=============");
        new BaseLoop<Integer>(list, 30) {
            @Override
            public void loop(List<Integer> rows, int page, int pageSize, int start, int end) {
                log.info("page {}, pageSize {}, start {}, end {}", page, pageSize, start, end);
                log.info("rows: {}", rows);
                assertNotNull(rows);
                assertTrue(CollectionUtils.isNotEmpty(rows));
            }
        };
    }

}