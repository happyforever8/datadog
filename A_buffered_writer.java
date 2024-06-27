(1)https://www.1point3acres.com/bbs/thread-1065882-1-1.html
第二题： implement a buffered writer: 这个writer存在一个buffer 和可设置的最大buffer size，这里的buffer工作方式是
1， 读取的数据先存在buffer里面不写入硬盘
2， 当buffer里的数据量达到buffer size的时候，一次性清空所有buffer内的数据到硬盘中。如此往复。


(2)coding 2: Given a File class, implement a bufferedfile to simulate file writer with buffer
follow up: optimize buffer


(3) 给你一些文件的接口函数：
write(const uint8_t* bytes, int nBytes)
flush()
然后实现BufferedFile类，要求模拟一个memory storage
constructor： (File* f, int nMaxBufferedBytes)；nMaxBufferedBytes is memory size
write(const uint8_t* bytes, int nBytes)： 优先写入memory，memory FIFO写入disk
flush(): from mem to disk

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


优化 BufferedFile 类的缓冲区可以通过减少内存分配和拷贝操作来实现。
  我们可以使用 StringBuilder 替代 ArrayList<Character> 来更高效地管理字符缓冲区。
  此外，可以直接将内容块写入磁盘，而不是逐个字符写入，从而减少操作次数。

  import java.util.ArrayList;
import java.util.List;

public class BufferedFile {
    private int maxBufferedSize;
    private StringBuilder buffer;
    private List<String> diskStorage;

    public BufferedFile(int maxBufferedSize) {
        this.maxBufferedSize = maxBufferedSize;
        this.buffer = new StringBuilder();
        this.diskStorage = new ArrayList<>();
    }

    public void write(String content) {
        buffer.append(content);
        while (buffer.length() >= maxBufferedSize) {
            flush();
        }
    }

    private void flush() {
        if (buffer.length() > maxBufferedSize) {
            diskStorage.add(buffer.substring(0, maxBufferedSize));
            buffer.delete(0, maxBufferedSize);
        } else {
            diskStorage.add(buffer.toString());
            buffer.setLength(0); // Clear the buffer
        }
    }

    public List<String> getDiskStorage() {
        return new ArrayList<>(diskStorage); // Return a copy to preserve encapsulation
    }

    public static void main(String[] args) {
        BufferedFile bufferedFile = new BufferedFile(5);
        bufferedFile.write("HelloWorld");

        // Printing the contents of the diskStorage
        System.out.println(bufferedFile.getDiskStorage()); // Output: [Hello, World]
    }
}
使用 StringBuilder：

替换了 List<Character>，因为 StringBuilder 对字符追加操作更高效。
批量写入：

在 write 方法中，使用 append 将整个字符串追加到缓冲区中。
在 flush 方法中，检查缓冲区的长度并根据需要将内容块写入 diskStorage，从而减少单个字符写入的次数。
清空缓冲区：

使用 buffer.setLength(0) 来清空缓冲区，比逐个清除字符更高效。
磁盘存储格式：

diskStorage 存储的是字符串块，而不是字符列表，从而更高效地管理存储内容。
这种优化通过减少内存分配、拷贝和操作次数来提高整体性能，特别是在处理大数据量时。


