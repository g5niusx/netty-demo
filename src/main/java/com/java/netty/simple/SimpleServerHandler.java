package com.java.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 服务端处理
 */
@Slf4j
public class SimpleServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        String  message = "你好，客户端";
        ByteBuf byteBuf = Unpooled.copiedBuffer(message, UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("服务器接收到:{}", msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ip:[{}]的用户已经连接",ctx.channel().remoteAddress());
    }
}
