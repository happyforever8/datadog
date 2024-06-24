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
