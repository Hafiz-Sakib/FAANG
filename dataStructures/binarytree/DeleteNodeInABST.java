import java.util.LinkedList;
import java.util.Queue;

/*
https://leetcode.com/problems/delete-node-in-a-bst
Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.
Basically, the deletion can be divided into two stages:
Search for a node to remove.
If the node is found, delete the node.
Note: Time complexity should be O(height of tree).
Example:
root = [5,3,6,2,4,null,7]
key = 3

    5
   / \
  3   6
 / \   \
2   4   7
Given key to delete is 3. So we find the node with value 3 and delete it.
One valid answer is [5,4,6,2,null,null,7], shown in the following BST.
    5
   / \
  4   6
 /     \
2       7
Another valid answer is [5,2,6,null,4,null,7].
    5
   / \
  2   6
   \   \
    4   7
 */
public class DeleteNodeInABST {
    public static void main(String[] args) {
        System.out.println("******************************** Solution 1 *****************************");
        System.out.println(deleteNode(createTreeNode(new Integer[]{3, 1, 4, null, 2}), 3));
        System.out.println(deleteNode(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 5));
        System.out.println(deleteNode(createTreeNode(new Integer[]{3, 2, 4, 1, null, null, null}), 3));
        System.out.println(deleteNode(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 2));
        System.out.println(deleteNode(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 4));
        System.out.println(deleteNode(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 6));
        System.out.println(deleteNode(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 3));

        System.out.println("******************************** Solution 2 *****************************");
        System.out.println(deleteNode_rev1(createTreeNode(new Integer[]{3, 1, 4, null, 2}), 3));
        System.out.println(deleteNode_rev1(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 5));
        System.out.println(deleteNode_rev1(createTreeNode(new Integer[]{3, 2, 4, 1, null, null, null}), 3));
        System.out.println(deleteNode_rev1(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 2));
        System.out.println(deleteNode_rev1(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 4));
        System.out.println(deleteNode_rev1(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 6));
        System.out.println(deleteNode_rev1(createTreeNode(new Integer[]{5, 3, 6, 2, 4, null, 7}), 3));
    }

    public static TreeNode deleteNode_rev1(TreeNode root, int key) {
        if (root == null) return null;
        if (root.val == key) {
            if (root.left != null && root.right != null) {
//                Get the lowest from right;
                TreeNode lowest = getLowest(root.right);
//                Delete the lowest number
                deleteNode_rev1(root, lowest.val);
//                Assign the lowest value to current node.
                root.val = lowest.val;
            } else if (root.left != null) {
//                If one of the child is not null, then move that child up.
                root = root.left;
            } else if (root.right != null) {
                root = root.right;
            } else {
//            If no any child nodes
                return null;
            }
        } else if (root.val < key) {
            root.right = deleteNode_rev1(root.right, key);
        } else {
            root.left = deleteNode_rev1(root.left, key);
        }
        return root;
    }

    private static TreeNode getLowest(TreeNode root) {
        if (root.left == null) return root;
        return getLowest(root.left);
    }

    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (root.val == key) return deleteCurrentNode(root);
        if (root.val > key) root.left = deleteNode(root.left, key);
        else root.right = deleteNode(root.right, key);
        return root;
    }

    private static TreeNode deleteCurrentNode(TreeNode root) {
//        If root is leaf node.
        if (root.left == null && root.right == null) return null;
//        If root has only one node.
        if (root.left != null && root.right == null) return root.left;
        if (root.right != null && root.left == null) return root.right;
//        If root has two nodes, then find the successor.
        return findAndRemoveSuccessor(root);
    }

    public static TreeNode findAndRemoveSuccessor(TreeNode root) {
        TreeNode successor = findSuccessor(root);
        root = removeSuccessor(root, successor);
        root.val = successor.val;
        return root;
    }

    private static TreeNode removeSuccessor(TreeNode root, TreeNode successor) {
        if (root == null) return null;
        if (root.val == successor.val) {
            if (root.left == null && root.right == null) return null;
            else if (root.right != null) {
                root = root.right;
            } else {
                root = root.left;
            }
            return root;
        }
        if (root.left != null) root.left = removeSuccessor(root.left, successor);
        if (root.right != null) root.right = removeSuccessor(root.right, successor);
        return root;
    }

    private static TreeNode findSuccessor(TreeNode root) {
        root = root.right;
        while (root.left != null) root = root.left;
        return root;
    }

    public static TreeNode createTreeNode(Integer[] integers) {
        Queue<TreeNode> nodes = new LinkedList<>();
        TreeNode head = null;
        int index = 0;
        while (index < integers.length) {
            if (nodes.isEmpty()) {
                Integer current = integers[index++];
                if (current != null) {
                    TreeNode treeNode = new TreeNode(current);
                    nodes.add(treeNode);
                    head = treeNode;
                }
            } else {
//                Create left and right child.
                TreeNode currentHead = nodes.poll();
//                Create left Child.
                Integer left = integers[index++];
                if (left != null) {
                    TreeNode treeNode = new TreeNode(left);
                    currentHead.left = treeNode;
                    nodes.add(treeNode);
                }
//                Create Right Child.
                Integer right = integers[index++];
                if (right != null) {
                    TreeNode treeNode = new TreeNode(right);
                    currentHead.right = treeNode;
                    nodes.add(treeNode);
                }
            }
        }
        return head;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
}
