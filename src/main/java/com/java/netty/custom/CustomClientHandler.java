package com.java.netty.custom;

import com.java.netty.custom.protocol.CustomProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


@ChannelHandler.Sharable
@Slf4j
public class CustomClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        CustomProtocol customProtocol = new CustomProtocol("hello world");
        ctx.writeAndFlush(customProtocol);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("客户端接收到: {}", msg);
    }
}
