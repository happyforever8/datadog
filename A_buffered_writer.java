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

============== add error handling and thread safe=======================
  import java.util.ArrayList;
import java.util.List;

public class BufferedFile {
    private final int maxBufferedSize;
    private final StringBuilder buffer;
    private final List<String> diskStorage;

    public BufferedFile(int maxBufferedSize) {
        this.maxBufferedSize = maxBufferedSize;
        this.buffer = new StringBuilder();
        this.diskStorage = new ArrayList<>();
    }

    public synchronized void write(String content) {
        buffer.append(content);
        while (buffer.length() >= maxBufferedSize) {
            flush();
        }
    }

    private synchronized void flush() {
        try {
            while (buffer.length() >= maxBufferedSize) {
                diskStorage.add(buffer.substring(0, maxBufferedSize));
                buffer.delete(0, maxBufferedSize);
            }
            if (buffer.length() > 0 && buffer.length() < maxBufferedSize) {
                diskStorage.add(buffer.toString());
                buffer.setLength(0); // Clear the buffer
            }
        } catch (Exception e) {
            System.err.println("Error during flush: " + e.getMessage());
        }
    }

    public synchronized List<String> getDiskStorage() {
        return new ArrayList<>(diskStorage); // Return a copy to preserve encapsulation
    }

    public static void main(String[] args) {
        BufferedFile bufferedFile = new BufferedFile(5);
        bufferedFile.write("HelloWorld");

        // Printing the contents of the diskStorage
        System.out.println(bufferedFile.getDiskStorage()); // Output: [Hello, World]
    }
}



// n is the total length of the content being written before flushing.
// k is the average length of each substring (in this case, maxBufferedSize).  
// Write Operation:

// Time Complexity: o(n)
// Space Complexity: O(n)

// Flush Operation:

// Time Complexity: O(n)

// Space Complexity: O(n)

// Get Disk Storage Operation:

// Time Complexity: O(k)
// Space Complexity: O(k)



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

  List

线程安全性：否
主要用途：存储和操作多种对象
性能：低（字符串拼接）
操作方法：add, remove, get, set等
内存使用：高（对象引用）
动态扩展：是（自动扩展容量）
适用场景：需要存储和操作不同类型对象
优点：灵活，支持多种操作，随机访问时间复杂度O(1)（ArrayList）
缺点：字符串拼接性能较差，内存占用高

    
StringBuilder
线程安全性：否
主要用途：高效字符串拼接
性能：高（单线程环境）
操作方法：append, insert, delete, replace等
内存使用：较低（连续字符数组）
动态扩展：是（自动扩展容量）
适用场景：频繁的字符串拼接操作（单线程）
优点：高效的字符串拼接，低内存开销
缺点：线程不安全，单一用途（字符串操作）
    
StringBuffer
线程安全性：是
主要用途：线程安全的高效字符串拼接
性能：中等（有同步开销）
操作方法：append, insert, delete, replace等
内存使用：较低（连续字符数组）
动态扩展：是（自动扩展容量）
适用场景：频繁的字符串拼接操作（多线程）
优点：线程安全的字符串拼接，低内存开销
缺点：有同步开销，性能较StringBuilder低
    
通过上述对比，可以根据具体的需求选择合适的类。如果是单线程环境中的字符串拼接操作，
StringBuilder是最佳选择；如果需要线程安全的操作，则应使用StringBuffer；
    而List适用于需要存储和操作多种对象类型的场景，但不适合频繁的字符串拼接操作。


