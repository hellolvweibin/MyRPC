package com.lv.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：
 */
public class NioFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "hello,world！！！😊";
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("file/writeFIle.text");
        //通过 fileOutputStream 获取对应的 FileChannel
        //这个 fileChannel 真实类型是 FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();
        //创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //str放入buffer
        buffer.put(str.getBytes());

        //对 byteBuffer 进行 flip,读写切换
        buffer.flip();

        //byteBuffer 数据写入到 fileChannel
        channel.write(buffer);
        channel.close();


    }
}
