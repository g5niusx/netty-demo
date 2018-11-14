package com.java.netty.encoder;

import com.java.netty.kit.ProtostuffKit;
import com.java.netty.message.DemoMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import static com.java.netty.Constants.UTF_8;

/**
 * 编码器，将消息流出给客户端的时候进行编码
 *
 * @author g5niusx
 */
@Slf4j
public class DemoEncoder extends MessageToByteEncoder<DemoMessage> {
    protected void encode(ChannelHandlerContext ctx, DemoMessage msg, ByteBuf out) throws Exception {
        // 写入标志头
        out.writeBytes(msg.getHead().getBytes(UTF_8));
        log.info("写入标志头: {}", msg.getHead().getBytes(UTF_8).length);
        // 写入版本号
        out.writeBytes(msg.getVersion().getBytes(UTF_8));
        log.info("写入版本号: {}", msg.getVersion().getBytes(UTF_8).length);
        // 写入长度
        byte[] serializer = ProtostuffKit.serializer(msg);
        out.writeInt(serializer.length);
        log.info("写入的长度为:{}", serializer.length);
        // 写入数据
        out.writeBytes(serializer);
    }
}
