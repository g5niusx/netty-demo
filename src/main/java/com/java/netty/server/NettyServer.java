package com.java.netty.server;

import com.java.netty.decoder.DemoDecoder;
import com.java.netty.encoder.DemoEncoder;
import com.java.netty.server.handler.ServeerChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    private Integer port;

    public NettyServer(Integer port) {
        this.port = port;
    }

    public void start() {
        //1. 创建监听线程
        NioEventLoopGroup listenerNioEventLoopGroup = new NioEventLoopGroup(1);
        //2. 创建工作线程
        NioEventLoopGroup workNioEventLoopGroup = new NioEventLoopGroup(4);
        //3. 创建服务器端的引导程序
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //4. 指定服务端的处理流程
        serverBootstrap.group(listenerNioEventLoopGroup, workNioEventLoopGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new DemoDecoder());
                        ch.pipeline().addLast(new DemoEncoder());
                        ch.pipeline().addLast(new ServeerChannelHandler());
                    }
                });
        //5. 设置关闭方式
        try {
            ChannelFuture sync = serverBootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("关闭服务端异常", e);
        }
    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(9999);
        nettyServer.start();
    }

}
