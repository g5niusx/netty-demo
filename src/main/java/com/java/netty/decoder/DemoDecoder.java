package com.java.netty.decoder;

import com.java.netty.kit.ProtostuffKit;
import com.java.netty.message.DemoMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.java.netty.Constants.UTF_8;

/**
 * 解码器，将消息流入到netty的时候对消息进行解码
 *
 * @author g5niusx
 */
@Slf4j
public class DemoDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = DemoMessage.HEAD.getBytes(UTF_8).length;
        // 如果可读的字节数小于标志位则直接返回
        if (in.readableBytes() < length) {
            return;
        }
        // 获取到标志头
        byte[] headBytes = new byte[length];
        in.readBytes(headBytes);
        log.info("标志头为: {}", new String(headBytes, UTF_8));
        // 获取到版本号
        byte[] versionBytes = new byte[DemoMessage.VERSION.getBytes(UTF_8).length];
        in.readBytes(versionBytes);
        log.info("版本号为: {}", new String(versionBytes, UTF_8));
        int dataLength = in.readInt();
        log.info("数据长度为: {}", dataLength);
        byte[] bytes = new byte[dataLength];
        in.readBytes(bytes);
        DemoMessage deserialize = ProtostuffKit.deserialize(bytes, DemoMessage.class);
        log.info(new String(bytes, UTF_8));
        out.add(deserialize);
    }
}
