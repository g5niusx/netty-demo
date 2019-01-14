package com.java.netty.custom.protocol;

import lombok.Getter;

import java.io.Serializable;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 自定义消息协议
 * length|data
 *
 * @author g5niusx
 */
public class CustomProtocol implements Serializable {

    /**
     * 消息传输长度
     */
    @Getter
    private Integer length;
    /**
     * 具体的消息
     */
    @Getter
    private byte[]  message;

    public CustomProtocol(String message) {
        this.message = message.getBytes(UTF_8);
        this.length = this.message.length;
    }
}
