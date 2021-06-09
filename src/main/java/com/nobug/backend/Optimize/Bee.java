package com.nobug.backend.Optimize;

import okhttp3.*;

import java.io.IOException;

public class Bee {
    private final OkHttpClient httpClient = new OkHttpClient();

    public static void main(String[] args) throws Exception {

        Bee obj = new Bee();

//        System.out.println("Testing 1 - Send Http GET request");
//        obj.sendGet();

        System.out.println("Testing 2 - Send Http POST request");
        obj.sendPost();

    }

    private void sendGet() throws Exception {

        Request request = new Request.Builder()
                .url("https://www.google.com/search?q=mkyong")
                .addHeader("custom-key", "mkyong")  // add request headers
                .addHeader("User-Agent", "OkHttp Bot")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }

    }

    private String sendPost() throws Exception {

        // form parameters
        RequestBody formBody = new FormBody.Builder()
                .add("text", "I experienced a crash when opening the settings menu")
                .build();

        Request request = new Request.Builder()
                .url("http://bugreportchecker.ngrok.io/api")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
            return response.body().string();
        }

    }


}
