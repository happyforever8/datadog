第二轮Coding, 简单版LC408.
就是给一个word和一个pattern, pattern里有数字，如果看到数字就match word里的几个字符，这题有3 parts
1. 如果数字只是一个digit： word: datadog, pattern: d3dog -> match
2. 数字可以是多个digit: word: accessibility, pattern: a11y -> match
3. 加分项，可以escape数字，in which case escape掉的数字就要match word里面的数字
word: datadog, pattern: d\3dog -> NO match
word: d3dog, pattern: d\3dog -> match.（
这part可以不用写就说思路就行，但是我前两part很快写完所以很快把这个也写完了，就这样加上前后聊天还是多出来15分钟‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌左右，最后这轮提前结束了）


datadog -> d3dog follow up也基本一样（见其他面经，如果数字是两位数或多位数，
  用{1,3}表示等等）。我大概写了下代码， 用memorization 记录index。复杂度。

isMatch("datadog", "datadog") => true
isMatch("datadog", "datadogs") => false
isMatch("3", "aaa") => true
isMatch("3", "aa") => false


 package Tests;

public class WorldPattern {


    public static boolean singleDigitalMatch(String word, String pattern) {
        int wIndex = 0, pIndex = 0;

        while (wIndex < word.length() && pIndex < pattern.length()) {
            char pChar = pattern.charAt(pIndex);

            if (Character.isDigit(pChar)) {
                int num = pChar - '0';
                wIndex += num;
                if (wIndex > word.length()) {
                    return false;
                }
                pIndex++;

            } else {
                if (word.charAt(wIndex) != pChar){
                    return false;
                }
            }
            wIndex++;
            pIndex++;
        }
        return wIndex == word.length() && pIndex == pattern.length();
    }

    public static boolean multipleDigitalMatch(String word, String pattern){
        int wIndex = 0, pIndex = 0;

        while (wIndex < word.length() && pIndex < pattern.length()){
            char pChar = pattern.charAt(pIndex);

             if (Character.isDigit(pChar)){
                int numStart = pIndex;
                while (pIndex < pattern.length() && Character.isDigit(pattern.charAt(pIndex))) {
                    pIndex++;
                }
                int num = Integer.parseInt(pattern.substring(numStart, pIndex));
                wIndex += num;
                if (wIndex > word.length()) {
                    return false; // number is too large
                }
            } else {
                if (word.charAt(wIndex) != pChar){
                    return false;
                }
            }
            wIndex++;
            pIndex++;
        }
        return wIndex == word.length() && pIndex == pattern.length();

    }

    public static boolean escapeMatch(String word, String pattern){
        int wIndex = 0, pIndex = 0;

        while (wIndex < word.length() && pIndex < pattern.length()){
            char pChar = pattern.charAt(pIndex);

            if (pChar == '\\') {
                // Handle escaped characters
                pIndex++;
                if (pIndex >= pattern.length()) {
                    return false; // Invalid escape at end of pattern
                }
                pChar = pattern.charAt(pIndex);
                if (pChar != word.charAt(wIndex)) {
                    return false; // Mismatch after escaping
                }
            } else if (Character.isDigit(pChar)){
                // handle single
                int num = pChar - '0';
                wIndex += num;
                if (wIndex > word.length()){
                    return false;
                }
                pIndex++;
            } else {
                if (word.charAt(wIndex) != pChar){
                    return false;
                }
            }
            wIndex++;
            pIndex++;
        }
        return wIndex == word.length() && pIndex == pattern.length();

    }

    public static void main(String[] args) {
        String word1 = "datadog";
        String pattern1 = "d3dog";
        System.out.println(singleDigitalMatch(word1, pattern1)); // true

        String word2 = "accessibility";
        String pattern2 = "a11y";
        System.out.println(multipleDigitalMatch(word2, pattern2)); // true

        String word3 = "datadog";
        String pattern3 = "d\\3dog";
        System.out.println(escapeMatch(word3, pattern3)); // false

        String word4 = "d3dog";
        String pattern4 = "d\\3dog";
        System.out.println(escapeMatch(word4, pattern4)); // true

        String word5 = "example";
        String pattern5 = "e\\5e";
        System.out.println(escapeMatch(word5, pattern5)); // false

        String word6 = "example";
        String pattern6 = "e10e";
        System.out.println(multipleDigitalMatch(word6, pattern6)); // false
    }
}
