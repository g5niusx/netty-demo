package com.java.oio.demo;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞的时间服务端
 *
 * @author g5niusx
 */
@Slf4j
public class OIOTimeServer {
    static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        log.info("oio服务端已经启动，监听端口为{}", PORT);
        while (true) {
            final Socket accept = serverSocket.accept();
            new Thread(new OIOTimeHandler(accept)).start();
        }
    }
}

