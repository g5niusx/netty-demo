package com.java.netty.custom.codec;

import com.java.netty.custom.protocol.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器
 *
 * @author g5niusx
 */
public class CustomEncoder extends MessageToByteEncoder<CustomProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
        // 1.写入长度
        out.writeInt(msg.getLength());
        // 2.写入消息字节
        out.writeBytes(msg.getMessage());
    }
}
