package com.java.netty.idlestate;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import static java.nio.charset.StandardCharsets.UTF_8;


@ChannelHandler.Sharable
@Slf4j
public class IdleClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("test", UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String s = msg.toString(UTF_8);
        if ("ping".equals(s)) {
            log.info("客户端接收到服务端心跳：{}", s);
        } else {
            log.info("客户端接收到: {}", s);

        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 如果触发的事件为心跳事件
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleState = (IdleStateEvent) evt;
            switch (idleState.state()) {
                case WRITER_IDLE:
                    break;
                case READER_IDLE:
                    break;
                case ALL_IDLE:
                    ByteBuf ping = Unpooled.copiedBuffer("ping", UTF_8);
                    ctx.writeAndFlush(ping);
                    break;
            }

        }
    }
}
