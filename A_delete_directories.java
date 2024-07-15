(1)Coding 1: given a file system api, Implement a api to list, delete all files under given path.
followup: how to reduce resource usage.

(2)
    给你一些文件的接口函数：
FindList(string path): find all sub dir and files in current path
Delete(string path): if file or empty dir, delete and return true.
isDir(string path‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌): if path is dir, return true
然后实现rm -rf，删除path下所有内容
解法： recursion
follow up：what if you have out of memory issue?

(3) coding第一轮是给3个function，问如何实现删除一个目录里所有文件的函数，
    followup是如果oom了该怎么办，我给了一个方案以后再问如果这个方案还是oom了该怎么办，最后说了思路时间就差不多了


import java.util.List;

class FileSystem {
    public FileSystem() {
        // Constructor implementation
    }

    public List<String> findList(String path) {
        // Implementation to find and return list of children for the given path
        return null;
    }

    public void delete(String path) {
        // Implementation to delete the given path
    }

    public boolean isDir(String path) {
        // Implementation to check if the given path is a directory
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        deleteDirs("your/path/here", fs);
    }

    //The overall time complexity is O(n), 
    //where n is the total number of files and directories in the given path and all its subdirectories.

    //The overall space complexity is O(d), where d is the maximum depth of the directory tree.

    public static void deleteDirs(String path, FileSystem fs) {
        if (fs.isDir(path)) {
            List<String> children = fs.findList(path);
            for (String child : children) {
                deleteDirs(child, fs);
            }
        }
        fs.delete(path);
    }

    // time and space is same as above
    // iterative solution
    public void DeleteAllFilesAndDir(String path) {
        Stack<String> stack = new Stack<>();
        stack.push(path);
        
        while (!stack.isEmpty()) {
            String currentPath = stack.peek();
            List<String> contents = findList(currentPath);
            boolean isDirEmpty = true;
            
            for (String item : contents) {
                if (isDir(item)) {
                    stack.push(item);
                    isDirEmpty = false;
                } else {
                    delete(item);
                }
            }
            
            // If the directory is empty or its contents have been deleted, remove it
             // After processing the contents, if isDirEmpty is true (meaning the directory is either empty 
             //     or all its contents have been deleted), pop the current path from the stack and delete the directory.
            if (isDirEmpty) {
                stack.pop();
                delete(currentPath);
            }
        }
    }
    
}

OOM 怎么办
方案一：限制栈的深度
我们可以限制一次处理的目录深度来控制内存消耗。当栈达到一定深度时，将当前状态保存到外部存储中，继续处理下一个部分

    import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class FileDeleter {

    void SaveState(Stack<String> stack) {
        // Save the state to an external storage (e.g., a file or database)
    }

    Stack<String> LoadState() {
        // Load the state from external storage
        return new Stack<>();
    }

    public void DeleteAllFilesAndDir(String path) {
        Stack<String> stack = new Stack<>();
        stack.push(path);

        List<String> dirsToDelete = new ArrayList<>();
        int maxDepth = 100; // Adjust based on memory capacity

        while (!stack.isEmpty()) {
            if (stack.size() > maxDepth) {
                SaveState(stack);
                stack = LoadState();
                continue;
            }

            String currentPath = stack.peek();
            List<String> contents = FindList(currentPath);
            boolean isDirEmpty = true;

            for (String item : contents) {
                if (isDir(item)) {
                    stack.push(item);
                    isDirEmpty = false;
                } else {
                    Delete(item);
                }
            }

            if (isDirEmpty) {
                stack.pop();
                Delete(currentPath);
            }
        }

        for (int i = dirsToDelete.size() - 1; i >= 0; i--) {
            Delete(dirsToDelete.get(i));
        }
        Delete(path);
    }

    public static void main(String[] args) {
        FileDeleter deleter = new FileDeleter();
        String path = "your_directory_path";
        deleter.DeleteAllFilesAndDir(path);
    }
}


===========================================
    方案二：分批处理栈
我们可以分批处理栈中的元素，逐步处理每一批来避免一次性加载太多数据。
    import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class FileDeleter {

    void SaveState(Stack<String> stack) {
        // Save the state to an external storage (e.g., a file or database)
    }

    Stack<String> LoadState() {
        // Load the state from external storage
        return new Stack<>();
    }

    public void DeleteAllFilesAndDir(String path) {
        Stack<String> stack = new Stack<>();
        stack.push(path);

        List<String> dirsToDelete = new ArrayList<>();
        int batchSize = 100; // Adjust the batch size based on memory capacity

        while (!stack.isEmpty()) {
            Stack<String> tempStack = new Stack<>();

            while (!stack.isEmpty() && tempStack.size() < batchSize) {
                tempStack.push(stack.pop());
            }

            while (!tempStack.isEmpty()) {
                String currentPath = tempStack.peek();
                List<String> contents = FindList(currentPath);
                boolean isDirEmpty = true;

                for (String item : contents) {
                    if (isDir(item)) {
                        stack.push(item);
                        dirsToDelete.add(item);
                        isDirEmpty = false;
                    } else {
                        Delete(item);
                    }
                }

                if (isDirEmpty) {
                    tempStack.pop();
                    Delete(currentPath);
                }
            }

            if (!stack.isEmpty()) {
                SaveState(stack);
                stack.clear();
                stack.addAll(LoadState());
            }
        }

        for (int i = dirsToDelete.size() - 1; i >= 0; i--) {
            Delete(dirsToDelete.get(i));
        }
        Delete(path);
    }

    public static void main(String[] args) {
        FileDeleter deleter = new FileDeleter();
        String path = "your_directory_path";
        deleter.DeleteAllFilesAndDir(path);
    }
}



=========================================
    import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

class FileSystem {

    public FileSystem() {
    }

    public List<String> findList(String path) {
        List<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path))) {
            for (Path entry : stream) {
                fileList.add(entry.toString());
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e);
        }
        return fileList;
    }

    public boolean delete(String path) {
        if (isDir(path)) {
            List<String> children = findList(path);
            if (children.isEmpty()) {
                try {
                    Files.delete(Paths.get(path));
                    System.out.println("Deleting empty directory: " + path);
                    return true;
                } catch (IOException e) {
                    System.err.println("Failed to delete directory: " + e.getMessage());
                    return false;
                }
            } else {
                System.out.println("Directory not empty: " + path);
                return false;
            }
        } else {
            try {
                Files.delete(Paths.get(path));
                System.out.println("Deleting file: " + path);
                return true;
            } catch (IOException e) {
                System.err.println("Failed to delete file: " + e.getMessage());
                return false;
            }
        }
    }

    public boolean isDir(String path) {
        return Files.isDirectory(Paths.get(path));
    }
