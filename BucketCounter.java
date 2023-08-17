import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BucketCounter {
    public static Map<String, Integer> countBuckets(List<Integer> integers, int numBuckets, int bucketWidth) {
        Map<String, Integer> bucketCountMap = new HashMap<>();

        if (integers == null || integers.isEmpty() || numBuckets <= 0 || bucketWidth <= 0) {
            return bucketCountMap;
        }

        int[] buckets = new int[numBuckets];

        for (int num : integers) {
            int bucketIndex = num / bucketWidth;
            if (bucketIndex >= numBuckets) {
                bucketIndex = numBuckets - 1;
            }
            buckets[bucketIndex]++;
        }

        for (int i = 0; i < numBuckets; i++) {
            String bucketRange = (i * bucketWidth) + "-" + ((i * bucketWidth) + bucketWidth - 1);
            bucketCountMap.put(bucketRange, buckets[i]);
        }

        return bucketCountMap;
    }

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(11);
        integers.add(20);
        integers.add(100);

        int numBuckets = 3;
        int bucketWidth = 1;

        Map<String, Integer> bucketCountMap = countBuckets(integers, numBuckets, bucketWidth);
        for (Map.Entry<String, Integer> entry : bucketCountMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
