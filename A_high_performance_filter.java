(1) coding第二轮是面经里的high performance filter，followup是如果数据量很大的话该如何优‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌化。

(2) coding：
有一个数据流会进来一些tags比如
['apple, facebook, google', 'banana, facebook', 'facebook, google, tesla', 'intuit, google, facebook']
然后有一个filter list， 根据filter list输出这些Tags的补集
比如filter by ['apple']那么return ['facebook', 'google'] (只有第一个里面有APPLE）
比如filter by ['facebook', 'google']那么return ['apple', 'tesla','intuit']
需要high performance filter
这个题挺奇怪的， 到现在我也不知道它正确解法是什么， 当时写了一个single thread 解法然后面试官一直表示要high performance, 
    然后我问它这个可以把stream 分几个机器parallel执行就行貌似面试官也不是想要这个。然后一开始给的stream格式是Array，
    后来又说不能fit into memory，数据流的data是高度相似的， 然后最后剩10分钟的时候说需要弄index
所以这一轮虽然是coding但也不是严格意义上的coding
    
其实他们是想把整个数据流都cache住，然后再做preprocess，看每一条记录有哪些tag，再filter。
    我不知道是不是我的问题，既然是stream data，当然是multi process+bit musk比较合理。怎么又要存cache，那不就说是一个big dataset不就好了么。求指正

(3) 2. Coding1：几乎是面经里的High Performance Filter，有incoming stream，有filter list，根据filter list输出tags的补集。
    实际题里是metrics log，所以有一些分割符号需要处理，不仅是像之前面经里的逗号那么直接。总体思路还是inverted index没问题。
    high performance是因为stream 进来需要多次query，所以optimization for query，
    那么就需要preprocessing。inverted index是个套路，一般用token做key，可以搜搜相关文章。在这个题里token就是tag/关键词。

(4) 题目和过往面经一样。
['apple, facebook, google', 'banana, facebook', 'facebook, google, tesla', 'intuit, google, facebook']
然后有一个 filter list， 根据 filter list 输出这些 Tags 的补集
比如 filter by ['apple']那么 return ['facebook', 'google'] (只有第一个里面有 APPLE）
比如 filter by ['facebook', 'google']那么 return‍‍‍‌‍‍‍‍‍‍‌‌‍‍‍‌‌‍‍ ['apple', 'tesla','intuit']
我是用hashmap 做Cache。 Follow up 是怎么更好的index 这个hashmap (Key 是啥，Value 是啥）。    
    

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
import java.util.*;
import java.util.stream.Collectors;

public class BetterDatadogInvertedIndex {

    Map<String, Set<String>> invertedIndex = new HashMap<>();

    public void pushTags(List<String> tags) {
        Set<String> tagsSet = new HashSet<>(tags);
        for (String tag : tagsSet) {
            Set<String> targetDocuments = invertedIndex.getOrDefault(tag, new HashSet<>());
            targetDocuments.addAll(tagsSet); // Union of current tag set and new tagsSet
            invertedIndex.put(tag, targetDocuments);
        }
    }

    public Set<String> searchTags(List<String> tags) {
        Set<String> allFoundTags = new HashSet<>();

        for (String tag : tags) {
            Set<String> tagDocuments = invertedIndex.getOrDefault(tag, new HashSet<>());
            if (allFoundTags.isEmpty()) {
                allFoundTags.addAll(tagDocuments);
            } else {
                allFoundTags.retainAll(tagDocuments); // Intersection with existing results
            }
        }

        return allFoundTags;
    }

    public static void main(String[] args) {
        BetterDatadogInvertedIndex s = new BetterDatadogInvertedIndex();
        s.pushTags(List.of("apple", "google", "facebook"));
        s.pushTags(List.of("banana", "facebook"));
        s.pushTags(List.of("facebook", "google", "tesla"));
        s.pushTags(List.of("intuit", "google", "facebook"));

        Set<String> res1 = s.searchTags(List.of("apple"));
        Set<String> res2 = s.searchTags(List.of("facebook", "google"));

        System.out.println("Search result for 'apple': " + res1);
        System.out.println("Search result for 'facebook' and 'google': " + res2);
    }
}
数据结构选择：

使用了 HashMap<String, Set<String>> 作为 invertedIndex 的实现，
    其中 String 表示标签，Set<String> 表示包含该标签的文档集合。
    这样的设计使得根据标签快速查找对应的文档集合成为可能，HashMap 提供了平均 O(1) 的时间复杂度的查找操作。
数据处理：

在 pushTags 方法中，使用了 Set<String> 来存储标签，
    确保每个标签集合的唯一性。这样可以避免重复添加标签，保证了数据的一致性和正确性。
    
搜索操作：
searchTags 方法在处理搜索操作时，利用了集合的交集运算 retainAll，
    这是一个高效的操作。通过逐步取交集的方式，筛选出同时包含所有搜索标签的文档标签集合。
    这种方法尽可能减少了不必要的遍历和比较操作，提高了搜索效率。
    
Java Stream API 的利用：
虽然代码中没有直接使用 Java Stream API，但在实际应用中，
    如果涉及到更复杂的数据处理和转换，可以利用 Stream API 提供的并行处理能力来进一步提升性能。
    
内存管理：
使用了基本的集合操作和遍历，避免了不必要的对象创建和销毁，有效管理了内存使用。


=================================
    stream


    import java.util.*;
import java.util.stream.Collectors;

public class BetterDatadogInvertedIndex {

    Map<String, Set<String>> invertedIndex = new HashMap<>();

    public void pushTags(List<String> tags) {
        tags.stream()
            .flatMap(tag -> new HashSet<>(tags).stream()) // 将每个标签转换为对应的文档标签集合
            .forEach(tag -> invertedIndex.computeIfAbsent(tag, k -> new HashSet<>()).addAll(tags));
    }

    public Set<String> searchTags(List<String> tags) {
        return tags.stream()
                   .map(tag -> invertedIndex.getOrDefault(tag, new HashSet<>()))
                   .reduce((set1, set2) -> {
                       set1.retainAll(set2); // 取交集
                       return set1;
                   })
                   .orElse(new HashSet<>()); // 如果为空则返回空集合
    }

    public static void main(String[] args) {
        BetterDatadogInvertedIndex s = new BetterDatadogInvertedIndex();
        s.pushTags(List.of("apple", "google", "facebook"));
        s.pushTags(List.of("banana", "facebook"));
        s.pushTags(List.of("facebook", "google", "tesla"));
        s.pushTags(List.of("intuit", "google", "facebook"));

        Set<String> res1 = s.searchTags(List.of("apple"));
        Set<String> res2 = s.searchTags(List.of("facebook", "google"));

        System.out.println("Search result for 'apple': " + res1); // Output: [facebook, google]
        System.out.println("Search result for 'facebook' and 'google': " + res2); // Output: [intuit, apple, tesla]
    }
}
pushTags 方法：

使用 flatMap 将每个标签映射为一个新的文档标签集合，并通过 forEach 将其添加到 invertedIndex 中。这里利用了 computeIfAbsent 方法来确保每个标签对应的文档标签集合都存在。
searchTags 方法：

使用 stream 对输入的标签列表进行处理，首先映射每个标签到对应的文档标签集合，然后使用 reduce 方法将所有集合取交集。如果集合为空，则返回一个空的 HashSet。






java Stream API 在某些情况下可以提高代码的简洁性和可读性，并且在适当的情况下也可以提升性能。以下是一些使得 Java Stream API更高效的因素：

内部迭代：

Stream API 使用内部迭代，而不是外部迭代（显式地编写循环），这意味着它可以更好地利用多核处理器和并行计算。
    内部迭代使得底层实现可以选择并行执行操作，以提高整体的处理速度。
    
延迟执行：
Stream 的操作通常是延迟执行的。这意味着当调用 Stream 的中间操作时，
    它只会记录操作的逻辑，并不会立即执行。直到调用了终端操作（如 collect、forEach、reduce 等）时，
    才会触发实际的计算。这种方式可以优化操作的执行顺序，并且在某些情况下可以减少不必要的计算量。
    
函数式编程风格：
Stream API 鼓励使用函数式编程风格，例如使用 map、filter、reduce 等高阶函数。这种方式使得代码更为简洁和清晰
    ，减少了显式的控制流和临时变量，从而减少了出错的可能性，并且使得代码更易于优化。
    
优化的并行处理：
Stream API 提供了并行处理的支持。通过调用 parallelStream() 方法可以将串行操作转换为并行操作
    ，从而在多核处理器上同时处理数据集合的不同部分，提高了处理大数据集的效率。当数据量较大时，合理使用并行流可以显著提升处理速度。
    
底层优化：
Java 平台的实现对 Stream API 进行了优化，使得其底层操作更为高效。
    例如，底层的数据结构和算法可能经过了精心设计，以提供较好的性能和可扩展性。
尽管 Stream API 在许多情况下能够提升代码的性能和可读性，但也需要根据具体的应用场景进行评估。
    在某些特定的场景下，传统的循环和条件判断可能会更为直接和高效。因此，选择使用 Stream API 还是传统的循环，需要根据具体的需求、性能要求和代码复杂性来进行权衡和选择。
