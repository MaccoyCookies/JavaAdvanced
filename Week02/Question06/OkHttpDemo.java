package com.maccoy.advanced.nio;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpDemo {

    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static void main(String[] args) {
        String response = get("http://127.0.0.1:8808/test");
        System.out.println("response = " + response);
    }

    private static String get(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
