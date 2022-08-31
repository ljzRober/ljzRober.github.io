package com.ljz.plat.android;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "backClick";

    RelativeLayout rootView;
    Button button;
    Button button1;
    FrameLayout content;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    CustomView mCustomView;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "MainActivity, onKeyDown: ");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        statusTranslucent(this);
//        setFullScreenFlag(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: requestCode = "+requestCode+"permissions= "+ Arrays.toString(permissions) +"grantResults= "+ Arrays.toString(grantResults));
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
        Utils.getInstance().clearFullScreenFlag(this);
        MyRelativeLayout relativeLayout = new MyRelativeLayout(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight());
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setBackgroundColor(Color.RED);
        relativeLayout.setId(R.id.status_bar);
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                Log.d(TAG, "handleOnBackPressed: ");
                remove();
            }
        };
        onBackPressedCallback.setEnabled(true);
        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
        content.addView(relativeLayout);
//        relativeLayout.setFocusable(true);
        setKeyListener(rootView);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        Log.d(TAG, "screen orientation: " + this.getRequestedOrientation());
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        Log.d(TAG, "onMultiWindowModeChanged: "+ isInMultiWindowMode);
    }

    @Override
    public boolean isInMultiWindowMode() {
        return super.isInMultiWindowMode();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: "+newConfig.orientation);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    private void initView() {
        rootView = findViewById(R.id.rootView);
        button = findViewById(R.id.changeOrientation);
        button1 = findViewById(R.id.jump_land);
        button2 = findViewById(R.id.jump_LinerLayout);
        button3 = findViewById(R.id.show_dialog);
        button4 = findViewById(R.id.show_normal_dialog);
        button5 = findViewById(R.id.request_httpData);
        button6 = findViewById(R.id.jump_ForthActivity);
        content = this.findViewById(android.R.id.content);
        mCustomView = findViewById(R.id.customView);

        rootView.setBackgroundColor(Color.TRANSPARENT);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("height", getStatusBarHeight());
                intent.setClass(getContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) getContext();
                if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    setLandScapeFullScreen();
                } else {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ThirdActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    PermissionDialog permissionDialog = new PermissionDialog();
                    permissionDialog.showNow(getSupportFragmentManager(), TAG);
                }
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this.getContext());
                dialog.setContentView(R.layout.permission_dialog);
                dialog.show();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL url = null;
                try {
                    url = new URL(getContext().getString(R.string.http_url));
                    Utils.getInstance().requestHttp(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        button6.setOnClickListener(v -> {
//            CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayout);
//            View view = LayoutInflater.from(getApplication().getBaseContext()).inflate(R.layout.permission_dialog, null);
//            Snackbar snackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_SHORT);
//            snackbar.setAnchorView(view);
//            snackbar.show();

            //注册全局监听
            PermissionControl.register(getActivity().getApplicationContext());
            Intent intent = new Intent();
            intent.setClass(getContext(), ForthActivity.class);
            startActivity(intent);
        });
    }

    private Activity getActivity() {
        return this;
    }

    private Context getContext() {
        return this;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static void statusTranslucent(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private void setKeyListener(@NonNull View view) {
        Log.d(TAG, "setKeyListener: ");
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setFocusable(true);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d(TAG, "onKey: keyCode={}"+keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Toast.makeText(getContext(), "keyDown", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    /**
     * @see TransparentActivity#test()
     * {@link com.ljz.plat.android.TransparentActivity#test()} dsadaf
     */
    private void test(String str){}
}
