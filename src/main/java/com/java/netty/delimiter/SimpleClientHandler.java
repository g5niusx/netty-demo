package com.java.netty.delimiter;

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
        for (int i = 0; i < 50; i++) {
            String message = "测试\t";
            // 由于没有使用任何的管道，所以向channel中写入数据的时候，要使用ByteBuf,否则会造成消息无法发送
            ByteBuf byteBuf = Unpooled.copiedBuffer(message, UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("客户端接收到: {}", msg.toString(UTF_8));
    }
}
