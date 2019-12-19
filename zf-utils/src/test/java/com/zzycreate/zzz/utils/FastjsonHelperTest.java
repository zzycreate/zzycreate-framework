package com.zzycreate.zzz.utils;

import com.alibaba.fastjson.TypeReference;
import com.zzycreate.zzz.utils.model.TestBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author zzycreate
 * @date 2019/12/18
 */
@Slf4j
public class FastjsonHelperTest {

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
    public void toObject() {
        TestBean bean = getTestBean();
        List<TestBean> list = CastHelper.toList(bean);
        log.debug("{}", FastjsonHelper.toJsonString(bean));
        log.debug("{}", FastjsonHelper.toJsonString(list));
        // {"d":1.23,"flag":true,"i":1,"list":["list1","",null],"map":{"k1":"v1","k2":false,"k3":null},"obj":null,"str":"hi"}
        // [{"d":1.23,"flag":true,"i":1,"list":["list1","",null],"map":{"k1":"v1","k2":false,"k3":null},"obj":null,"str":"hi"}]
        TestBean testBean = FastjsonHelper.toObject("{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"map\":{\"k1\":\"v1\",\"k2\":false,\"k3\":null},\"obj\":null,\"str\":\"hi\"}", TestBean.class);
        List<TestBean> testBeans = FastjsonHelper.toObject("[{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"map\":{\"k1\":\"v1\",\"k2\":false,\"k3\":null},\"obj\":null,\"str\":\"hi\"}]", new TypeReference<List<TestBean>>() {
        });
        testEquals(bean, testBean);
        assertEquals(1, testBeans.size());
        assertEquals(bean, testBeans.get(0));
    }

    @Test
    public void format() {
        TestBean bean = getTestBean();
        String formatJsonString = FastjsonHelper.format(bean);
        log.debug("formatJsonString: {}", formatJsonString);
        String expectedJson = "{\n" +
                "\t\"d\":1.23,\n" +
                "\t\"flag\":true,\n" +
                "\t\"i\":1,\n" +
                "\t\"list\":[\n" +
                "\t\t\"list1\",\n" +
                "\t\t\"\",\n" +
                "\t\tnull\n" +
                "\t],\n" +
                "\t\"map\":{\n" +
                "\t\t\"k1\":\"v1\",\n" +
                "\t\t\"k2\":false,\n" +
                "\t\t\"k3\":null\n" +
                "\t},\n" +
                "\t\"obj\":null,\n" +
                "\t\"str\":\"hi\"\n" +
                "}";
        assertEquals(expectedJson, formatJsonString);
    }

    @Test
    public void toJsonString() {
        TestBean bean = getTestBean();
        String jsonString1 = FastjsonHelper.toJsonString(bean);
        log.debug("jsonString1: {}", jsonString1);
        // {"d":1.23,"flag":true,"i":1,"list":["list1","",null],"map":{"k1":"v1","k2":false,"k3":null},"obj":null,"str":"hi"}
        assertEquals("{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"map\":{\"k1\":\"v1\",\"k2\":false,\"k3\":null},\"obj\":null,\"str\":\"hi\"}", jsonString1);

        String jsonString2 = FastjsonHelper.toJsonString(bean, false);
        log.debug("jsonString2: {}", jsonString2);
        // {"d":1.23,"flag":true,"i":1,"list":["list1","",null],"map":{"k1":"v1","k2":false},"str":"hi"}
        assertEquals("{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"map\":{\"k1\":\"v1\",\"k2\":false},\"str\":\"hi\"}", jsonString2);

        String jsonString3 = FastjsonHelper.toJsonString(bean, false, true);
        log.debug("jsonString3: {}", jsonString3);
        // {"d":1.23,"flag":true,"i":1,"list":["list1","",null],"map":{"k1":"v1","k2":false},"str":"hi"}
        assertEquals("{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"map\":{\"k1\":\"v1\",\"k2\":false},\"str\":\"hi\"}", jsonString3);

        String jsonString4 = FastjsonHelper.toJsonString(bean, CastHelper.toSet("map"));
        log.debug("jsonString4: {}", jsonString4);
        // {"d":1.23,"flag":true,"i":1,"list":["list1","",null],"obj":null,"str":"hi"}
        assertEquals("{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"obj\":null,\"str\":\"hi\"}", jsonString4);

        String jsonString5 = FastjsonHelper.toJsonString(bean, CastHelper.toSet("map"), null, true, false, false, true);
        log.debug("jsonString5: {}", jsonString5);
        // {"d":1.23,"flag":true,"i":1,"list":["list1","",null],"obj":null,"str":"hi"}
        assertEquals("{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"obj\":null,\"str\":\"hi\"}", jsonString5);

        TestBean bean1 = getTestBean();
        bean1.setStr(null);
        String jsonString6 = FastjsonHelper.toJsonString(bean1, CastHelper.toSet("map"), null, true, true, false, true);
        log.debug("jsonString6: {}", jsonString6);
        // {"d":1.23,"flag":true,"i":1,"list":["list1","",null],"obj":null,"str":""}
        assertEquals("{\"d\":1.23,\"flag\":true,\"i\":1,\"list\":[\"list1\",\"\",null],\"obj\":null,\"str\":\"\"}", jsonString6);

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