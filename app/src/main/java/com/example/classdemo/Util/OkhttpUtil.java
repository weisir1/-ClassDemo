package com.example.classdemo.Util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpUtil {
    public static void send(String url, Handler handler, int requestCode){
         Message message ;
         message = new Message();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                switch (requestCode) {
                    case 0:
                        message.arg1 = requestCode;
                        message.obj = response.body().string();
                        handler.sendMessage(message);
                        break;
                }
            }
        });
    }
}
