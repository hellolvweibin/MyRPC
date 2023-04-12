package com.lv.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：使用 FileChannel（通道）和方法 transferFrom，完成图片的拷贝
 */
public class NioFileChannel04 {
    public static void main(String[] args) throws IOException {
        //创建相关的流
        FileInputStream fileInputStream = new FileInputStream("page/img01.png");
        FileOutputStream fileOutputStream = new FileOutputStream("page/img02.png");

        //获取流的channel
        FileChannel source = fileInputStream.getChannel();
        FileChannel dest = fileOutputStream.getChannel();

        //使用 transferForm 完成拷贝
        dest.transferFrom(source,0,source.size());

        //关闭流和通道

        source.close();
        dest.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
