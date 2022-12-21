package com.ljz.plat.android.mwidget.data.accession;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ljz.plat.android.mwidget.data.MyCallback;
import com.ljz.plat.android.mwidget.data.Observers;
import com.ljz.plat.android.mwidget.data.Requester;
import com.ljz.plat.android.mwidget.data.WidgetContext;
import com.ljz.plat.android.mwidget.data.model.Num;
import com.ljz.plat.android.mwidget.data.model.StockInfo;
import com.ljz.plat.android.mwidget.data.result.NumParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumAccession {
    public static final String TAG = "NumAccession";
    public static final String LINK = "_";
    public static final String ITEM = ",";
    private final Observers<Map<String, Num>> mObserver = new Observers<>();
    private static final Map<Integer, NumAccession> mMap = new HashMap<>();

    public static NumAccession of(int widgetId) {
        if (!mMap.containsKey(widgetId)) {
            mMap.put(widgetId, new NumAccession());
        }
        return mMap.get(widgetId);
    }

    public Observers<Map<String, Num>> getObserver() {
        return mObserver;
    }

    private Map<String, String> asParam(@NonNull List<StockInfo> stocks) {
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("business", "tradeConfig");
        hashMap.put("codelist", codeOf(stocks));
        return hashMap;
    }

    @NonNull
    private String codeOf(@NonNull List<StockInfo> stocks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StockInfo stockInfo : stocks) {
            stringBuilder.append(stockInfo.getCode()).append(LINK)
                    .append(stockInfo.getMarket()).append(ITEM);
        }
        return stocks.size() > 0 ?
                stringBuilder.deleteCharAt(stringBuilder.length() - 1 ).toString() : "";
    }

    public void AsyncData(@NonNull WidgetContext widgetContext) {
        new Requester(widgetContext.getNumUrl())
                .addParams(asParam(widgetContext.getStocks()))
                .Method(Requester.Method.GET)
                .request(new MyCallback<String>() {
                    @Override
                    public void onResult(String result) {
                        try {
                            mObserver.setValue(new NumParser().parse(result));
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
