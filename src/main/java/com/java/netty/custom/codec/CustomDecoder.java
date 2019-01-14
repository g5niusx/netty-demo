package com.java.netty.custom.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 自定义解码器
 *
 * @author g5niusx
 */
public class CustomDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        // 读取消息长度
        int length = in.readInt();
        // 如果可以读的数据小于协议中的数据，需要重置一下读的下标，重新读取
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        // 将读取的字节填充到空的数组中
        in.readBytes(bytes);
        // 将传输的字节数组转换为字符串传递给下一个入站类处理
        out.add(new String(bytes, UTF_8));
    }
}
