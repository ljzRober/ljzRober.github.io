package com.ljz.plat.android.mwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.NonNull;

import com.ljz.plat.android.R;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
   @NonNull
   private final Intent mIntent;
   @NonNull
   private final Context mContext;
   private final int count = 20;
   @NonNull
   private List<String> mWidgetItems = new ArrayList<>();
   int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

   private WidgetViewsFactory(@NonNull Context context, @NonNull Intent intent) {
      this.mContext = context;
      this.mIntent = intent;
      Bundle extras = intent.getExtras();
      if (extras != null) {
         mAppWidgetId = extras.getInt(
                 AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
      }
   }

   public static WidgetViewsFactory of(@NonNull Context context, @NonNull Intent intent) {
      return new WidgetViewsFactory(context, intent);
   }

   @Override
   public void onCreate() {
      for (int i = 0; i < count; i++) {
         mWidgetItems.add(i + "!");
      }
   }

   @Override
   public void onDataSetChanged() {

   }

   @Override
   public void onDestroy() {

   }

   @Override
   public int getCount() {
      return count;
   }

   @Override
   public RemoteViews getViewAt(int position) {
      RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_item);
      remoteViews.setTextViewText(R.id.list_text, mWidgetItems.get(position));

      CurveView curveView = new CurveView(mContext, mAppWidgetId);
      remoteViews.setImageViewBitmap(R.id.curve, curveView.getCurvePic());
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
