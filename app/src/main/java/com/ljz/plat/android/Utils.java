package com.ljz.plat.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.stream.Stream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {
    private static final String TAG = "Utils";

    private static final Utils INSTANCE = new Utils();

    private Utils() {
    }

    public static Utils getInstance() {
        return INSTANCE;
    }

    public void clearFullScreenFlag(@NonNull Activity activity) {
        Window window = activity.getWindow();
        int flag = activity.getWindow().getAttributes().flags;
        if (((flag & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN)) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public void setFullScreenFlag(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        window.setAttributes(params);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void setLandScapeFullScreen(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(layoutParams);
        }
    }

    public void statusTranslucent(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        Toast.makeText(activity, String.valueOf(window.getDecorView().getWindowSystemUiVisibility()), Toast.LENGTH_SHORT).show();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.getDecorView().setSystemUiVisibility(View.INVISIBLE);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    public void quitStatusTranslucent(@NonNull Activity activity) {
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    public void setStatusTextMode(@NonNull Activity activity, boolean darkMode) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        window.getDecorView().setSystemUiVisibility(darkMode ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @TargetApi(28)
    public int getTopHeight(@NonNull Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        WindowInsets windowInsets = decorView.getRootWindowInsets();
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        int statusBarHeight = displayCutout.getSafeInsetTop();
        Log.d(TAG, "setLanScapeStatusBar: "+statusBarHeight);
        return statusBarHeight;
    }

    public void hideSoftKeyBord(@NonNull Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void requestHttp(@NonNull URL url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseHttpData(call, response);
            }
        });
    }

    private void parseHttpData(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {
            if (response.body() == null) {
                Log.d(TAG, "response.body() == null");
            } else {
                String data = response.body().string();
                Log.d(TAG, "response.body().String()" + data);
                Gson gson = new Gson();
                HttpModel httpModel = gson.fromJson(data, HttpModel.class);
                Log.d(TAG, "httpModel = " + httpModel.toString());
                if (httpModel.getData() == null) {
                    Log.d(TAG, "httpModel.getData() == null");
                }
            }
        } else {
            Log.d(TAG, "parseHttpData: error");
        }
    }

    static Stream<BigInteger> primes() {
        return Stream.iterate(BigInteger.valueOf(2), BigInteger::nextProbablePrime);
    }

    public void Mersenne() {
        primes().map(p -> BigInteger.valueOf(2).pow(p.intValue()).subtract(BigInteger.ONE))
                .filter(mersenne -> mersenne.isProbablePrime(50))
                .limit(20)
                .forEach(System.out::println);
    }
}
