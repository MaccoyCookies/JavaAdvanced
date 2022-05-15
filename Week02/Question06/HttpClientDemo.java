package com.maccoy.advanced.nio;

import sun.net.www.http.HttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClientDemo {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8808/test");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8"));
            // bufferedWriter.write(param);
            // bufferedWriter.flush();
            // bufferedWriter.close();
            if (httpURLConnection.getResponseCode() == 200) {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String tempStr = null;
                    String str = "";
                    while ((tempStr = bufferedReader.readLine()) != null) {
                        str += tempStr;
                    }
                    System.out.println(str);
                } finally {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
