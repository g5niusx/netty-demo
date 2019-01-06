package com.java.netty.idlestate;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 心跳客户端实例
 *
 * @author g5niusx
 */
public class IdleClient {

    private static final int    PORT = 9999;
    private static final String IP   = "127.0.0.1";


    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        Bootstrap         bootstrap         = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 当3秒没有进行读写的时候，就会触发心跳,心跳的处理类要放在第一个
                        ch.pipeline().addLast(new IdleStateHandler(0, 0, 3, TimeUnit.SECONDS));
                        ch.pipeline().addLast(new IdleClientHandler());
                    }
                })
                .connect(IP, PORT).channel().closeFuture().sync();

    }
}
