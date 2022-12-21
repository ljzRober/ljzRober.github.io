package com.ljz.plat.android.mwidget.data;

import androidx.annotation.NonNull;

import com.ljz.plat.android.mwidget.data.model.StockInfo;

import java.util.List;

public class WidgetContext {
   String mCurveUrl = "https://nativeapphq.hexin.cn/hexin";
   String mNumUrl = "https://eq.10jqka.com.cn/interface/common_config";
   String mDataUrl = "https://nativeapphq.hexin.cn/hexin";
   List<StockInfo> mStocks;

   private WidgetContext(@NonNull List<StockInfo> stocks) {
      this.mStocks = stocks;
   }

   public static WidgetContext of(@NonNull List<StockInfo> stocks) {
      return new WidgetContext(stocks);
   }

   public List<StockInfo> getStocks() {
      return mStocks;
   }

   public String getCurveUrl() {
      return mCurveUrl;
   }

   public void setCurveUrl(String curveUrl) {
      mCurveUrl = curveUrl;
   }

   public String getNumUrl() {
      return mNumUrl;
   }

   public void setNumUrl(String numUrl) {
      mNumUrl = numUrl;
   }

   public String getDataUrl() {
      return mDataUrl;
   }

   public void setDataUrl(String dataUrl) {
      mDataUrl = dataUrl;
   }
}
