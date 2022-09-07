package com.ljz.plat.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    WebView webView_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView_layout = findViewById(R.id.webView_layout);
        setWebView(webView_layout);
        initWebView(webView_layout);
    }

    private void setWebView(@NonNull WebView webView) {
        WebSettings webSettings =  webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setDefaultTextEncodingName("utf-8");
    }

    private void initWebView(@NonNull WebView webView) {
        webView.loadUrl("https://t.10jqka.com.cn/m/post/articleShare/articleshare.html?" +
                "pid=238904588&type=circle&client_userid=HMpet&share_hxapp=gsc&share_action=&back_source=hyperlink");
    }
}