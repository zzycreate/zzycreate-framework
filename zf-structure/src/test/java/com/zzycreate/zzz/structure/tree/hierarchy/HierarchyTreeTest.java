package com.zzycreate.zzz.structure.tree.hierarchy;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 层次树测试
 *
 * @author zhenyao.zhao
 * @date 2019/11/18
 */
public class HierarchyTreeTest {

    private static HierarchyTree<String, Long, String> tree() {
        HierarchyTree<String, Long, String> tree = new HierarchyTree<String, Long, String>(
                Stream.of("1", "2", "3", "4").collect(Collectors.toCollection(LinkedList::new)));
        tree.putValue(Arrays.asList(1L), "node1");
        tree.putValue(Arrays.asList(1L, 2L), "node2");
        tree.putValue(Arrays.asList(1L, 2L, 3L), "node3");
        tree.putValue(Arrays.asList(1L, 4L), "node4");
        tree.putValue(Arrays.asList(1L, 4L, 5L), "node5");
        tree.putValue(Arrays.asList(1L, 4L, 6L), "node6");
        tree.putValue(Arrays.asList(1L, 7L), "node7");
        tree.putValue(Arrays.asList(1L, 7L, 8L), "node8");
        tree.putValue(Arrays.asList(1L, 7L, 8L, 9L), "node9");
        tree.putValue(Arrays.asList(10L), "node10");
        tree.putValue(Arrays.asList(11L), "node11");
        tree.putValue(Arrays.asList(11L, 12L), "node12");
        tree.putValue(Arrays.asList(11L, 12L, 13L), "node13");
        tree.putValue(Arrays.asList(11L, 12L, 14L), "node14");
        tree.putValue(Arrays.asList(1L, 7L, 8L, 9L, 15L), "node14");
        tree.putValue(Arrays.asList(1L, 7L, 8L, 9L, 16L), "node-1");
        tree.putValue(Arrays.asList(1L, 7L, 8L, 9L, 17L), "node-1");
        return tree;
    }

    @Test
    public void testTraversal() {
        HierarchyTree<String, Long, String> tree = tree();
        System.out.println("hierarchy tree: " + tree);

        Assert.assertNotNull(tree);
        Assert.assertNotNull(tree.getRoot());
        Assert.assertEquals(3, tree.getRoot().getChildren().size());

        System.out.println("深度优先遍历");
        long now = System.currentTimeMillis();
        tree.depthFirstTraversal(node -> System.out.println(node.toString()));
        long now1 = System.currentTimeMillis();
        System.out.println("深度耗时：" + (now1 - now) + "s");

        System.out.println("广度优先遍历");
        tree.breadFirstTraversal(node -> System.out.println(node.toString()));
        long now2 = System.currentTimeMillis();
        System.out.println("深度耗时：" + (now2 - now1) + "s");
    }

    @Test
    public void testSize() {
        HierarchyTree<String, Long, String> tree = tree();
//        System.out.println("hierarchy tree: {}", tree);

        int size = tree.size();
        System.out.println("树的大小: " + size);
        Assert.assertEquals(18, size);
    }

    @Test
    public void testHeight() {
        HierarchyTree<String, Long, String> tree = tree();
//        System.out.println("hierarchy tree: {}", tree);

        int height = tree.height();
        System.out.println("树的深度: " + height);
        Assert.assertEquals(5, height);
    }

    @Test
    public void testMaxAndMin() {
        HierarchyTree<String, Long, String> tree = tree();
//        System.out.println("hierarchy tree: {}", tree);

        Comparator<HierarchyTreeNode<String, Long, String>> comparator = Comparator.comparingInt(node -> {
            String value = node.getValue();
            if (StringUtils.isBlank(value)) {
                return 0;
            }
            String str = node.getValue().replace("node", "");
            return Integer.parseInt(str);
        });
        HierarchyTreeNode<String, Long, String> maxNode = tree.max(comparator);
        System.out.println("最大值：" + maxNode);
        Assert.assertEquals("node14", maxNode.getValue());
        Assert.assertEquals(14L, maxNode.getId().longValue());

        HierarchyTreeNode<String, Long, String> max1Node = tree.max(comparator, (a, b) -> a);
        System.out.println("最大值1：" + max1Node);
        Assert.assertEquals("node14", max1Node.getValue());
        Assert.assertEquals(14L, max1Node.getId().longValue());
        HierarchyTreeNode<String, Long, String> max2Node = tree.max(comparator, (a, b) -> b);
        System.out.println("最大值2：" + max2Node);
        Assert.assertEquals("node14", max2Node.getValue());
        Assert.assertEquals(15L, max2Node.getId().longValue());

        HierarchyTreeNode<String, Long, String> minNode = tree.min(comparator);
        System.out.println("最小值：" + minNode);
        Assert.assertEquals("node-1", minNode.getValue());
        Assert.assertEquals(16L, minNode.getId().longValue());

        HierarchyTreeNode<String, Long, String> min1Node = tree.min(comparator, (a, b) -> a);
        System.out.println("最小值1：" + min1Node);
        Assert.assertEquals("node-1", min1Node.getValue());
        Assert.assertEquals(16L, min1Node.getId().longValue());
        HierarchyTreeNode<String, Long, String> min2Node = tree.min(comparator, (a, b) -> b);
        System.out.println("最小值2：" + min2Node);
        Assert.assertEquals("node-1", min2Node.getValue());
        Assert.assertEquals(17L, min2Node.getId().longValue());

    }

    @Test
    public void testJson() throws JsonProcessingException {
        HierarchyTree<String, Long, String> tree = tree();
//        System.out.println("hierarchy tree: {}", tree);

        System.out.println("json序列化");
        // jackson 默认根据 getter/setter 方法序列化
        String jacksonString = new ObjectMapper().writer().writeValueAsString(tree);
        System.out.println("jackson string: " + jacksonString);
        // fastjson 默认根据 getter/setter 方法序列化, 但是序列化的顺序和jackson不一样
        String fastjsonString = JSONObject.toJSONString(tree);
        System.out.println("fastjson string: " + fastjsonString);
        // gson 默认根据属性序列化，序列化的字段和上述都不一样
        String gsonString = new Gson().toJson(tree);
        System.out.println("gson string: " + gsonString);
    }

}
