package com.lv.nio.chatRoom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：服务端
 */
public class GroupChatServer {
    /**
     * 定义属性
     */
    private Selector selector;
    private ServerSocketChannel listenChannel;

    private static final int PORT = 6667;

    /**
     * 构造器 初始化工作
     */
    public GroupChatServer() {

        try {
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置为非阻塞模式
            listenChannel.configureBlocking(false);
            //将listenChannel注册到选择其中, SelectionKey.OP_ACCEPT表示有新的网络可以连接，值为16
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 监听消息
     */
    public void listen() {
        try {
            //循环处理
            while (true) {
                //选择一个键用于处理socket
                int count = selector.select();
                //只要有事件可以处理
                if (count > 0) {
                    //使用迭代器进行遍历
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //1.取出 selectionKey
                        SelectionKey key = iterator.next();
                        //2. 监听accept
                        if (key.isAcceptable()) {
                            //3. 建立和客户端的连接
                            SocketChannel accept = listenChannel.accept();
                            //设置为非阻塞
                            accept.configureBlocking(false);
                            //4. 将accept注册到selector中,设置读操作
                            accept.register(selector, SelectionKey.OP_READ);
                            System.out.println(accept.getRemoteAddress() + "上线了～");
                        }
                        if (key.isReadable()) {
                            //当前通道可读，通道发送read事件
                            readData(key);
                        }
                        //当前的 key 删除，防止重复处理
                        iterator.remove();
                    }

                } else {
                    System.out.println("等待中....");
                }
            }
        } catch (Exception e) {
            System.out.println("出现异常！");
            e.printStackTrace();
        }
    }

    /**
     * @description 读取客户端信息
     */
    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            //1. 得到channel
            channel = (SocketChannel) key.channel();
            //2. 创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //3. 根据count做处理
            if (count > 0) {
                //4. 把缓存区的数据转换成字符串
                String msg = new String(buffer.array());
                //输出消息
                System.out.println("客户端说：" + msg);
                //把消息发给其他客户端
                sendInfoToOtherClients(msg, channel);

            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println(channel.getRemoteAddress() + "离线了...");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @description 转发消息给其它客户(通道)
     */

    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中");
        //遍历所有已经注册到选择其中的SocketChannel,排除自己
        for (SelectionKey key : selector.keys()) {
            //通过key 取出channel
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //msg 存入buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //buffer 数据写入管道
                dest.write(buffer);

            }
        }
    }

    public static void main(String[] args) {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }


}
