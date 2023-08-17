import java.util.HashMap;
import java.util.Map;

 // 给一个file system structure (json) 算出所有文件的内存大小。去壳：遍历树并求叶子的值之和
     
// tims is O(N), n is path length
// space is O(h), h is the depth of file
class FileSystemNode {
    String name;
    boolean isFile;
    Map<String, FileSystemNode> children;
    int size;

    public FileSystemNode(String name) {
        this.name = name;
        this.children = new HashMap<>();
        this.isFile = false;
        this.size = 0;
    }
}

public class FileSystem {
    public static int calculateTotalSize(FileSystemNode root) {
        if (root == null) {
            return 0;
        }

        if (root.isFile) {
            return root.size;
        }

        int totalSize = 0;
        for (FileSystemNode child : root.children.values()) {
            totalSize += calculateTotalSize(child);
        }

        return totalSize;
    }

    public static void main(String[] args) {
        FileSystemNode root = new FileSystemNode("home");
        FileSystemNode me = new FileSystemNode("me");
        me.children.put("foo.txt", new FileSystemNode("foo.txt"));
        me.children.get("foo.txt").isFile = true;
        me.children.get("foo.txt").size = 231;
        me.children.put("abs.txt", new FileSystemNode("abs.txt"));
        me.children.get("abs.txt").isFile = true;
        me.children.get("abs.txt").size = 443;
        root.children.put("me", me);

        int totalSize = calculateTotalSize(root);
        System.out.println("Total file size: " + totalSize);

//        for (Map.Entry<String, FileSystemNode> entry : ){
//
//        }
    }
}


// to get the specific dir
//public int calculateTotalSize(String filePath) {
//    FileSystemNode node = getNodeAtPath(filePath);
//    if (node == null) {
//        return 0;
//    }
//
//    return calculateTotalSize(node);
//}
//private FileSystemNode getNodeAtPath(String filePath) {
//    String[] pathElements = filePath.split("/");
//    FileSystemNode currentNode = this;
//
//    for (String element : pathElements) {
//        if (element.isEmpty()) {
//            continue;
//        }
//
//        currentNode = currentNode.children.get(element);
//        if (currentNode == null) {
//            return null; // Node not found
//        }
//    }
//
//    return currentNode;
//}
//
//    private int calculateTotalSize(FileSystemNode node) {
//        if (node == null) {
//            return 0;
//        }
//
//        int totalSize = node.size;
//
//        for (FileSystemNode child : node.children.values()) {
//            totalSize += calculateTotalSize(child);
//        }
//
//        return totalSize;
//    }
//}
//




