import java.util.*;

/*
Example:
Input: [1,null,2,3]
   1
    \
     2
    /
   3
PreOrder Traversal Output: [1,2,3]
InOrder Traversal Output: [1,3,2]
PostOrder Traversal Output: [3,2,1]
 */
public class TraverseATree {
    public static void main(String[] args) {
        TreeNode treeNode = createTreeNode(new ArrayList<>(Arrays.asList(5, 6, 7, 3, 4, 3, 2, null, 1, null, null, null, null, null, 9)));
        System.out.println("===================Recursive========================");
        System.out.println("Actual Node\t:" + treeNode);
        System.out.println("PreOrder   \t:" + preorderTraversal(treeNode));
        System.out.println("InOrder    \t:" + inorderTraversal(treeNode));
        System.out.println("PostOrder  \t:" + postorderTraversal(treeNode));
        System.out.println("LevelOrder \t:" + levelOrder(treeNode));
        System.out.println("===================Iterative========================");
        System.out.println("Actual Node\t:" + treeNode);
        System.out.println("PreOrder   \t:" + preorderTraversal_iterative(treeNode));
        System.out.println("InOrder    \t:" + inorderTraversal_iterative(treeNode));
        System.out.println("PostOrder  \t:" + postorderTraversal(treeNode));
        System.out.println("LevelOrder \t:" + levelOrder(treeNode));
    }

    public static TreeNode createTreeNode(ArrayList<Integer> nums) {
        Queue<TreeNode> treeNodeQueue = new LinkedList<>();
        int index = 1;
        TreeNode treeNode = new TreeNode(nums.get(0));
        treeNodeQueue.add(treeNode);

        while (index < nums.size() && !treeNodeQueue.isEmpty()) {
            TreeNode peek = treeNodeQueue.poll();
            if (nums.get(index) != null) {
                TreeNode left = new TreeNode(nums.get(index++));
                peek.left = left;
                treeNodeQueue.add(left);
            } else index++;
            if (index < nums.size() && nums.get(index) != null) {
                TreeNode right = new TreeNode(nums.get(index++));
                peek.right = right;
                treeNodeQueue.add(right);
            } else index++;
        }
        return treeNode;
    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        output.add(root.val);
        output.addAll(preorderTraversal(root.left));
        output.addAll(preorderTraversal(root.right));
        return output;
    }

    public static List<Integer> preorderTraversal_iterative(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        if (root == null) return output;
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode peek = stack.pop();
            if (peek != null) {
                output.add(peek.val);
                stack.add(peek.right);
                stack.add(peek.left);
            }
        }
        return output;
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        output.addAll(inorderTraversal(root.left));
        output.add(root.val);
        output.addAll(inorderTraversal(root.right));
        return output;
    }

    public static List<Integer> inorderTraversal_iterative(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        if (root == null) return output;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            output.add(curr.val);
            curr = curr.right;
        }
        return output;
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        output.addAll(postorderTraversal(root.left));
        output.addAll(postorderTraversal(root.right));
        output.add(root.val);
        return output;
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ouput = new ArrayList<>();
        if (root == null) return ouput;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                level.add(poll.val);
                if (poll.left != null) queue.add(poll.left);
                if (poll.right != null) queue.add(poll.right);
            }
            ouput.add(level);
        }
        return ouput;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> op = new ArrayList<>();
        if (root == null) return op;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int index = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            if (index % 2 == 1) Collections.reverse(level);
            op.add(level);
            index++;
        }
        return op;
    }
}