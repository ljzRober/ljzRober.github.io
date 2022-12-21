package com.ljz.plat.android.mwidget.data.accession;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ljz.plat.android.mwidget.data.WidgetContext;
import com.ljz.plat.android.mwidget.data.model.StockInfo;
import com.ljz.plat.android.mwidget.data.result.CurveParser;
import com.ljz.plat.android.mwidget.data.model.CurveData;
import com.ljz.plat.android.mwidget.data.MyCallback;
import com.ljz.plat.android.mwidget.data.Observers;
import com.ljz.plat.android.mwidget.data.Requester;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurveAccession {
    public static final String TAG = "CurveAccession";
    public static final String LINK = ";";
    private final Observers<Map<String, CurveData>> mObserver = new Observers<>();
   private static final Map<Integer, CurveAccession> mMap = new HashMap<>();

   public static CurveAccession of(int widgetId) {
      if (!mMap.containsKey(widgetId)) {
         mMap.put(widgetId, new CurveAccession());
      }
      return mMap.get(widgetId);
   }

   public Observers<Map<String, CurveData>> getObserver() {
      return mObserver;
   }

   private Map<String, String> asParam(@NonNull List<StockInfo> stocks) {
      final HashMap<String, String> hashMap = new HashMap<>();
      hashMap.put("method", "quote");
      hashMap.put("datetime", "8192(0-0)");
      hashMap.put("datatype", "10,199112");
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
      new Requester(widgetContext.getCurveUrl())
            .addParams(asParam(widgetContext.getStocks()))
            .Method(Requester.Method.POST)
            .request(new MyCallback<String>() {
               @Override
               public void onResult(String result) {
                   try {
                       mObserver.setValue(new CurveParser().parse(result));
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
