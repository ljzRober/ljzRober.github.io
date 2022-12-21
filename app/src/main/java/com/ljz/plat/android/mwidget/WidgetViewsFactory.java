package com.ljz.plat.android.mwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.NonNull;

import com.ljz.plat.android.R;
import com.ljz.plat.android.mwidget.data.WidgetManager;
import com.ljz.plat.android.mwidget.data.model.CurveData;
import com.ljz.plat.android.mwidget.data.accession.CurveAccession;
import com.ljz.plat.android.mwidget.data.model.LargeWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
   private static final String url = "https://nativeapphq.hexin.cn/hexin";
   public static final String TAG = "WidgetViewsFactory";

   @NonNull
   private final Intent mIntent;
   @NonNull
   private final Context mContext;
   private WidgetManager mWidgetManager;
   int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
   public static int plus = 1;

   private WidgetViewsFactory(@NonNull Context context, @NonNull Intent intent) {
      this.mContext = context;
      this.mIntent = intent;
      Bundle extras = intent.getExtras();
      if (extras != null) {
         mAppWidgetId = extras.getInt(
                 AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
         mWidgetManager = WidgetManager.of(mAppWidgetId);
      }
   }

   public static WidgetViewsFactory of(@NonNull Context context, @NonNull Intent intent) {
      return new WidgetViewsFactory(context, intent);
   }

   @Override
   public void onCreate() {
      Log.d(TAG, "onCreate: ");
   }

   @Override
   public void onDataSetChanged() {
      Log.d(TAG, "onDataSetChanged: ");
      // do nothing
   }

   @Override
   public void onDestroy() {
      Log.d(TAG, "onDestroy: ");
   }

   @Override
   public int getCount() {
      List<LargeWidget> largeWidgets = mWidgetManager.getMergedObserver().getValue();
      if (largeWidgets == null) {
         return 0;
      } else {
         return largeWidgets.size();
      }
   }

   @Override
   public RemoteViews getViewAt(int position) {
      RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_item);
      List<LargeWidget> largeWidgets = mWidgetManager.getMergedObserver().getValue();
      if (largeWidgets != null) {
         LargeWidget largeWidget = largeWidgets.get(position);
         if (largeWidget != null) {
            String name = largeWidget.getMap().get("55");
            remoteViews.setTextViewText(R.id.list_text, String.valueOf(name));
            CurveData curveData = largeWidget.getCurveData();
            if (curveData != null) {
               CurveView curveView = new CurveView(mContext, mAppWidgetId);
               remoteViews.setImageViewBitmap(R.id.curve,
                       curveView.getCurvePic(largeWidget));
            }
         }
      }
      return remoteViews;
   }

   @Override
   public RemoteViews getLoadingView() {
      return null;
   }

   @Override
   public int getViewTypeCount() {
      return 1;
   }
   @Override
   public long getItemId(int position) {
      return position;
   }

   @Override
   public boolean hasStableIds() {
      return true;
   }
}
