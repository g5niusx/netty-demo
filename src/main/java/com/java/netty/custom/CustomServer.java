package com.java.netty.custom;

import com.java.netty.custom.codec.CustomDecoder;
import com.java.netty.custom.codec.CustomEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端
 */
@Slf4j
public class CustomServer {

    private static final int PORT = 9999;

    public static void main(String[] args) throws InterruptedException {
        // 创建监听线程，这个线程池会用来监听连接到客户端的连接，
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        // 创建工作线程，用来处理具体的请求
        NioEventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
        // 创建服务端的引导
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ChannelFuture sync = serverBootstrap.group(nioEventLoopGroup, workEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        // 增加自定义解码器
                        ch.pipeline().addLast(new CustomDecoder());
                        // 增加自定义编码器
                        ch.pipeline().addLast(new CustomEncoder());
                        ch.pipeline().addLast(new CustomServerHandler());
                    }
                })
                .bind(PORT).addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("端口{}绑定成功", PORT);
                    }
                }).sync();
        sync.channel().closeFuture().sync();
    }
}