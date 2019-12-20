package com.zzycreate.zzz.structure.tree.nary;

import com.zzycreate.zzz.structure.tree.Tree;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * N叉树
 *
 * @param <ID>    主键类型
 * @param <Value> 值类型
 * @author zhenyao.zhao
 * @date 2019-11-18
 */
public class NaryTree<ID, Value> implements Tree<ID, Value> {

    private NaryTreeNode<ID, Value> root;

    public NaryTree() {
    }

    public NaryTree(NaryTreeNode<ID, Value> root) {
        this.root = root;
    }

    @Override
    public NaryTreeNode<ID, Value> getRoot() {
        return this.root;
    }

    public void setRoot(NaryTreeNode<ID, Value> root) {
        this.root = root;
    }

    @Override
    public boolean isEmpty() {
        return this.getRoot() == null;
    }

    public void breadFirstTraversal(Consumer<NaryTreeNode<ID, Value>> consumer) {
        Objects.requireNonNull(consumer);

        Queue<NaryTreeNode<ID, Value>> queue = new LinkedList<>();
        queue.offer(this.getRoot());

        while (!queue.isEmpty()) {
            NaryTreeNode<ID, Value> currNode = queue.poll();
            if (currNode != null) {
                consumer.accept(currNode);
                currNode.getChildren().forEach(queue::offer);
            }
        }
    }

    public void depthFirstTraversal(Consumer<NaryTreeNode<ID, Value>> consumer) {
        Objects.requireNonNull(consumer);
        this.getRoot().traversal(consumer);
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
