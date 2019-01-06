package com.java.netty.fixed;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static java.nio.charset.StandardCharsets.UTF_8;


@ChannelHandler.Sharable
@Slf4j
public class SimpleClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        StringBuilder message = new StringBuilder("测试");
        while (message.toString().getBytes(UTF_8).length < 1024) {
            message.append(" ");
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.toString(), UTF_8);
        ctx.writeAndFlush(byteBuf);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("客户端接收到: {}", msg.toString(UTF_8));
    }
}
