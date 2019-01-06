package com.java.netty.fixed;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * 客户端
 *
 * @author g5niusx
 */
public class SimpleClient {

    private static final int    PORT = 9999;
    private static final String IP   = "127.0.0.1";


    public static void main(String[] args) throws InterruptedException {
        // 客户端不需要监听端口，只需要一个线程池来发送消息
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        // 创建一个引导器
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                // 指定使用哪种channel来处理消息,分别有nio，oio等
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 定义消息的长度为1024
                        ch.pipeline().addLast(new FixedLengthFrameDecoder(1024));
                        ch.pipeline().addLast(new SimpleClientHandler());
                    }
                })
                .connect(IP, PORT).channel().closeFuture().sync();

    }
}
