package com.lv.netty;

import java.nio.IntBuffer;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：Buffer 的案例
 */
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //往buffer中存数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        //将buffer进行转换
        //flip 在一系列通道读取或 放置 操作之后，调用此方法以准备一系列通道写入或相对 获取 操作。
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
