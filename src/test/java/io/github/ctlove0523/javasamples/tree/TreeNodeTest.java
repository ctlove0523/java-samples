package io.github.ctlove0523.javasamples.tree;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: TreeNodeTest
 *
 * @author: chentong
 * Date:     2019/4/28 23:43
 */
public class TreeNodeTest extends TestCase {

    @Test
    public void test_findPath() {
        TreeNode root = createTree();
        List<TreeNode> path = root.findPath(5);
        List<Integer> target = Arrays.asList(1, 2, 5);
        checkPath(path, target);
        path = root.findPath(10);
        target = Arrays.asList(1, 3, 6, 10);
        checkPath(path, target);
    }

    @Test
    public void test_findPath_notFound() {
        TreeNode node12 = new TreeNode(12, null, null);
        TreeNode node11 = new TreeNode(11, null, null);
        TreeNode node7 = new TreeNode(7, node11, node12);
        List<TreeNode> path = node7.findPath(13);
        assertEquals(path.isEmpty(),true);
    }


    private void checkPath(List<TreeNode> path, List<Integer> target) {
        Assert.assertEquals(path.size(), target.size());
        for (int i = 0; i < path.size(); i++) {
            TreeNode node1 = path.get(i);
            assertEquals(node1.getValue(), target.get(i).intValue());
        }
    }

    private TreeNode createTree() {
        TreeNode node12 = new TreeNode(12, null, null);
        TreeNode node11 = new TreeNode(11, null, null);
        TreeNode node10 = new TreeNode(10, null, null);
        TreeNode node9 = new TreeNode(9, null, null);
        TreeNode node8 = new TreeNode(8, null, null);
        TreeNode node7 = new TreeNode(7, node11, node12);
        TreeNode node6 = new TreeNode(6, node10, null);
        TreeNode node5 = new TreeNode(5, null, node9);
        TreeNode node4 = new TreeNode(4, node8, null);
        TreeNode node3 = new TreeNode(3, node6, node7);
        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode root = new TreeNode(1, node2, node3);
        return root;
    }
}
