
There is a stream that has coming tags and also has a list of keywords, 
design a high performance filter to output these keywords remaining tags.
For example: given stream ['apple, facebook, google', 'banana, facebook', 'facebook, google, tesla', 'intuit, google, facebook'], 
if the keyword is ['apple'] the output should ['facebook', 'google'] because only 'apple, facebook, google' has apple. 
Similarly if the keyword is ['facebook', 'google'], the output should ['apple', 'tesla', 'intuit']. The output can be in any order and can be put into a single list/array.

I was not sure how to handle these:

High performance filter.
The tags are coming as in a stream.
Thanks for the help!
https://leetcode.com/discuss/interview-question/2639509/DataDog-Interview-Question


题目和过往面经一样。
['apple, facebook, google', 'banana, facebook', 'facebook, google, tesla', 'intuit, google, facebook']
然后有一个 filter list， 根据 filter list 输出这些 Tags 的补集
比如 filter by ['apple']那么 return ['facebook', 'google'] (只有第一个里面有 APPLE）
比如 filter by ['facebook', 'google']那么 return‍‍‍‌‍‍‍‍‍‍‌‌‍‍‍‌‌‍‍ ['apple', 'tesla','intuit']
我是用hashmap 做Cache。 Follow up 是怎么更好的index 这个hashmap (Key 是啥，Value 是啥）。

package Datadog;

import java.util.*;
public class HighPerformanceFilter {

    Map<String, Set<Integer>> streamMap = new HashMap<>();
    List<String> stream = new ArrayList<>();

    public void addTag(String tag){
        int index = stream.size();
        for(String s : tag.split(",")){
            s = s.trim().toLowerCase();
            streamMap.putIfAbsent(s, new HashSet<>());
            streamMap.get(s).add(index);
        }
        stream.add(tag);
    }

    public Set<String> searchTags(List<String> keywords) {
        Map<Integer, Integer> counterMap = new HashMap<>();
        for(String keyword : keywords){
            for(int document : streamMap.getOrDefault(keyword, new HashSet<>())){
                counterMap.put(document, counterMap.getOrDefault(document, 0)+1);
            }
        }
        Set<String> set = new HashSet<>();
        for(int key : counterMap.keySet()){
            if(counterMap.get(key) == keywords.size()){
                set.addAll(Arrays.asList(stream.get(key).split(", ")));
            }
        }
        for(String i : keywords){
            set.remove(i);
        }
        return set;
    }



    public static void main(String[] args){
        HighPerformanceFilter h = new HighPerformanceFilter();
        h.addTag("apple, facebook, google");
        h.addTag("banana, facebook");
        h.addTag("facebook, google, tesla");
        h.addTag("intuit, google, facebook");
        System.out.println(h.searchTags(Arrays.asList(new String[] {"facebook", "google"})));
    }
}



//   second way
public class DatadogInvertedIndex {

    Map<String, Set<Set<String>>> invertedIndex = new HashMap<>();

    public void pushTags(List<String> tags) {
        Set<String> tagsSet = new HashSet<>(tags);
        for (String tag : tagsSet) {
            Set<Set<String>> targetDocuments = invertedIndex.getOrDefault(tag, new HashSet<>());
            targetDocuments.add(tagsSet);
            invertedIndex.put(tag, targetDocuments);
        }
    }

    public Set<String> searchTags(List<String> tags) {
        Set<String> allFoundTags = searchTag(tags.get(0)).stream()
                .filter(s -> s.containsAll(tags))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        tags.forEach(allFoundTags::remove);
        return allFoundTags;
    }

    private Set<Set<String>> searchTag(String tag) {
        return invertedIndex.getOrDefault(tag, new HashSet<>());
    }

    public static void main(String[] args) {
        DatadogInvertedIndex s = new DatadogInvertedIndex();
        s.pushTags(List.of("apple","google", "facebook"));
        s.pushTags(List.of("banana","facebook"));
        s.pushTags(List.of("facebook", "google", "tesla"));
        s.pushTags(List.of("intuit", "google", "facebook"));

        Set<String> res1 = s.searchTags(List.of("apple"));
        Set<String> res2 = s.searchTags(List.of("facebook", "google"));
    }
}
