package com.java.netty.Idlestate;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 服务端处理
 */
@Slf4j
public class IdleServerHandler extends SimpleChannelInboundHandler<ByteBuf> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String s = msg.toString(UTF_8);
        // 定义ping字符串为保持心跳的消息
        if ("ping".equals(s)) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("ping", UTF_8));
            log.info("服务端接受到心跳消息为:{},心跳时间为:{}", s, new Date());
        } else {
            log.info("服务器接收到:{}", s);
        }
    }
}
