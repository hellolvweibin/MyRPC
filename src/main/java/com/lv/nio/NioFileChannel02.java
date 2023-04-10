package com.lv.nio;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：
 */
public class NioFileChannel02 {
    public static void main(String[] args) throws IOException {
        //先创建文件流
        File file = new File("file/writeFIle.text");
        FileInputStream fileInputStream = new FileInputStream(file);
        //通过 fileInputStream 获取对应的 FileChannel -> 实际类型 FileChannelImpl
        FileChannel channel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

        //将通道的数据读取到缓冲区
        channel.read(buffer);
        //将buffer转换成字符串
        System.out.println(new String(buffer.array()));
        //关闭流
        fileInputStream.close();


    }
}
