import java.util.ArrayList;
import java.util.List;

class Node {
    int val;
    List<Node> children;

    public Node(int val, ArrayList<Node> children) {
        this.val = val;
        this.children = children;
    }
}

public class MaxPathSumNary {
    public static int findMaxPathSum(Node root) {
        if (root == null) {
            return 0;
        }

        return findMaxPathSumHelper(root, 0);
    }

    private static int findMaxPathSumHelper(Node Node, int currentSum) {
        if (Node == null) {
            return currentSum;
        }

        currentSum += Node.val;

        if (Node.children == null || Node.children.isEmpty()) {
            return currentSum;
        }

        int maxChildSum = 0;
        for (Node child : Node.children) {
            maxChildSum = Math.max(maxChildSum, findMaxPathSumHelper(child, currentSum));
        }

        return maxChildSum;
    }

    public static void main(String[] args) {
        Node root = new Node(1, new ArrayList<>());
        Node child1 = new Node(3, new ArrayList<>());
        Node child2 = new Node(2, new ArrayList<>());
        Node child3 = new Node(4, new ArrayList<>());
        Node child4 = new Node(5, new ArrayList<>());
        Node child5 = new Node(6, new ArrayList<>());
        root.children.add(child1);
        root.children.add(child2);
        root.children.add(child3);
        child1.children.add(child4);
        child1.children.add(child5);

        int maxPathSum = findMaxPathSum(root);
        System.out.println("Max path sum: " + maxPathSum);
    }
}

// iternative 
//    public static int findMaxPathSum(Node root) {
//        if (root == null) {
//            return 0;
//        }
//
//        int maxSum = 0;
//        Queue<Node> NodeQueue = new LinkedList<>();
//        Queue<Integer> sumQueue = new LinkedList<>();
//
//        NodeQueue.offer(root);
//        sumQueue.offer(root.val);
//
//        while (!NodeQueue.isEmpty()) {
//            Node Node = NodeQueue.poll();
//            int currentSum = sumQueue.poll();
//
//            if (Node.children == null || Node.children.isEmpty()) {
//                maxSum = Math.max(maxSum, currentSum);
//            }
//
//            if (Node.children != null) {
//                for (Node child : Node.children) {
//                    NodeQueue.offer(child);
//                    sumQueue.offer(currentSum + child.val);
//                }
//            }
//        }
//
//        return maxSum;
//    }


// print out the path
    public static int findMaxPathSum(Node root) {
        if (root == null) {
            return 0;
        }

        int[] maxSum = {0};
        List<Node> maxPath = new LinkedList<>();

        findMaxPathSumHelper(root, 0, new LinkedList<>(), maxSum, maxPath);

        System.out.print("Max path: ");
        printPath(maxPath);
        System.out.println();

        return maxSum[0];
    }
private static void findMaxPathSumHelper(Node node, int currentSum, List<Node> currentPath, int[] maxSum, List<Node> maxPath) {
        if (node == null) {
            return;
        }

        currentSum += node.val;
        currentPath.add(node);

        if (node.children == null || node.children.isEmpty()) {
            if (currentSum > maxSum[0]) {
                maxSum[0] = currentSum;
                maxPath.clear();
                maxPath.addAll(currentPath);
            }
        }

        if (node.children != null) {
            for (Node child : node.children) {
                findMaxPathSumHelper(child, currentSum, currentPath, maxSum, maxPath);
            }
        }

        currentPath.remove(currentPath.size() - 1);
    }

// follow up 是 一‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌个node, 多个parent, dfs/bfs 会重复读取
2，N-tree的root-leaf最大值，标准dfs
可能我写的比较块，问了很多follow up，时间空间，input里有环‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌怎么判断，怎么提升效率等等。白人小哥，待了3年的senior，人挺好的
