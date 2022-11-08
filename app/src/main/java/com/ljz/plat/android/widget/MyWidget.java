package com.ljz.plat.android.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ljz.plat.android.R;

public class MyWidget extends AppWidgetProvider {
   String TAG = "MyWidget：" ;
   public static final String REFRESH_WIDGET = "com.oitsme.REFRESH_WIDGET";
   @Override
   public void onReceive(Context context, Intent intent) {
      super.onReceive(context, intent);
      Log.i(TAG ,"接受广播");

      String action = intent.getAction();
      AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
      if (action.equals(REFRESH_WIDGET)) {
         // 接受“bt_refresh”的点击事件的广播
         Toast.makeText(context, "刷新...", Toast.LENGTH_SHORT).show();
         final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
      }
   }

   /**
    * 第一个widget被添加调用
    * @param context
    */
   @Override
   public void onEnabled(Context context) {
      super.onEnabled(context);
      Log.i(TAG ,"widget  onEnabled 状态");
//      context.startService(new Intent(context, WidgetService.class));

   }

   /**
    * widget被添加 || 更新时调用
    * @param context
    */
   @Override
   public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
      super.onUpdate(context, appWidgetManager, appWidgetIds);
      Log.i(TAG ,"widget  onUpdate 状态");
//      context.startService(new Intent(context, WidgetService.class));

      for (int appWidgetId : appWidgetIds) {
         // 获取AppWidget对应的视图
         RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

         // 设置响应 “按钮(bt_refresh)” 的intent
         Intent btIntent = new Intent().setAction(REFRESH_WIDGET);
         btIntent.setClassName(context, MyWidget.class.getName());
         PendingIntent btPendingIntent = PendingIntent.getBroadcast(context,
                 0, btIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
         remoteViews.setOnClickPendingIntent(R.id.btn_refound, btPendingIntent);

         // 调用集合管理器对集合进行更新
         appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
      }
   }

   /**
    * 最后一个widget被删除时调用
    * @param context
    */
   @Override
   public void onDisabled(Context context) {
      super.onDisabled(context);
      Log.i(TAG ,"widget  onDisabled 状态");
//      context.stopService(new Intent(context, WidgetService.class));
   }

   /**
    * widget被删除时调用
    * @param context
    * @param appWidgetIds
    */
   @Override
   public void onDeleted(Context context, int[] appWidgetIds) {
      super.onDeleted(context, appWidgetIds);
      Log.i(TAG ,"widget  onDeleted 状态");

   }
}

