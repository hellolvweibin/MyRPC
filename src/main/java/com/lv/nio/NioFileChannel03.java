package com.lv.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：使用 FileChannel（通道）和方法 read、write，完成文件的拷贝
 */
public class NioFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("file/1.txt");
        //创建FileChanelImpl 用于文件的读取
        FileChannel channel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("file/2.txt");
        // 创建FileChanelImpl 用于文件的写入
        FileChannel channel02 = fileOutputStream.getChannel();
        //新建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(512);

        //循环读取
        while (true) {
            //这里有一个重要的操作，一定不要忘了
            /*
            public final Buffer clear() {
                position = 0;
                limit = capacity;
                mark = -1;
                return this;
            }
            */
            //buffer.clear();//清空数据（重置标志位，如果没做，会陷入死循环
            int read = channel01.read(buffer);
            System.out.println("read:" + read);
            if (read == -1) {
                //读完结束
                break;
            }
            //
            buffer.flip();
            channel02.write(buffer);

        }
        //关闭流
        fileInputStream.close();
        fileOutputStream.close();


    }
}
