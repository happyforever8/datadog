(1)第三轮，白人小哥，L+Q问题， 建inverted index就可以，有很多follow up，但只要一开始interface建好后面extend非常简单。小哥当即反馈是他见过最全面和整洁的代码


// 设计一个queries search object，它每次读取一行信息string。当这个string格式是
// "Q: hello world" 表示这是个queries， 内容是 hello world
// 如果string 格式是 “L: hello morning world”那么表示这是个log， 内容是 hello morning world。 

//   每次读取query要保存query内容并赋予query id， 每次读取log要把所有在这个log中出现的query id给出来。 
//   这里出现的定义是如果query的每一个word都在log中出现了。
//   注意这里有可能要求log中相同word的出现次数要多于或等于在query中出现的次数，也可能不要求次数，word只要出现即可。
//   但不管怎样，在log中的每个word都能和不同的query中的word重复而独立的匹配。
//   解法是用地里之前提到的reverted index 去记录每一个word在哪些query中出现了，
//   然后遇到log把每个word带入reverted index 去重建 qid-> words list 结构然后和那个qid的word list相比较。
// https://www.1point3acres.com/bbs/thread-1065882-1-1.html



//https://www.1point3acres.com/bbs/thread-1007613-1-1.html 
给一组strings,开头可能是"L: " or "Q: "，如果是Q就是query，后面会跟一组words比如“Q：hello world”; 如果是L就是log,后面同样会跟一组words比如"L: hi hello world"
写一个function，读入这组strings, 如果是query, 要register不同的query并给他们assign一个qid，如果是log, 找到match的query qid并print出来。
Input example:
["Q: hello world",
"Q: data failure",
"Q: world hello",
"L: hello world we have a data failure",
"L: oh no system error",
"Q: system error",
"L: oh no system error again"]

Output would be:
[ "Registered q1",
"Registered q2",
"Registered q1",
"Log q1, q2",
"Log",
"Registered q3",
"Log q3"]
注意有几个tricky part
1. query 里面的单词顺序不管，只要有一样的set of words就算是一样的query。但是单词出现次数要管，比如"hello world world"跟"hello hello world"是两个不一样的query。
2. log里面也是顺序不管，但是单词出现次数要一致。

数据结构设计:

（1）使用 queriesDict 存储已注册的查询，每个查询由其单词计数的哈希值作为键，查询 ID 作为值。
使用 revertedIdx 作为倒排索引，存储每个单词对应的查询 ID 列表，以便快速找到包含该单词的查询。
哈希值生成:

（2）对每个查询，计算其单词计数并生成哈希值，确保顺序一致性（例如，按字典序排序单词）。
处理查询（Q）:

（3）如果查询的哈希值已存在于 queriesDict 中，说明该查询已注册，输出相应的查询 ID。
如果查询的哈希值不存在，将其添加到 queriesDict 和 revertedIdx 中，并分配一个新的查询 ID。
处理日志（L）:

（4）遍历日志中的每个单词，从 revertedIdx 中找到包含该单词的查询 ID。
收集这些查询 ID 及其对应的单词和计数，存储在临时的 queries 中。
最后，通过比较 queries 中的单词计数与 queriesDict 中的哈希值，找到完全匹配的查询 ID，并输出这些查询 ID。


import java.util.*;

public class LogsAndQueries {

//     queriesDict: {hello1world1=1}
//    revertedIdx: {world=[1], hello=[1]}
    private Map<String, Integer> queriesDict;
    private Map<String, List<Integer>> revertedIdx;
    private int id;

    public LogsAndQueries() {
        queriesDict = new HashMap<>();
        revertedIdx = new HashMap<>();
        id = 1;
    }

    private String getHash(Map<String, Integer> counter) {
        List<String> res = new ArrayList<>();
        List<String> keys = new ArrayList<>(counter.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            res.add(key);
            res.add(String.valueOf(counter.get(w)));
        }
        return String.join("", res);
    }

    public void input(String entry) {
        String[] parts = entry.split(":");
        String type = parts[0].trim();
        String content = parts[1].trim();
        String[] words = content.split(" ");
        Map<String, Integer> counter = new HashMap<>();
        for (String word : words) {
            counter.put(word, counter.getOrDefault(word, 0) + 1);
        }

        if (type.equals("Q")) {
            handleQuery(counter);
        } else {
            handleLog(counter, words);
        }
    }

    private void handleQuery(Map<String, Integer> counter) {
        String hash = getHash(counter);
        if (!queriesDict.containsKey(hash)) {
            queriesDict.put(hash, id);
            for (String word : counter.keySet()) {
                revertedIdx.computeIfAbsent(word, k -> new ArrayList<>()).add(id);
            }
            System.out.println("Registered q" + id);
            id++;
        } else {
            System.out.println("Registered q" + queriesDict.get(hash));
        }
        System.out.println("queriesDict: " + queriesDict);
        System.out.println("revertedIdx: " + revertedIdx);
    }

    private void handleLog(Map<String, Integer> counter, String[] words) {
        List<Integer> result = new ArrayList<>();
       //queries 是用来临时存储每个查询与当前日志中匹配的单词及其出现次数
       // queries: {1={world=1, hello=2}, 2={data=1, failure=1}, 3={world=1, hello=2}}
        Map<Integer, Map<String, Integer>> queries = new HashMap<>();
        for (String word : words) {
            if (revertedIdx.containsKey(word)) {
                for (int qid : revertedIdx.get(word)) {
                    queries.computeIfAbsent(q, k -> new HashMap<>())
                           .put(word, queries.get(qid).getOrDefault(word, 0) + 1);
                }
            }
            // 打印每个单词处理后的 queries
            System.out.println("After processing word '" + word + "': " + queries);
        }

        for (int cq : queries.keySet()) {
            String chash = getHash(queries.get(cq));
            if (queriesDict.containsKey(chash) && queriesDict.get(chash) == cq) {
                result.add(cq);
            }
        }

        if (result.isEmpty()) {
            System.out.println("Log");
        } else {
            System.out.print("Log ");
            for (int qid : result) {
                System.out.print("q" + qid + " ");
            }
            System.out.println();
        }

        System.out.println("queriesDict: " + queriesDict);
        System.out.println("revertedIdx: " + revertedIdx);
        System.out.println("queries: " + queries);
    }

    public static void main(String[] args) {
        String[] entries = {
            "Q: hello world",
            "Q: data failure",
            "Q: world hello",
            "Q: world hello hello",
            "L: hello world we have a data failure hello",
            "L: oh no system error",
            "Q: system error",
            "L: oh no system error again"
        };

        LogsAndQueries logsAndQueries = new LogsAndQueries();
        for (String entry : entries) {
            logsAndQueries.input(entry);
        }
    }
}
Registered q1
queriesDict: {hello1world1=1}
revertedIdx: {world=[1], hello=[1]}

Registered q2
queriesDict: {hello1world1=1, data1failure1=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}

Registered q1
queriesDict: {hello1world1=1, data1failure1=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}


queries: {1={world=1, hello=2}, 2={data=1, failure=1}}
Log q2 
queriesDict: {hello1world1=1, data1failure1=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}

queries: {}
Log
queriesDict: {hello1world1=1, data1failure1=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}

Registered q3
queriesDict: {hello1world1=1, data1failure1=2, error1system1=3}
revertedIdx: {system=[3], world=[1], data=[2], failure=[2], hello=[1], error=[3]}

queries: {3={system=1, error=1}}
Log q3 
queriesDict: {hello1world1=1, data1failure1=2, error1system1=3}
revertedIdx: {system=[3], world=[1], data=[2], failure=[2], hello=[1], error=[3]}
