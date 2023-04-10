package com.lv.netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：BIO 服务
 */
public class BIOServer {
    public static void main(String[] args) throws Exception{
        //线程池机制 1.创建线程池 2.如果有客户端连接，就分配一个线程与之通信
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        //死循环监听
        while(true){
            System.out.println("线程id:"+Thread.currentThread().getId()+"名字="+Thread.currentThread().getName());
            //监听客户端连接
            System.out.println("等待客户端连接");
            final Socket accept = serverSocket.accept();
            System.out.println("连接到一个客户端");
            //监听到客户端链接，创建一个线程进行处理
            newCachedThreadPool.execute(new Runnable() {
                //重写run
                @Override
                public void run() {
                    //与客户端通信
                    handler(accept);
                }
            });
        }

    }
    /**
     * 建立方法与客户端通信
     */
    public static void handler(Socket socket){
        System.out.println("线程信息id = " + Thread.currentThread().getId() + "名字 = " + Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        //通过socket获取流
        try {
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端发送的数据
            while(true){
                System.out.println("线程信息id = " + Thread.currentThread().getId() + "名字 = " + Thread.currentThread().getName());
                System.out.println("正在读取中....");
                int read = inputStream.read(bytes);
                if(read != -1){
                    //输出客户端发送的数据,把字节流转换成字符串
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和客户端的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}
