package com.zzycreate.zzz.structure.tree;

/**
 * 树
 *
 * @param <ID>    主键类型
 * @param <Value> 值类型
 * @author zhenyao.zhao
 * @date 2019-11-18
 */
public interface Tree<ID, Value> {

    /**
     * 是否为空树
     *
     * @return 是否为空树
     */
    boolean isEmpty();

    /**
     * 获取根节点
     *
     * @param <T> 节点类型
     * @return 根节点
     */
    <T extends TreeNode<ID, Value>> T getRoot();

}
