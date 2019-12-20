package com.zzycreate.zzz.structure.tree.hierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zzycreate.zzz.structure.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 层次树节点
 * 1. 使用 transient 关键字屏蔽字段的json序列化过程，作用于 FastJson 和 Gson
 * 2. 使用 @JsonIgnore 注解过滤jackson序列化字段
 *
 * @param <Hierarchy> 层次类型，标识当前节点在树上的深度标识
 * @param <ID>        主键类型
 * @param <Value>     值类型
 * @author zhenyao.zhao
 * @date 2019/11/18
 */
public class HierarchyTreeNode<Hierarchy, ID, Value> implements TreeNode<ID, Value> {

    private Hierarchy hierarchy;
    private int depth = 0;
    private ID id;
    private Value value;
    @JsonIgnore
    private transient HierarchyTreeNode<Hierarchy, ID, Value> parent;
    private List<HierarchyTreeNode<Hierarchy, ID, Value>> children = new LinkedList<>();
    @JsonIgnore
    private transient Map<ID, HierarchyTreeNode<Hierarchy, ID, Value>> childrenMap = new HashMap<>();

    public HierarchyTreeNode() {
    }

    public HierarchyTreeNode(Hierarchy hierarchy, ID id) {
        this.hierarchy = hierarchy;
        this.id = id;
    }

    public HierarchyTreeNode(Hierarchy hierarchy, ID id, Value value) {
        this.hierarchy = hierarchy;
        this.id = id;
        this.value = value;
    }

    public Hierarchy getHierarchy() {
        return this.hierarchy;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public ID getId() {
        return this.id;
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    @Override
    public void setValue(Value value) {
        this.value = value;
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> getParent() {
        return this.parent;
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> getChild(ID id) {
        return this.childrenMap.get(id);
    }

    @Override
    public List<HierarchyTreeNode<Hierarchy, ID, Value>> getChildren() {
        return this.isLeaf() ? new ArrayList<>() : this.children;
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> getOrAddChild(Hierarchy hierarchy, ID id) {
        HierarchyTreeNode<Hierarchy, ID, Value> childNode = this.getChild(id);
        if (childNode == null) {
            childNode = new HierarchyTreeNode<>(hierarchy, id);
            this.addChild(childNode);
        }
        return childNode;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    @Override
    public boolean isLeaf() {
        return this.childrenMap == null || this.childrenMap.isEmpty();
    }

    private void addChild(HierarchyTreeNode<Hierarchy, ID, Value> childNode) {
        Objects.requireNonNull(childNode);
        this.children.add(childNode);
        this.childrenMap.put(childNode.id, childNode);
        childNode.depth = this.depth + 1;
        childNode.parent = this;
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> filter(Map<Hierarchy, Set<ID>> filters) {
        HierarchyTreeNode<Hierarchy, ID, Value> node = new HierarchyTreeNode<>(this.hierarchy, this.id, this.value);
        if (!this.isLeaf()) {
            for (Map.Entry<ID, HierarchyTreeNode<Hierarchy, ID, Value>> nodeEntry : this.childrenMap.entrySet()) {
                ID nodeId = nodeEntry.getKey();
                HierarchyTreeNode<Hierarchy, ID, Value> childNode = nodeEntry.getValue();
                Set<ID> filterIds = filters.get(childNode.hierarchy);
                if (filterIds == null || filterIds.contains(nodeId)) {
                    HierarchyTreeNode<Hierarchy, ID, Value> filterChild = childNode.filter(filters);
                    node.addChild(filterChild);
                }
            }
        }

        return node;
    }

    protected void traversal(Consumer<HierarchyTreeNode<Hierarchy, ID, Value>> consumer) {
        consumer.accept(this);
        for (HierarchyTreeNode<Hierarchy, ID, Value> child : getChildren()) {
            child.traversal(consumer);
        }
    }

    @Override
    public String toString() {
        String childrenIds = getChildren().stream()
                .map(HierarchyTreeNode::getId)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return "HierarchyTreeNode{" + "hierarchy = " + hierarchy + ", id = " + id + ", value=" + value + ", children = [" + childrenIds + "])}";
    }

    protected void flatToString(StringBuilder stringBuilder, String indent) {
        stringBuilder.append(indent).append(this.toString()).append(System.lineSeparator());
        indent = "    " + indent;

        for (HierarchyTreeNode<Hierarchy, ID, Value> childNode : this.getChildren()) {
            childNode.flatToString(stringBuilder, indent);
        }
    }

}
