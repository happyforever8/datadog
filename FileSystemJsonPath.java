import org.json.JSONObject;

class FileSystem_path {
    private JSONObject root;

    public FileSystem_path(JSONObject root) {
        this.root = root;
    }

    public int calculateSizeAtPath(String path) {
        JSONObject currentNode = root;

        String[] pathComponents = path.split("/");
        for (String component : pathComponents) {
            if (component.isEmpty()) continue;

            if (!currentNode.has(component)) {
                return -1; // Path doesn't exist
            }
            currentNode = currentNode.getJSONObject(component);
        }

        return calculateTotalSize(currentNode);
    }

    public int calculateTotalSize(JSONObject node) {
        int totalSize = 0;

        for (String key : node.keySet()) {
            Object value = node.get(key);

            if (value instanceof Integer) {
                totalSize += (int) value;
            } else if (value instanceof JSONObject) {
                totalSize += calculateTotalSize((JSONObject) value);
            }
        }

        return totalSize;
    }

    public static void main(String[] args) {
        String jsonString = "{\"home\": {\"me\": {\"foo.txt\": 231, \"abs.txt\": 443}, \"haha.css\": 52}}";

        JSONObject root = new JSONObject(jsonString);
        FileSystem_path fileSystem = new FileSystem_path(root);

        String specificPath = "/home/me/1";
        int pathSize = fileSystem.calculateSizeAtPath(specificPath);

        if (pathSize >= 0) {
            System.out.println("Total size at path " + specificPath + ": " + pathSize + " KB");
        } else {
            System.out.println("Path " + specificPath + " not found.");
        }
    }
}

