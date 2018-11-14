package com.java.netty.server.handler;

import com.java.netty.message.DemoMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * @author g5niusx
 */
@Slf4j
@ChannelHandler.Sharable
public class ServeerChannelHandler extends SimpleChannelInboundHandler<DemoMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DemoMessage msg) throws Exception {
        String s = new String(msg.getData(), UTF_8);
        log.info("接收到消息: {}", s);
        DemoMessage message = new DemoMessage();
        message.setData("你好".getBytes(UTF_8));
        ctx.writeAndFlush(message);
    }


}
