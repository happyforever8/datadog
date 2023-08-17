public class roo_to_leaf {
    public static void  main(String[] args){
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(8);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(6);
        root.left.right.left = new TreeNode(10);
        root.right.left.left = new TreeNode(7);
        root.right.left.right = new TreeNode(9);
        root.right.right.right = new TreeNode(5);

        int sum = getRootToLeafSum(root);
        System.out.println("The maximum sum is " + sum);
        System.out.print("The maximum sum path is ");

        printPath(root, sum);
    }
    // Function to calculate the maximum root-to-leaf sum in a binary tree
    public static int getRootToLeafSum(TreeNode root){
        if (root == null){
            return Integer.MIN_VALUE;
        }
        if (root.left == null || root.right == null){
            return root.val;
        }
        int left = getRootToLeafSum(root.left);
        int right = getRootToLeafSum(root.right);

        return (left > right ? left : right) + root.val;

    }
    // Function to print the root-to-leaf path with a given sum in a binary tree
    public static boolean printPath(TreeNode root, int sum){
        if (sum == 0 && root == null){
            return true;
        }
        if (root == null){
            return false;
        }
        // recur for the left and right subtree with reduced sum
        boolean left = printPath(root.left, sum - root.val);
        boolean right = false;

        if (!left){
            right = printPath(root.right, sum - root.val);
        }
        if (left || right){
            System.out.println(root.val + " ");
        }
        return left || right;
    }

}


class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
