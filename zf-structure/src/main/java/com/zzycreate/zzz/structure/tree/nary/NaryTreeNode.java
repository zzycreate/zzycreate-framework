package com.zzycreate.zzz.structure.tree.nary;

import com.zzycreate.zzz.structure.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * N叉树节点
 *
 * @param <ID>    主键类型
 * @param <Value> 值类型
 * @author zhenyao.zhao
 * @date 2019-11-18
 */
public class NaryTreeNode<ID, Value> implements TreeNode<ID, Value> {

    private ID id;
    private Value value;

    private List<NaryTreeNode<ID, Value>> children;

    public NaryTreeNode() {
    }

    public NaryTreeNode(ID id) {
        this.id = id;
    }

    public NaryTreeNode(ID id, Value value) {
        this.id = id;
        this.value = value;
    }

    public NaryTreeNode(ID id, Value value, List<NaryTreeNode<ID, Value>> children) {
        this.id = id;
        this.value = value;
        this.children = children;
    }

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public List<? extends NaryTreeNode<ID, Value>> getChildren() {
        return this.isLeaf() ? new ArrayList<>() : children;
    }

    @Override
    public boolean isLeaf()  {
        return children == null || children.isEmpty();
    }

    protected void traversal(Consumer<NaryTreeNode<ID, Value>> consumer) {
        consumer.accept(this);
        for (NaryTreeNode<ID, Value> child : getChildren()) {
            child.traversal(consumer);
        }
    }

    @Override
    public String toString() {
        String childrenIds = getChildren().stream()
                .map(NaryTreeNode::getId)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return getClass().getSimpleName() + "{" + "id = " + id + ", value = " + value + ", children = [" + childrenIds + "])}";
    }

    public void flatToString(StringBuilder stringBuilder, String indent) {
        stringBuilder.append(indent).append(this.toString()).append(System.lineSeparator());
        indent = "    " + indent;

        for (NaryTreeNode<ID, Value> childNode : this.getChildren()) {
            childNode.flatToString(stringBuilder, indent);
        }
    }

}
