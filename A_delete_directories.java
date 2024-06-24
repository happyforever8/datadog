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
}
