package com.ljz.plat.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    public static final String TAG = "ThirdActivity";
    private static final int WHITE = -0xFFFFFF;
    TextView textView_time;
    TextView textView_ExceptionStr;
    TextView textView_valuesName;
    TextView textView_usefulHeight;
    TextView textView_javaTest;
    private static final String VALUES_NAME = "value name = ";
    private static final String HEIGHT = "useful height = ";
    private static final String DECORVIEW_HEIGHT = "decorview height = ";
    private static final String APPLICATION_HEIGHT = "application height = ";
    private static final String TEXTVIEW_WIDTH = "textview width = ";
    private static final String CHANGE_LINE = "\n";
    private static final String APPCOMPAT_ACTIVITY_IS_FRAGMENT_ACTIVITY = "AppCompatActivity IS FRAGMENT_ACTIVITY = ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
//        this.getWindow().setStatusBarColor(Color.WHITE);
//        Utils.getInstance().setStatusTextMode(this, true);
//        Utils.getInstance().statusTranslucent(this);
//        this.getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView_time = findViewById(R.id.time);
        textView_time.setText(String.valueOf(System.currentTimeMillis()));

        textView_ExceptionStr = findViewById(R.id.ExceptionStr);
        textView_ExceptionStr.setText(Log.getStackTraceString(getException()));

        textView_valuesName = findViewById(R.id.values_Name);
        String value = VALUES_NAME + getResources().getString(R.string.values_home);
        textView_valuesName.setText(value);

        StringBuilder stringBuilder = new StringBuilder();

        textView_usefulHeight = findViewById(R.id.useful_height);
        String value1 = HEIGHT + getResources().getDisplayMetrics().heightPixels;

        String value2 = DECORVIEW_HEIGHT + getWindow().getDecorView().getMeasuredHeight();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        String value3 = APPLICATION_HEIGHT + displayMetrics.heightPixels;

        String value4 = TEXTVIEW_WIDTH + textView_usefulHeight.getWidth();

        stringBuilder.append(value1).append(CHANGE_LINE);
        stringBuilder.append(value2).append(CHANGE_LINE);
        stringBuilder.append(value3).append(CHANGE_LINE);
        stringBuilder.append(value4).append(CHANGE_LINE);

        textView_usefulHeight.setText(stringBuilder);

        textView_javaTest = findViewById(R.id.java_test);
        boolean childInstance = this instanceof FragmentActivity;
        textView_javaTest.setText(APPCOMPAT_ACTIVITY_IS_FRAGMENT_ACTIVITY + childInstance);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "screen orientation: " + this.getRequestedOrientation());
    }

    /**
     * <p>this is a test javadoc {@literal |r| < 1}</p>
     * <p>this is a test javadoc |r| < 1</p>
     * @return
     */
    private Exception getException() {
        return new IllegalArgumentException("there is a Exception !!!");
    }
}