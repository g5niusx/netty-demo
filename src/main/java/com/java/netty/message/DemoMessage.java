package com.java.netty.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 报文协议格式
 * 0xxxxxx0|1.0.0|length|data
 *
 * @author g5niusx
 */
@Getter
@Setter
@Data
public class DemoMessage implements Serializable {

    public static final String HEAD    = "0xff";
    public static final String VERSION = "1.0.0";

    /**
     * 标志头
     */
    private String  head    = HEAD;
    /**
     * 数据
     */
    private byte[]  data;
    /**
     * 长度
     */
    private Integer length;
    /**
     * 协议版本
     */
    private String  version = VERSION;

}
