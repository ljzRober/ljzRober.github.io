package com.ljz.plat.android.mwidget.data.accession;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ljz.plat.android.mwidget.data.MyCallback;
import com.ljz.plat.android.mwidget.data.Observers;
import com.ljz.plat.android.mwidget.data.Requester;
import com.ljz.plat.android.mwidget.data.WidgetContext;
import com.ljz.plat.android.mwidget.data.model.CurveData;
import com.ljz.plat.android.mwidget.data.model.LargeWidget;
import com.ljz.plat.android.mwidget.data.model.StockInfo;
import com.ljz.plat.android.mwidget.data.result.DataParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAccession {
    public static final String TAG = "DataAccession";
    private final Observers<List<LargeWidget>> mObserver = new Observers<>();
    private static final Map<Integer, DataAccession> mMap = new HashMap<>();
    public static final String LINK = ";";

    public static DataAccession of(int widgetId) {
        if (!mMap.containsKey(widgetId)) {
            mMap.put(widgetId, new DataAccession());
        }
        return mMap.get(widgetId);
    }

    public Observers<List<LargeWidget>> getObserver() {
        return mObserver;
    }

    private Map<String, String> asParam(@NonNull List<StockInfo> stocks) {
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("method", "quote");
        hashMap.put("datetime", "0(0-0)");
        hashMap.put("datatype", "10,5,55,199112,264648,6");
        hashMap.put("code", codeOf(stocks));
        hashMap.put("app", "iostoday");
        hashMap.put("source", "hq");
        return hashMap;
    }

    @NonNull
    private String codeOf(@NonNull List<StockInfo> stocks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StockInfo stockInfo : stocks) {
            stringBuilder.append(stockInfo.getCode()).append(LINK);
        }
        return stocks.size() > 0 ?
                stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString() : "";
    }

    public void AsyncData(@NonNull WidgetContext widgetContext) {
        new Requester(widgetContext.getDataUrl())
                .addParams(asParam(widgetContext.getStocks()))
                .Method(Requester.Method.POST)
                .request(new MyCallback<String>() {
                    @Override
                    public void onResult(String result) {
                        try {
                            mObserver.setValue(new DataParser().parse(result));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onException(@NonNull Exception exception) {
                        Log.e(TAG, "onException: ", exception);
                    }
                });
    }
}
