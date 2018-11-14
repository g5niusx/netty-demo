package com.java.netty.server.handler;

import com.java.netty.message.DemoMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author g5niusx
 */
@Slf4j
@ChannelHandler.Sharable
public class ServeerChannelHandler extends SimpleChannelInboundHandler<DemoMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DemoMessage msg) throws Exception {
        log.info(msg.toString());
    }


}
