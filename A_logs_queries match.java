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




  import java.util.*;

public class Main {
    private static Map<String, Integer> queriesDict;
    private static Map<String, List<Integer>> revertedIdx;
    private static int id;

    public static void main(String[] args) {
        queriesDict = new HashMap<>();
        revertedIdx = new HashMap<>();
        id = 1;

        input("Q: hello world");
        input("Q: data failure");
        input("Q: world hello");
        input("L: hello world we have a data failure");
        input("L: oh no system error");
        input("Q: system error");
        input("L: oh no system error again");
    }

    public static String getHashFromCounters(Map<String, Integer> counter) {
        List<String> keys = new ArrayList<>(counter.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String w : keys) {
            sb.append(w);
            sb.append(counter.get(w));
        }
        return sb.toString();
    }

    public static void input(String entry) {
        String[] parts = entry.split(":");
        String tp = parts[0].trim();
        String content = parts[1].trim();
        String[] words = content.split(" ");

        if (tp.equals("Q")) {
            Map<String, Integer> counter = new HashMap<>();
            for (String word : words) {
                counter.put(word, counter.getOrDefault(word, 0) + 1);
            }
            String hash = getHashFromCounters(counter);
            if (!queriesDict.containsKey(hash)) {
                queriesDict.put(hash, id);
                for (String word : counter.keySet()) {
                    if (!revertedIdx.containsKey(word)) {
                        revertedIdx.put(word, new ArrayList<>());
                    }
                    revertedIdx.get(word).add(id);
                }
                System.out.println("Registered q" + id);
                id++;
            } else {
                System.out.println("Registered q" + queriesDict.get(hash));
            }
        } else if (tp.equals("L")) {
            List<Integer> result = new ArrayList<>();
            for (String word : words) {
                if (revertedIdx.containsKey(word)) {
                    for (int q : revertedIdx.get(word)) {
                        if (!result.contains(q)) {
                            result.add(q);
                        }
                    }
                }
            }
            if (result.isEmpty()) {
                System.out.println("Log");
            } else {
                StringBuilder logResult = new StringBuilder("Log");
                for (int q : result) {
                    logResult.append(" q").append(q);
                }
                System.out.println(logResult.toString());
            }
        }
    }
}
