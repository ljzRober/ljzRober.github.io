package com.ljz.plat.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        statusTranslucent(this);
        Utils.getInstance().setFullScreenFlag(this);
        setContentView(R.layout.activity_second);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(layoutParams);
        }

        RelativeLayout relativeLayout = findViewById(R.id.backGround);
        relativeLayout.setBackgroundColor(Color.GREEN);
    }

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        int height = intent.getIntExtra("height", 0);
        setLanScapeStatusBar(height);
        super.onStart();
    }

    private void setLanScapeStatusBar(int height) {
        MyRelativeLayout relativeLayout = new MyRelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(height,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setBackgroundColor(Color.RED);
        relativeLayout.setId(R.id.status_bar);
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        viewGroup.addView(relativeLayout);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
