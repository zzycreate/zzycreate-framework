package com.zzycreate.zzz.structure.tree.nary;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class NaryTreeTest {

    /**
     * 1-
     * 1-2-
     * 1-2-3-
     * 1-4-
     * 1-4-5-
     * 1-4-6-
     * 1-7-
     * 1-7-8-
     * 1-7-8-9-
     * 10-
     * 11-
     * 11-12-
     * 11-12-13-
     * 11-12-14-
     */
    @Test
    public void naryTreeTest() throws JsonProcessingException {
        NaryTreeNode<Long, String> node3 = new NaryTreeNode<>(3L, "node3");
        NaryTreeNode<Long, String> node5 = new NaryTreeNode<>(5L, "node5");
        NaryTreeNode<Long, String> node6 = new NaryTreeNode<>(6L, "node6");
        NaryTreeNode<Long, String> node9 = new NaryTreeNode<>(9L, "node9");
        NaryTreeNode<Long, String> node10 = new NaryTreeNode<>(10L, "node10");
        NaryTreeNode<Long, String> node13 = new NaryTreeNode<>(13L, "node13");
        NaryTreeNode<Long, String> node14 = new NaryTreeNode<>(14L, "node14");
        NaryTreeNode<Long, String> node12 = new NaryTreeNode<>(12L, "node12", new ArrayList<>(Arrays.asList(node13, node14)));
        NaryTreeNode<Long, String> node11 = new NaryTreeNode<>(11L, "node11", new ArrayList<>(Arrays.asList(node12)));
        NaryTreeNode<Long, String> node8 = new NaryTreeNode<>(8L, "node8", new ArrayList<>(Arrays.asList(node9)));
        NaryTreeNode<Long, String> node7 = new NaryTreeNode<>(7L, "node7", new ArrayList<>(Arrays.asList(node8)));
        NaryTreeNode<Long, String> node2 = new NaryTreeNode<>(2L, "node2", new ArrayList<>(Arrays.asList(node3)));
        NaryTreeNode<Long, String> node4 = new NaryTreeNode<>(4L, "node4", new ArrayList<>(Arrays.asList(node5, node6)));
        NaryTreeNode<Long, String> node1 = new NaryTreeNode<>(1L, "node1", new ArrayList<>(Arrays.asList(node2, node4, node7)));
        NaryTreeNode<Long, String> root = new NaryTreeNode<>(0L, "root", new ArrayList<>(Arrays.asList(node1, node10, node11)));
        NaryTree<Long, String> tree = new NaryTree<>(root);
        System.out.println("nary tree: " + tree);

        Assert.assertNotNull(tree);
        Assert.assertNotNull(tree.getRoot());
        Assert.assertEquals(3, tree.getRoot().getChildren().size());

        System.out.println("深度优先遍历");
        tree.depthFirstTraversal(node -> System.out.println(node.toString()));
        System.out.println("广度优先遍历");
        tree.breadFirstTraversal(node -> System.out.println(node.toString()));

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
