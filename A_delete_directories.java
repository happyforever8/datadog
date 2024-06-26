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

    public static void deleteDirs(String path, FileSystem fs) {
        if (fs.isDir(path)) {
            List<String> children = fs.findList(path);
            for (String child : children) {
                deleteDirs(child, fs);
            }
        }
        fs.delete(path);
    }

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
