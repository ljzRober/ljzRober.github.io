package com.ljz.plat.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ljz.plat.android.mwidget.data.model.CurveData;
import com.ljz.plat.android.mwidget.data.MyCallback;
import com.ljz.plat.android.mwidget.data.Requester;

import java.util.HashMap;
import java.util.Map;

public class WidgetActivity extends AppCompatActivity {

    private static final String url = "https://nativeapphq.hexin.cn/hexin";
    private static HashMap<String, String> hashmap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        initView();
    }

    private void initView() {
        Button testHttp = findViewById(R.id.testHttp);
        testHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashmap.put("method", "quote");
                hashmap.put("datetime", "8192(0-0)");
                hashmap.put("datatype", "10,199112");
                hashmap.put("code", "300033");
                hashmap.put("app", "iostoday");
                hashmap.put("source", "hq");

                new Requester(url).addParams(hashmap).request(new MyCallback<String>() {
                    @Override
                    public void onResult(String result) {

                    }

                    @Override
                    public void onException(@NonNull Exception exception) {

                    }
                });
            }
        });
    }
}