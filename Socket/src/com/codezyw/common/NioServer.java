
package com.codezyw.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * TCP/IP的NIO非阻塞方式 服务器端
 */
public class NioServer implements Runnable {
    /**
     * 选择器，主要用来监控各个通道的事件
     */
    private Selector mSelector = null;
    /**
     * 第一个端口
     */
    private Integer mPortA = 8099;
    /**
     * 第二个端口
     */
    private Integer mPortB = 9099;
    /**
     * 服务器地址
     */
    private String mHostName = "localhost";
    /**
     * 第一个服务器通道 服务A
     */
    private ServerSocketChannel mServerSocketChannelA;
    /**
     * 第二个服务器通道 服务B
     */
    private ServerSocketChannel mServerSocketChannelB;
    /**
     * 连接1
     */
    private SocketChannel mSocketChannelA;
    /**
     * 连接2
     */
    private SocketChannel mSocketChannelB;
    /**
     * 缓冲区
     */
    private ByteBuffer mBuffer = ByteBuffer.allocate(512);

    public NioServer() {
        init();
    }

    /**
     * 1：是初始化选择器<br>
     * 2：打开两个通道<br>
     * 3：给通道上绑定一个socket<br>
     * 4：将选择器注册到通道上
     */
    public void init() {
        try {
            // 创建选择器
            this.mSelector = SelectorProvider.provider().openSelector();
            // 打开第一个服务器通道
            this.mServerSocketChannelA = ServerSocketChannel.open();
            // 告诉程序现在不是阻塞方式的
            this.mServerSocketChannelA.configureBlocking(false);
            // 获取现在与该通道关联的套接字
            this.mServerSocketChannelA.socket().bind(new InetSocketAddress(mHostName, this.mPortA));
            // 将选择器注册到通道上，返回一个选择键
            // OP_ACCEPT用于套接字接受操作的操作集位
            this.mServerSocketChannelA.register(this.mSelector, SelectionKey.OP_ACCEPT);

            // 然后初始化第二个服务端
            this.mServerSocketChannelB = ServerSocketChannel.open();
            this.mServerSocketChannelB.configureBlocking(false);
            this.mServerSocketChannelB.socket().bind(new InetSocketAddress(mHostName, this.mPortB));
            this.mServerSocketChannelB.register(this.mSelector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 这个方法是连接 客户端连接服务器
     * 
     * @throws IOException
     */
    public void accept(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        if (server.equals(mServerSocketChannelA)) {
            mSocketChannelA = server.accept();
            mSocketChannelA.configureBlocking(false);
            // OP_READ用于读取操作的操作集位
            mSocketChannelA.register(this.mSelector, SelectionKey.OP_READ);
        } else {
            mSocketChannelB = server.accept();
            mSocketChannelB.configureBlocking(false);
            // OP_READ用于读取操作的操作集位
            mSocketChannelB.register(this.mSelector, SelectionKey.OP_READ);
        }
    }

    /**
     * 从通道中读取数据 并且判断是给那个服务通道的
     * 
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException {
        this.mBuffer.clear();
        // 通过选择键来找到之前注册的通道
        // 但是这里注册的是ServerSocketChannel为什么会返回一个SocketChannel？？
        SocketChannel channel = (SocketChannel) key.channel();
        // 从通道里面读取数据到缓冲区并返回读取字节数
        int count = channel.read(this.mBuffer);

        if (count == -1) {
            // 取消这个通道的注册
            key.channel().close();
            key.cancel();
            return;
        }

        // 将数据从缓冲区中拿出来
        String input = new String(this.mBuffer.array()).trim();
        // 那么现在判断是连接的那种服务
        if (channel.equals(this.mSocketChannelA)) {
            System.out.println("欢迎您使用服务A");
            System.out.println("您的输入为：" + input);
        } else {
            System.out.println("欢迎您使用服务B");
            System.out.println("您的输入为：" + input);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 选择一组键，其相应的通道已为 I/O 操作准备就绪。
                this.mSelector.select();

                // 返回此选择器的已选择键集
                // public abstract Set<SelectionKey> selectedKeys()
                Iterator selectorKeys = this.mSelector.selectedKeys().iterator();
                while (selectorKeys.hasNext()) {
                    // 这里找到当前的选择键
                    SelectionKey key = (SelectionKey) selectorKeys.next();
                    // 然后将它从返回键队列中删除
                    selectorKeys.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        // 如果遇到请求那么就响应
                        this.accept(key);
                    } else if (key.isReadable()) {
                        // 读取客户端的数据
                        this.read(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    public static void main(String[] args) {
        NioServer server = new NioServer();
        Thread thread = new Thread(server);
        thread.start();
    }
}
