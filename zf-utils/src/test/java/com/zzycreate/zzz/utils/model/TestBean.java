package com.zzycreate.zzz.utils.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 测试用的对象
 *
 * @author zzycreate
 * @date 18-12-9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TestBean {

    private String str;

    private int i;

    private Double d;

    private boolean flag;

    private Object obj;

    private List<String> list;

    private Map<String, Object> map;

}
