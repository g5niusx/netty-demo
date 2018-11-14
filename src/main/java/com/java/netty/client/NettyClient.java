package com.java.netty.client;

import com.java.netty.client.handler.ClientHandler;
import com.java.netty.decoder.DemoDecoder;
import com.java.netty.encoder.DemoEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {

    private String  ip = "127.0.0.1";
    private Integer port;

    public NettyClient(Integer port) {
        this.port = port;
    }

    public NettyClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public void send() {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(1);
        Bootstrap         bootstrap         = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new DemoEncoder())
                                .addLast(new DemoDecoder())
                                .addLast(new ClientHandler());
                    }
                });
        try {
            ChannelFuture connect = bootstrap.connect(ip, port).sync();
            connect.addListener(future -> {
                if (future.isSuccess()) {
                    log.info(String.format("连接 %s:%d 成功", ip, port));
                } else {
                    log.error(String.format("连接 %s:%d 失败", ip, port));
                }
            });
            connect.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("关闭客户端异常", e);
        } finally {
            nioEventLoopGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient(9999);
        nettyClient.send();
    }
}
