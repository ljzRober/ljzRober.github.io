package com.ljz.plat.android.mwidget.data.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CurveData {
    @NonNull
    private final String code;
    @NonNull
    private List<Float> data = new ArrayList<>();
    private int count;

    public CurveData(@NonNull String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    @NonNull
    public List<Float> getData() {
        return data;
    }

    public void setData(@NonNull List<Float> data) {
        this.data = data;
    }

    public void addData(@NonNull Float data) {
        this.data.add(data);
    }
}
