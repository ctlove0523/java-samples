package io.github.ctlove0523.javasamples.tree;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: TreeNode
 *
 * @author: chentong
 * Date:     2019/4/28 7:58
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TreeNode {
    private int value;
    private TreeNode left;
    private TreeNode right;

    /**
     * 深度优先搜索
     *
     * @param value 待搜索值
     * @return 是否存在
     */
    public boolean deepFirstSearch(int value) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(this);
        while (!stack.isEmpty()) {
            TreeNode top = stack.pop();
            if (value == top.getValue()) {
                return true;
            }

            if (null != top.getRight()) {
                stack.push(top.getRight());
            }
            if (null != top.getLeft()) {
                stack.push(top.getLeft());
            }
        }
        return false;
    }

    /**
     * 查询根节点到目标节点的路径
     *
     * @param val 目标节点
     * @return 路径
     */
    public List<TreeNode> findPath(int val) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(this);
        List<TreeNode> path = new ArrayList<>();

        while (!stack.isEmpty()) {
            TreeNode top = stack.pop();
            path.add(top);
            // check the value,if find stop loop
            if (top.getValue() == val) {
                break;
            }

            if (top.getRight() != null) {
                stack.push(top.getRight());
            }
            if (top.getLeft() != null) {
                stack.push(top.getLeft());
            }

            // if the node is leaf,we need rollback the path
            if (null == top.getLeft() && null == top.getRight()) {
                if (stack.isEmpty()) {
                    path.clear();
                    break;
                }
                TreeNode nextTop = stack.peek();
                for (int i = path.size() - 1; i >= 0; i--) {
                    if (path.get(i).getRight() == nextTop || path.get(i).getLeft() == nextTop) {
                        path = path.subList(0, i + 1);
                        break;
                    }
                }
            }
        }

        return path;
    }
}
