package com.zzycreate.zzz.structure;

/**
 * 节点
 *
 * @param <ID>    主键 类型
 * @param <Value> 值/内容 类型
 * @author zhenyao.zhao
 * @date 2019-11-18
 */
public interface Node<ID, Value> {

    /**
     * 节点主键
     *
     * @return 节点主键
     */
    ID getId();

    /**
     * 节点值/内容
     *
     * @return 节点值/内容
     */
    Value getValue();

}
