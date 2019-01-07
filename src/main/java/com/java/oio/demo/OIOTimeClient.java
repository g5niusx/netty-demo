package com.java.oio.demo;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * 时间客户端
 *
 * @author g5niusx
 */
@Slf4j
public class OIOTimeClient {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("127.0.0.1", OIOTimeServer.PORT);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        ) {
            /*由于服务端读取的时候使用了BufferedReader,默认会忽略LF标志,所以手动增加LF标志*/
            bufferedWriter.write("test\r\n");
            bufferedWriter.flush();
            String readLine = bufferedReader.readLine();
            log.info("接收到服务端返回:{}", readLine);
        } catch (Exception e) {
            log.error("客户端异常", e);
        }
    }
}

