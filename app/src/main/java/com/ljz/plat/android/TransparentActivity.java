package com.ljz.plat.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class TransparentActivity extends AppCompatActivity {

    public static final String TAG = "TransparentActivity";
    private final PermissionDialog permissionDialog = new PermissionDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionDialog.showNow(this.getSupportFragmentManager(), TAG);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(this, "recieve permission result", Toast.LENGTH_SHORT).show();
        permissionDialog.dismissAllowingStateLoss();
        finish();
    }

    private void test(){
    }
}