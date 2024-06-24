https://www.1point3acres.com/bbs/thread-1065882-1-1.html
第二题： implement a buffered writer: 这个writer存在一个buffer 和可设置的最大buffer size，这里的buffer工作方式是
1， 读取的数据先存在buffer里面不写入硬盘
2， 当buffer里的数据量达到buffer size的时候，一次性清空所有buffer内的数据到硬盘中。如此往复。

  import java.util.ArrayList;
import java.util.List;

public class BufferedFile {
    private int maxBufferedSize;
    private List<Character> diskStorage;
    private List<Character> buffer;

    public BufferedFile(int maxBufferedSize) {
        this.maxBufferedSize = maxBufferedSize;
        this.diskStorage = new ArrayList<>();
        this.buffer = new ArrayList<>();
    }

    public void write(String content) {
        for (char ch : content.toCharArray()) {
            buffer.add(ch);
            if (buffer.size() == maxBufferedSize) {
                flush();
            }
        }
    }

    // Dummy writing to hard disk API, just for illustration purposes
    private void flush() {
        diskStorage.addAll(buffer);
        buffer.clear();
    }

    public List<Character> getDiskStorage() {
        return diskStorage;
    }

    public List<Character> getBuffer() {
        return buffer;
    }

    public static void main(String[] args) {
        BufferedFile bufferedFile = new BufferedFile(5);
        bufferedFile.write("HelloWorld");
        System.out.println(bufferedFile.getDiskStorage()); // Should print [H, e, l, l, o]
        System.out.println(bufferedFile.getBuffer()); // Should print [W, o, r, l, d]
    }
}