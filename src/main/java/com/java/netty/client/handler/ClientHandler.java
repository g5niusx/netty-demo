package com.java.netty.client.handler;

import com.java.netty.Constants;
import com.java.netty.message.DemoMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static java.nio.charset.StandardCharsets.UTF_8;


@ChannelHandler.Sharable
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<DemoMessage> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DemoMessage demoMessage = new DemoMessage();
        String s = "测试测试测试测试测试测试测试测试测试测" +
                "试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
                "试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
                "试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" +
                "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" +
                "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" +
                "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试";
        demoMessage.setData(s.getBytes(Constants.UTF_8));
        ctx.writeAndFlush(demoMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DemoMessage msg) throws Exception {
        String s = new String(msg.getData(), UTF_8);
        log.info("客户端接收到: {}", s);
    }
}
