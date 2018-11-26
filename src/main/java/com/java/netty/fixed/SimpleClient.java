package com.java.netty.fixed;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

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
                        // 设置来制表符为特殊的分隔符
                        ByteBuf byteBuf = Unpooled.copiedBuffer("\t".getBytes(UTF_8));
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
                        ch.pipeline().addLast(new SimpleClientHandler());
                    }
                })
                .connect(IP, PORT).channel().closeFuture().sync();

    }
}
