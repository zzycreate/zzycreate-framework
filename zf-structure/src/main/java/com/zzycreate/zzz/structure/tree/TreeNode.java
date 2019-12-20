package com.zzycreate.zzz.structure.tree;

import com.zzycreate.zzz.structure.Node;

import java.util.List;

/**
 * 树节点
 *
 * @param <ID>    主键类型
 * @param <Value> 值类型
 * @author zhenyao.zhao
 * @date 2019-11-18
 */
public interface TreeNode<ID, Value> extends Node<ID, Value> {

    @Override
    ID getId();

    @Override
    Value getValue();

    /**
     * 设置值
     *
     * @param value 节点值
     */
    void setValue(Value value);

    /**
     * 是否叶子节点
     * 即没有子节点的节点
     *
     * @return 是否叶子节点
     */
    boolean isLeaf();

    /**
     * 获取子节点
     *
     * @return 子节点列表
     */
    List<? extends TreeNode<ID, Value>> getChildren();

}
