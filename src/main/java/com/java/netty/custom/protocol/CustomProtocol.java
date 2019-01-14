package com.java.netty.custom.protocol;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

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

    public static void main(String[] args) {
        CustomProtocol hello_world = new CustomProtocol("hello world");
        System.out.println(Arrays.toString(hello_world.IntToByte(hello_world.length)));
        System.out.println(Arrays.toString(hello_world.getMessage()));
    }


    public byte[] IntToByte(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((num >> 24) & 0xff);
        bytes[1] = (byte) ((num >> 16) & 0xff);
        bytes[2] = (byte) ((num >> 8) & 0xff);
        bytes[3] = (byte) (num & 0xff);
        return bytes;

    }
}
