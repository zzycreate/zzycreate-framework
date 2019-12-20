package com.zzycreate.zzz.structure.tree.hierarchy;

import com.zzycreate.zzz.structure.tree.Tree;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 层次树
 *
 * @param <Hierarchy> 层次类型，标识当前节点在树上的深度标识
 * @param <ID>        主键类型
 * @param <Value>     值类型
 * @author zhenyao.zhao
 * @date 2019-11-18
 */
public class HierarchyTree<Hierarchy, ID, Value> implements Tree<ID, Value> {

    private LinkedList<Hierarchy> hierarchies;
    private HierarchyTreeNode<Hierarchy, ID, Value> root;

    public HierarchyTree() {
        this.hierarchies = null;
        this.root = new HierarchyTreeNode<>();
    }

    public HierarchyTree(List<Hierarchy> hierarchies) {
        this.hierarchies = new LinkedList<>(hierarchies);
        this.root = new HierarchyTreeNode<>();
    }

    public static <Hierarchy, ID, Value> HierarchyTree<Hierarchy, ID, Value> of(List<Hierarchy> hierarchies) {
        return new HierarchyTree<Hierarchy, ID, Value>(hierarchies);
    }

    public List<Hierarchy> getHierarchies() {
        return this.hierarchies;
    }

    public void setHierarchies(List<Hierarchy> hierarchies) {
        this.hierarchies = new LinkedList<>(hierarchies);
        this.root = new HierarchyTreeNode<>();
    }

    @Override
    public HierarchyTreeNode<Hierarchy, ID, Value> getRoot() {
        return this.root;
    }

    public void setRoot(HierarchyTreeNode<Hierarchy, ID, Value> root) {
        this.root = root;
    }

    @Override
    public boolean isEmpty() {
        return this.getRoot() == null || this.getRoot().isLeaf();
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> putValue(List<ID> hierarchyIds, Value value) {
        HierarchyTreeNode<Hierarchy, ID, Value> node = this.addNode(hierarchyIds);
        node.setValue(value);
        return node;
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> putValue(List<ID> hierarchyIds, Value value,
                                                            BinaryOperator<Value> valueMerger) {
        HierarchyTreeNode<Hierarchy, ID, Value> node = this.addNode(hierarchyIds);
        node.setValue(valueMerger.apply(node.getValue(), value));

        return node;
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> findNode(List<ID> hierarchyIds) {
        Objects.requireNonNull(hierarchyIds);
        HierarchyTreeNode<Hierarchy, ID, Value> currNode = this.root;

        for (ID hierarchyId : hierarchyIds) {
            HierarchyTreeNode<Hierarchy, ID, Value> childNode = currNode.getChild(hierarchyId);
            if (childNode == null) {
                return null;
            }
            currNode = childNode;
        }

        return currNode;
    }

    private HierarchyTreeNode<Hierarchy, ID, Value> addNode(List<ID> ids) {
        Objects.requireNonNull(ids);
        Objects.requireNonNull(this.hierarchies);
        Objects.requireNonNull(this.root);
        HierarchyTreeNode<Hierarchy, ID, Value> currNode = this.root;

        // 确保hierarchy存在，如果hierarchy层次定义不够，则后续均使用定义的最后一个层次做标识
        int hierarchiesSize = this.hierarchies.size();
        Hierarchy lastHierarchy = this.hierarchies.get(hierarchiesSize - 1);
        if (hierarchiesSize < ids.size()) {
            int addSize = ids.size() - hierarchiesSize;
            for (int i = 0; i < addSize; i++) {
                // StringUtils.isNumeric 判断为整数，则层次递增
                if (lastHierarchy != null && StringUtils.isNumeric(String.valueOf(lastHierarchy))) {
                    int lastHierarchyInt = Integer.parseInt(String.valueOf(lastHierarchy));
                    lastHierarchyInt++;
                    lastHierarchy = (Hierarchy) Integer.valueOf(lastHierarchyInt);
                }
                this.hierarchies.add(lastHierarchy);
            }
        }
        Iterator<Hierarchy> hierarchyIterator = this.hierarchies.iterator();

        for (ID id : ids) {
            Hierarchy hierarchy = hierarchyIterator.next();
            currNode = currNode.getOrAddChild(hierarchy, id);
        }

        return currNode;
    }

    public HierarchyTree<Hierarchy, ID, Value> filter(Map<Hierarchy, Collection<ID>> filters) {
        Objects.requireNonNull(filters);
        HierarchyTree<Hierarchy, ID, Value> filteredTree = new HierarchyTree<Hierarchy, ID, Value>(this.hierarchies);

        Map<Hierarchy, Set<ID>> filterMap = filters.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> new HashSet<>(entry.getValue())));

        filteredTree.root = this.root.filter(filterMap);

        return filteredTree;
    }

    public void breadFirstTraversal(Consumer<HierarchyTreeNode<Hierarchy, ID, Value>> consumer) {
        Objects.requireNonNull(consumer);

        Queue<HierarchyTreeNode<Hierarchy, ID, Value>> queue = new LinkedList<>();
        queue.offer(this.getRoot());

        while (!queue.isEmpty()) {
            HierarchyTreeNode<Hierarchy, ID, Value> currNode = queue.poll();
            if (currNode != null) {
                consumer.accept(currNode);
                currNode.getChildren().forEach(queue::offer);
            }
        }
    }

    public void depthFirstTraversal(Consumer<HierarchyTreeNode<Hierarchy, ID, Value>> consumer) {
        Objects.requireNonNull(consumer);
        this.getRoot().traversal(consumer);
    }

    public int size() {
        AtomicReference<Integer> size = new AtomicReference<>(0);
        this.breadFirstTraversal(node -> size.getAndSet(size.get() + 1));

        return size.get();
    }

    public int height() {
        final Integer[] height = {0};
        this.breadFirstTraversal(node -> {
            if (node.getDepth() > height[0]) {
                height[0] = node.getDepth();
            }
        });
        return height[0];
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> max(Comparator<HierarchyTreeNode<Hierarchy, ID, Value>> comparator) {
        final HierarchyTreeNode<Hierarchy, ID, Value>[] maxNode = new HierarchyTreeNode[]{this.root};
        this.breadFirstTraversal(node -> {
            if (comparator.compare(maxNode[0], node) < 0) {
                maxNode[0] = node;
            }
        });
        return maxNode[0];
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> max(Comparator<HierarchyTreeNode<Hierarchy, ID, Value>> comparator,
                                                       BinaryOperator<HierarchyTreeNode<Hierarchy, ID, Value>> merge) {
        final HierarchyTreeNode<Hierarchy, ID, Value>[] maxNode = new HierarchyTreeNode[]{this.root};
        this.breadFirstTraversal(node -> {
            int compare = comparator.compare(maxNode[0], node);
            if (compare < 0) {
                maxNode[0] = node;
            } else if (compare == 0) {
                maxNode[0] = merge.apply(maxNode[0], node);
            }
        });
        return maxNode[0];
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> min(Comparator<HierarchyTreeNode<Hierarchy, ID, Value>> comparator) {
        final HierarchyTreeNode<Hierarchy, ID, Value>[] maxNode = new HierarchyTreeNode[]{this.root};
        this.breadFirstTraversal(node -> {
            if (comparator.compare(maxNode[0], node) > 0) {
                maxNode[0] = node;
            }
        });
        return maxNode[0];
    }

    public HierarchyTreeNode<Hierarchy, ID, Value> min(Comparator<HierarchyTreeNode<Hierarchy, ID, Value>> comparator,
                                                       BinaryOperator<HierarchyTreeNode<Hierarchy, ID, Value>> merge) {
        final HierarchyTreeNode<Hierarchy, ID, Value>[] maxNode = new HierarchyTreeNode[]{this.root};
        this.breadFirstTraversal(node -> {
            int compare = comparator.compare(maxNode[0], node);
            if (compare > 0) {
                maxNode[0] = node;
            } else if (compare == 0) {
                maxNode[0] = merge.apply(maxNode[0], node);
            }
        });
        return maxNode[0];
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return " [Empty Tree] ";
        }
        StringBuilder stringBuilder = new StringBuilder();
        this.getRoot().flatToString(stringBuilder, "|-");
        return getClass().getSimpleName() + "={" + System.lineSeparator() + stringBuilder.toString() + '}';
    }
}
