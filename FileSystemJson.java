import org.json.*;

public class FileSystemMemoryCalculator {

    public static int calculateMemory(JSONObject obj) {
        int totalMemory = 0;

        for (String key : obj.keySet()) {
            Object value = obj.get(key);

            if (value instanceof JSONObject) {
                totalMemory += calculateMemory((JSONObject) value);
            } else if (value instanceof Integer) {
                totalMemory += (int) value;
            }
        }

        return totalMemory;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "   \"home\":{\n" +
                "      \"me\":{\n" +
                "         \"foo.txt\": 10,\n" +
                "         \"abs.txt\": 2\n" +
                "      },\n" +
                "      \"haha.css\": 3\n" +
                "   }\n" +
                "}";

        try {
            JSONObject fileSystem = new JSONObject(json);
            int totalMemory = calculateMemory(fileSystem);
            System.out.println("Total memory used by all files: " + totalMemory + " KB");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
