package com.java.netty.client.handler;

import com.java.netty.Constants;
import com.java.netty.message.DemoMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<DemoMessage> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DemoMessage demoMessage = new DemoMessage();
        demoMessage.setData("测试".getBytes(Constants.UTF_8));
        ctx.writeAndFlush(demoMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DemoMessage msg) throws Exception {

    }
}
