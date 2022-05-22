package com.maccoy.advanced.gateway;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackEndServer {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(16);

    private static final int port = 8002;

    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(port);
            while (true) {
                Socket accept = socket.accept();
                executorService.execute(() -> {
                    service(accept);
                });
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static void service(Socket accept) {
        // printRequest(accept);
        handleResponse(accept);
    }

    // private static void printRequest(Socket accept) {
    //     BufferedReader bufferedReader = null;
    //     try {
    //         InputStream inputStream = accept.getInputStream();
    //         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //         int offset = -1;
    //         byte[] buffer = new byte[1024];
    //         while ((offset = inputStream.read(buffer)) != -1) {
    //             byteArrayOutputStream.write(buffer, 0, offset);
    //         }
    //         inputStream.close();
    //         System.out.println(byteArrayOutputStream);
    //     } catch (IOException ioException) {
    //         ioException.printStackTrace();
    //     } finally {
    //         try {
    //             // if (inputStream != null) {
    //             //     inputStream.close();
    //             // }
    //             // if (inputStreamReader != null) {
    //             //     inputStreamReader.close();
    //             // }
    //             if (bufferedReader != null) {
    //                 bufferedReader.close();
    //             }
    //         } catch (IOException ioException) {
    //             ioException.printStackTrace();
    //         }
    //     }
    // }

    private static void handleResponse(Socket accept) {
        try {
            PrintWriter printWriter = new PrintWriter(accept.getOutputStream());
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello, gateway " + port + ", " + System.currentTimeMillis();
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.println(body);
            printWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
