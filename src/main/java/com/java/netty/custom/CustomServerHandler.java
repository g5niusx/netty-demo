package com.java.netty.custom;

import com.java.netty.custom.protocol.CustomProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端处理
 */
@Slf4j
public class CustomServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new CustomProtocol("hello client"));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("服务器接收到:{}", msg);
    }
}
