package com.java.oio.demo;


import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * @author g5niusx
 */
public class OIOTimeHandler implements Runnable {
    private Socket socket;

    OIOTimeHandler(Socket socket) {
        this.socket = socket;
    }

    @Override

    public void run() {

        try (
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        ) {
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                bufferedWriter.write(new Date().toString() + "\r\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

