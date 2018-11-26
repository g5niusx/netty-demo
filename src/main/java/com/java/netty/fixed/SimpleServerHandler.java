package com.java.netty.fixed;

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
public class SimpleServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        String  message = "你好，客户端\t";
        ByteBuf byteBuf = Unpooled.copiedBuffer(message, UTF_8);
        ctx.writeAndFlush(byteBuf);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("服务器接收到:{}", msg.toString(UTF_8) + "test");
    }
}
