package com.lv.nio;

import java.nio.ByteBuffer;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：可以将一个普通 Buffer 转成只读 Buffer
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        //创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);

        for (int i = 0; i < 61; i++) {
            buffer.put((byte) i);
        }
        //读取
        buffer.flip();

        //得到一个只读的Buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        //读取
        while(readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }

        //会报错 BufferUnderflowException
        readOnlyBuffer.put((byte) 100);
    }
}
