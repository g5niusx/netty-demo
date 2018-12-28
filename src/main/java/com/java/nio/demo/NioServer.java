package com.java.nio.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 基于nio实现的服务端
 *
 * @author g5niusx
 */
@Slf4j
public class NioServer {
    private static final Integer  PORT = 8888;
    /**
     * 多路复用器
     */
    private              Selector selector;

    private NioServer() throws Exception {
        // 打开channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置为非阻塞channel,这样才能注册到多路复用器中
        serverSocketChannel.configureBlocking(false);
        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        // 创建多路复用器
        selector = Selector.open();
        // 将channel注册到多路复用器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("服务端已经启动,正在监听[{}]端口", PORT);

        handle();
    }

    /**
     * 处理socket的请求
     */
    private void handle() throws IOException {
        while (true) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }
            // 过滤掉无效的key然后再去处理
            selector.selectedKeys().stream().filter(SelectionKey::isValid).forEach(selectionKey -> {
                selector.selectedKeys().remove(selectionKey);
                handleKey(selectionKey);
            });
        }
    }

    /**
     * 处理不同事件的逻辑
     *
     * @param selectionKey 事件
     */
    private void handleKey(SelectionKey selectionKey) {
        try {
            // 接受连接就绪
            if (selectionKey.isAcceptable()) {
                handleAcceptableKey(selectionKey);
            }
            if (selectionKey.isReadable()) {
                SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
                // 读取数据
                ByteBuffer readBuffer = read(clientSocketChannel);
                // 处理连接已经断开的情况
                if (readBuffer == null) {
                    System.out.println("断开 Channel");
                    clientSocketChannel.register(selector, 0);
                    return;
                }
                // 打印数据
                if (readBuffer.position() > 0) {
                    String content = newString(readBuffer);
                    System.out.println("读取数据：" + content);
                    // 添加到响应队列
                    List<String> responseQueue = (ArrayList<String>) selectionKey.attachment();
                    responseQueue.add("响应：" + content);
                    // 注册 Client Socket Channel 到 Selector
                    clientSocketChannel.register(selector, SelectionKey.OP_WRITE, selectionKey.attachment());
                }
            }
        } catch (Exception e) {
            log.error("处理事件异常", e);
        }

    }

    private void handleAcceptableKey(SelectionKey selectionKey) throws Exception {
        // 接受 Client Socket Channel
        SocketChannel clientSocketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
        // 配置为非阻塞
        clientSocketChannel.configureBlocking(false);
        // 注册 Client Socket Channel 到 Selector
        clientSocketChannel.register(selector, SelectionKey.OP_READ, new ArrayList<String>());
    }

    /**
     * 读取数据
     *
     * @param channel 读取的channel
     * @return 数据buffer
     */
    private ByteBuffer read(SocketChannel channel) {
        // 注意，不考虑拆包的处理
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            int count = channel.read(buffer);
            if (count == -1) {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return buffer;
    }

    private String newString(ByteBuffer buffer) {
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        System.arraycopy(buffer.array(), buffer.position(), bytes, 0, buffer.remaining());
        return new String(bytes, UTF_8);
    }

    public static void main(String[] args) throws Exception {
        new NioServer();
    }
}
