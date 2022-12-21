package com.ljz.plat.android;

import android.app.Application;
import android.util.Log;

import okhttp3.OkHttp;

public class MyApplication extends Application {

   public static final String TAG = "MyApplication";

   @Override
   public void onCreate() {
      super.onCreate();
      Log.d(TAG, "onCreate: ");
   }
}
