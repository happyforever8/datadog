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


package Tests;

import java.util.*;

public class LogsAndQueries {
// Data Structures:
// queriesDict: A Map that stores each query's unique hash and its corresponding query ID.
// revertedIdx: A Map that stores each word and the list of query IDs it appears in.
// id: An integer used to assign a unique ID to each new query.

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
            res.add(String.valueOf(counter.get(key)));
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
    }

    private void handleLog(Map<String, Integer> counter, String[] words) {
        List<Integer> result = new ArrayList<>();
        // It creates a temporary Map called queries to store each query and the words it matches in the current log. 
        //     It then generates a hash for each query in the log and checks
        //     if it matches any existing queries in queriesDict. If there are matches, it prints the corresponding query IDs.
        Map<Integer, Map<String, Integer>> queries = new HashMap<>();
        for (String word : words) {
            if (revertedIdx.containsKey(word)) {
                for (int qid : revertedIdx.get(word)) {
                    queries.computeIfAbsent(qid, k -> new HashMap<>())
                            .put(word, queries.get(qid).getOrDefault(word, 0) + 1);
                }
            }
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
                "L: hello world we have a data failure",
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
=====================
Registered q2
queriesDict: {hello1world1=1, data1failure1=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}
=====================
Registered q1
queriesDict: {hello1world1=1, data1failure1=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}
=====================
Log q1 q2 
queriesDict: {hello1world1=1, data1failure1=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}
queries: {1={world=1, hello=1}, 2={data=1, failure=1}}
=====================
Log
queriesDict: {hello1world1=1, data1failure1=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}
queries: {}
=====================
Registered q3
queriesDict: {hello1world1=1, data1failure1=2, error1system1=3}
revertedIdx: {system=[3], world=[1], data=[2], failure=[2], hello=[1], error=[3]}
=====================
Log q3 
queriesDict: {hello1world1=1, data1failure1=2, error1system1=3}
revertedIdx: {system=[3], world=[1], data=[2], failure=[2], hello=[1], error=[3]}
queries: {3={system=1, error=1}}
=====================

========================================
如果不需要考虑出现次数，那么做相应简化即可，思路是一样的。
========================================
package Tests;

import java.util.*;

public class LogsAndQueries2 {
    private Map<Set<String>, Integer> queriesDict;
    private Map<String, List<Integer>> revertedIdx;
    private int id;

    public LogsAndQueries2() {
        this.queriesDict = new HashMap<>();
        this.revertedIdx = new HashMap<>();
        this.id = 1;
    }

    // Helper method to get a sorted set of words
    private Set<String> getSortedWords(String[] words) {
        return new TreeSet<>(Arrays.asList(words));
    }

    public void input(String entry) {
        String[] parts = entry.split(":");
        String tp = parts[0];
        String content = parts[1].trim();
        String[] words = content.split(" ");
        Set<String> sortedWords = getSortedWords(words);

        if (tp.equals("Q")) {
            if (!queriesDict.containsKey(sortedWords)) {
                queriesDict.put(sortedWords, id);
                for (String word : sortedWords) {
                    revertedIdx.computeIfAbsent(word, k -> new ArrayList<>()).add(id);
                }
                id++;
            }
            System.out.println("receive query " + queriesDict.get(sortedWords));
        } else {
            List<Integer> result = new ArrayList<>();
            Map<Integer, Set<String>> queries = new HashMap<>();
            for (String word : words) {
                if (revertedIdx.containsKey(word)) {
                    for (int q : revertedIdx.get(word)) {
                        queries.computeIfAbsent(q, k -> new TreeSet<>()).add(word);
                    }
                }
            }
            for (Map.Entry<Integer, Set<String>> entrySet : queries.entrySet()) {
                int cq = entrySet.getKey();
                Set<String> chash = getSortedWords(entrySet.getValue().toArray(new String[0]));
                if (queriesDict.containsKey(chash) && queriesDict.get(chash) == cq) {
                    result.add(cq);
                }
            }
            System.out.println("receive log and the relevant queries are " + result);

            System.out.println("queriesDict: " + queriesDict);
            System.out.println("revertedIdx: " + revertedIdx);
            System.out.println("queries: " + queries);

            System.out.println("==========================");
        }
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

        LogsAndQueries2 logsAndQueries2 = new LogsAndQueries2();
        for (String entry : entries) {
            logsAndQueries2.input(entry);
        }
    }
}

receive query 1
receive query 2
receive query 1
receive query 1
receive log and the relevant queries are [1, 2]
queriesDict: {[hello, world]=1, [data, failure]=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}
queries: {1=[hello, world], 2=[data, failure]}
==========================
receive log and the relevant queries are []
queriesDict: {[hello, world]=1, [data, failure]=2}
revertedIdx: {world=[1], data=[2], failure=[2], hello=[1]}
queries: {}
==========================
receive query 3
receive log and the relevant queries are [3]
queriesDict: {[error, system]=3, [hello, world]=1, [data, failure]=2}
revertedIdx: {system=[3], world=[1], data=[2], failure=[2], hello=[1], error=[3]}
queries: {3=[error, system]}
==========================

    
