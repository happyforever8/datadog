import java.util.*;

public class Repetition {
   // question here
    // 1. Do we need to care about capital and small letter
    // 2. Do we need to care about punctuation
    // 3.
    public static void main(String[] args){
        String str =  "The sun is the largest object in the solar system. It is the only star. And the sun is bright.";

        System.out.println(calculte(str));
        String str1 = "This is a nice car, I would like to buy one. But I don't have enough money";
        System.out.println(calculte(str1));
    }
    // space is O(N)
    // tims is O(N)
    public static int calculte(String str){
        if (str == null || str.length() == 0){
            return 0;
        }

        str = str.replaceAll("[^a-zA-Z ]", "");
        int result = 0;
        Map<String, Integer> map = new HashMap<>();
//        space just matches a space. The \s code matches any kind of "white space",
//                including space, tab, vertical tab, return, new line, and form feed.
        for (String s : str.split("\\s")){
            s = s.toLowerCase();
            if (!map.containsKey(s)){
                map.put(s, 1);
            } else {
                result++;
                map.put(s, map.get(s) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()){
            if (entry.getValue() > 1){
                System.out.println("value is : " + entry.getKey() + " , count is : " + entry.getValue());
            }

        }
        return result;
    }
}
