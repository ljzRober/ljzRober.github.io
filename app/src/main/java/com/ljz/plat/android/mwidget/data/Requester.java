package com.ljz.plat.android.mwidget.data;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashMap;

import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Requester {
    public static final String TAG = "Requester";
    @NonNull
    private final String mUrl;
    @NonNull
    private Map<String, String> mMap = new HashMap<>();
    private Method mMethod;

    public Requester(@NonNull String url) {
        this.mUrl = url;
    }

    public Requester addParams(@NonNull Map<String, String> list) {
        mMap = list;
        return this;
    }

    public Requester Method(@NonNull Method method) {
        this.mMethod = method;
        return this;
    }

    public void request(@NonNull MyCallback<String> callback) {
        mMethod.newCall(this)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(TAG, "onResponse: " + e.toString());
                        callback.onException(e);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        callback.onResult(parseData(response));
                    }
                });
    }

    private String parseData(@NonNull Response response) {
        try {
            byte[] b = response.body().bytes();     //获取数据的bytes
            return new String(b, "GB2312");
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @NonNull
    private RequestBody getRequestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        mMap.forEach(builder::addFormDataPart);
        return builder.build();
    }

    @NonNull
    private FormBody getFormBody() {
        FormBody.Builder builder = new FormBody.Builder();
        mMap.forEach(builder::add);
        return builder.build();
    }

    public enum Method {
        GET {
            @Override
            Call newCall(@NonNull Requester requester) {
                Uri.Builder builder = Uri.parse(requester.mUrl).buildUpon();
                requester.mMap.forEach(builder::appendQueryParameter);
                Request request = new Request.Builder()
                        .url(builder.build().toString())
                        .get()
                        .build();
                return new OkHttpClient().newCall(request);
            }
        },
        POST {
            @Override
            Call newCall(@NonNull Requester requester) {
                RequestBody requestBody = requester.getFormBody();
                Request request = new Request.Builder()
                        .url(requester.mUrl)
                        .post(requestBody)
                        .build();
                return new OkHttpClient().newCall(request);
            }
        };

        abstract Call newCall(@NonNull Requester requester);
    }
}
