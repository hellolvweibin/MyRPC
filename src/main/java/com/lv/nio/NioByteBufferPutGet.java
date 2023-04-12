package com.lv.nio;

import java.nio.ByteBuffer;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：ByteBuffer 支持类型化的 put 和 get
 */
public class NioByteBufferPutGet {
    public static void main(String[] args) {
        //创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);

        //类型话方式放入数据
        buffer.putInt(100);
        buffer.putChar('C');
        buffer.putShort((short) 4);
        buffer.putLong(9);

        //读写反转
        buffer.flip();

        System.out.println();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getShort());

    }
}
