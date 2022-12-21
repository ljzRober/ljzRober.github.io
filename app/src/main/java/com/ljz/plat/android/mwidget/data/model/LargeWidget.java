package com.ljz.plat.android.mwidget.data.model;

import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class LargeWidget {
    public static final String newPrice = "10";
    public static final String lastClosePrice = "6";
    public static final String code = "5";
    public static final String name = "55";
    public static final String amount = "199112";
    public static final String percent = "264648";

    private Map<String, String> mMap = new HashMap<>();
    private CurveData mCurveData;

    public CurveData getCurveData() {
        return mCurveData;
    }

    public void setCurveData(CurveData curveData) {
        mCurveData = curveData;
    }

    public void addParam(@NonNull Pair<String, String> pair) {
        mMap.put(pair.first, pair.second);
    }

    public Map<String, String> getMap() {
        return mMap;
    }

    public String getCode() {
        return mMap.get(code);
    }

}
