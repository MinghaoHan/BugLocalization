package com.nobug.backend.Optimize;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;

import static jdk.nashorn.internal.objects.Global.print;

public class Bee {
    private final OkHttpClient httpClient = new OkHttpClient();

    public static void main(String[] args) throws Exception {

        Bee obj = new Bee();

//        System.out.println("Testing 1 - Send Http GET request");
//        obj.sendGet();

        System.out.println("Testing 2 - Send Http POST request");
        obj.getBee("I experienced a crash when opening the settings menu");

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

    private String sendPost(String sentence) throws Exception {
        if(sentence==null)
            sentence="I experienced a crash when opening the settings menu";

        // form parameters
        RequestBody formBody = new FormBody.Builder()
                .add("text", sentence)
                .build();

        Request request = new Request.Builder()
                .url("http://bugreportchecker.ngrok.io/api")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            String responseContent = response.body().string();
            System.out.println(responseContent);
            return responseContent;
        }

    }

    public String getBee(String sentence) throws Exception {
        String tmp = sendPost(sentence);
        JSONObject bug_report = (JSONObject) JSONObject.parseObject(tmp).get("bug_report");
        JSONObject res = (JSONObject) bug_report.get("0");
        String result = res.getJSONArray("labels").toString();
        System.out.println(result);
        return result;
    }
}
