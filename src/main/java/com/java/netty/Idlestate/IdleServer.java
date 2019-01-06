package com.java.netty.Idlestate;

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
public class IdleServer {

    private static final int PORT = 9999;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup  = new NioEventLoopGroup();
        NioEventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap   serverBootstrap    = new ServerBootstrap();
        ChannelFuture sync = serverBootstrap.group(nioEventLoopGroup, workEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new IdleServerHandler());
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