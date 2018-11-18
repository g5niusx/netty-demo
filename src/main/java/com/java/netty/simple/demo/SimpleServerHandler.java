package com.java.netty.simple.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static com.java.netty.Constants.UTF_8;

/**
 * 服务端处理
 */
@Slf4j
public class SimpleServerHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 在不加管道的情况下，默认的对象都是ByteBuf
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("服务器接收到:{}", byteBuf.toString(UTF_8));
    }
}