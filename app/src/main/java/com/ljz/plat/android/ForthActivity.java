package com.ljz.plat.android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ForthActivity extends AppCompatActivity {

    public static final String TAG = "ForthActivity";
    Button mButton;
    Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_forth);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    private void initView() {
        mButton = findViewById(R.id.request_permission_with_start_activity);
        mButton1 = findViewById(R.id.request_permission_with_start_fragment);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionControl permissionControl = PermissionControl.of();
                permissionControl.requirePermission();
            }
        });
        mButton1.setOnClickListener(v -> {
            PermissionControl permissionControl = PermissionControl.of(this);
            permissionControl.requireFragmentPermission();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onPause() {
        super.onPause();
        if (!Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            this.startActivity(intent);
        } else {
            JacocoHelper.generateEcFile(true);
        }
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    private Context getContext() {
        return this;
    }
}