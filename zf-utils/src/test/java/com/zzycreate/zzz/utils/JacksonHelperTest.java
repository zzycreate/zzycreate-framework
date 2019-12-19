package com.zzycreate.zzz.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zzycreate.zzz.utils.model.TestBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * {@link JacksonHelper} 单元测试方法
 *
 * @author zzycreate
 * @date 2018/12/09
 */
@Slf4j
public class JacksonHelperTest {

    /**
     * 默认的TestBean
     *
     * @return TestBean
     */
    private static TestBean getTestBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", false);
        map.put("k3", null);
        TestBean bean = new TestBean();
        bean.setStr("hi");
        bean.setI(1);
        bean.setD(1.23);
        bean.setFlag(true);
        bean.setObj(null);
        bean.setList(CastHelper.toList("list1", "", null));
        bean.setMap(map);
        return bean;
    }

    @Test
    public void writer() {
    }

    @Test
    public void reader() {
    }

    @Test
    public void toJsonString() {
        TestBean bean = getTestBean();
        String jsonString1 = JacksonHelper.toJsonString(bean);
        log.debug("jsonString: {}", jsonString1);
        assertEquals("{\"str\":\"hi\",\"i\":1,\"d\":1.23,\"flag\":true,\"obj\":null,\"list\":[\"list1\",\"\",null]," +
                "\"map\":{\"k1\":\"v1\",\"k2\":false,\"k3\":null}}", jsonString1);
//        {"str":"hi","i":1,"d":1.23,"flag":true,"obj":null,"list":["list1","",null],"map":{"k1":"v1","k2":false,"k3":null}}

        String str = JacksonHelper.toJsonString("str");
        assertEquals("str", str);

        String formatJsonString = JacksonHelper.format(bean);
        log.debug("formatJsonString: {}", formatJsonString);
        String expectedJson = "{" + System.lineSeparator() +
                "  \"str\" : \"hi\"," + System.lineSeparator() +
                "  \"i\" : 1," + System.lineSeparator() +
                "  \"d\" : 1.23," + System.lineSeparator() +
                "  \"flag\" : true," + System.lineSeparator() +
                "  \"obj\" : null," + System.lineSeparator() +
                "  \"list\" : [ \"list1\", \"\", null ]," + System.lineSeparator() +
                "  \"map\" : {" + System.lineSeparator() +
                "    \"k1\" : \"v1\"," + System.lineSeparator() +
                "    \"k2\" : false," + System.lineSeparator() +
                "    \"k3\" : null" + System.lineSeparator() +
                "  }" + System.lineSeparator() +
                "}";
        assertEquals(expectedJson, formatJsonString);

        String str1 = JacksonHelper.format("str");
        assertEquals("str", str1);
    }

    @Test
    public void toObject() {
        TestBean bean = getTestBean();
        List<TestBean> list = CastHelper.toList(bean);
        log.debug("{}", JacksonHelper.toJsonString(bean));
        log.debug("{}", JacksonHelper.toJsonString(list));
        //{"d":1.23,"flag":true,"i":1,"list":["list1","",null],"map":{"k1":"v1","k2":false,"k3":null},"obj":null,"str":"hi"}
        //[{"d":1.23,"flag":true,"i":1,"list":["list1","",null],"map":{"k1":"v1","k2":false,"k3":null},"obj":null,"str":"hi"}]
        TestBean testBean = JacksonHelper.toObject("{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"map\":{\"k1\":\"v1\",\"k2\":false,\"k3\":null},\"obj\":null,\"str\":\"hi\"}", TestBean.class);
        List<TestBean> testBeans = JacksonHelper.toObject("[{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"map\":{\"k1\":\"v1\",\"k2\":false,\"k3\":null},\"obj\":null,\"str\":\"hi\"}]", new TypeReference<List<TestBean>>() {
        });
        testEquals(bean, testBean);
        assertEquals(1, testBeans.size());
        assertEquals(bean, testBeans.get(0));
    }

    @Test
    public void toList() {
        TestBean bean = getTestBean();
        List<TestBean> testBeans = JacksonHelper.toList("[{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"map\":{\"k1\":\"v1\",\"k2\":false,\"k3\":null},\"obj\":null,\"str\":\"hi\"}]", new TypeReference<List<TestBean>>() {
        });
        assertEquals(1, testBeans.size());
        assertEquals(bean, testBeans.get(0));
    }

    @Test
    public void toSet() {
        TestBean bean = getTestBean();
        Set<TestBean> testBeans = JacksonHelper.toSet("[{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"map\":{\"k1\":\"v1\",\"k2\":false,\"k3\":null},\"obj\":null,\"str\":\"hi\"}]", new TypeReference<Set<TestBean>>() {
        });
        assertEquals(1, testBeans.size());
        assertEquals(bean, testBeans.toArray()[0]);
    }

    @Test
    public void toMap() {
    }

    private void testEquals(TestBean expectedBean, TestBean testBean) {
        assertEquals(expectedBean.getD(), testBean.getD());
        assertEquals(expectedBean.getI(), testBean.getI());
        assertEquals(expectedBean.getStr(), testBean.getStr());
        assertEquals(expectedBean.getObj(), testBean.getObj());
        assertArrayEquals(expectedBean.getList().toArray(), testBean.getList().toArray());
        assertEquals("v1", testBean.getMap().get("k1"));
        assertEquals(false, testBean.getMap().get("k2"));
        assertNull(testBean.getMap().get("k3"));
    }
}
