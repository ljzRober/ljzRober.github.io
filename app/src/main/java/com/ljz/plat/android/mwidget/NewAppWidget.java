package com.ljz.plat.android.mwidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.ljz.plat.android.ForthActivity;
import com.ljz.plat.android.R;
import com.ljz.plat.android.mwidget.data.Transformer;
import com.ljz.plat.android.mwidget.data.WidgetContext;
import com.ljz.plat.android.mwidget.data.WidgetManager;
import com.ljz.plat.android.mwidget.data.model.CurveData;
import com.ljz.plat.android.mwidget.data.accession.CurveAccession;
import com.ljz.plat.android.mwidget.data.model.LargeWidget;
import com.ljz.plat.android.mwidget.data.model.Num;
import com.ljz.plat.android.mwidget.data.model.StockInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link NewAppWidgetConfigureActivity NewAppWidgetConfigureActivity}
 */
public class NewAppWidget extends AppWidgetProvider {

    public static final String ANDROID_APPWIDGET_ACTION_APPWIDGET_CONFIGURE = "android.appwidget.action.APPWIDGET_CONFIGURE";
    public static final String REFRESH = "android.appwidget.action.APPWIDGET_REFRESH";
    private static final String url = "https://nativeapphq.hexin.cn/hexin";
    private static final Observer mObserver = new Observer() {
        @Override
        public void onChanged(Object o) {
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listView);
        }
    };
    public static final String TAG = "NewAppWidget";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (Objects.equals(intent.getAction(), REFRESH)) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int appWidgetId = extras.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                requestData(appWidgetId, appWidgetManager);
                WidgetViewsFactory.plus *= 2;
            }
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = NewAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

//        requestData(appWidgetId, appWidgetManager);
        activeSetting(context, appWidgetId, views);
        activeRefresh(context, appWidgetId, views);
        updateList(context, appWidgetId, views);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void requestData(int appWidgetId,@NonNull AppWidgetManager appWidgetManager) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CountDownLatch countDownLatch = new CountDownLatch(3);
                WidgetManager widgetManager = WidgetManager.of(appWidgetId);
                widgetManager.setTransformer(new Transformer<List<LargeWidget>, Map<String, CurveData>, Map<String, Num>>() {
                    @Override
                    public List<LargeWidget> transform(Map<String, CurveData> first, Map<String, Num> second, List<LargeWidget> main) {
                        main.forEach(new Consumer<LargeWidget>() {
                            @Override
                            public void accept(LargeWidget largeWidget) {
                                String code = largeWidget.getCode();
                                CurveData curveData = first.get(code);
                                curveData.setCount(second.get(code).getCount());
                                largeWidget.setCurveData(curveData);
                            }
                        });
                        return main;
                    }
                });
                StockInfo ths = new StockInfo("300033", "33");
                StockInfo gl = new StockInfo("300034", "33");
                List<StockInfo> stockInfoList = Arrays.asList(ths, gl);
                widgetManager.setCountdown(countDownLatch)
                        .request(WidgetContext.of(stockInfoList));
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                widgetManager.getMergedObserver().observe(new Observer<List<LargeWidget>>() {
                    @Override
                    public void onChanged(List<LargeWidget> largeWidgets) {
                        Log.d(TAG, "onChanged: ");
                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listView);
                    }
                });
            }
        }).start();
    }

    private static void
    updateList(@NonNull Context context, int appWidgetId, @NonNull RemoteViews views) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.listView, intent);
    }

    private static void activeSetting(@NonNull Context context, int appWidgetId, @NonNull RemoteViews views) {
        Intent intent1 = new Intent(context, NewAppWidgetConfigureActivity.class);
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.setting, pendingIntent);
    }

    private static void activeRefresh(@NonNull Context context, int appWidgetId, @NonNull RemoteViews views) {
        Intent intent1 = new Intent(context, NewAppWidget.class);
        intent1.setAction(REFRESH);
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.refresh, pendingIntent);
    }

    private void setAlarm(@NonNull Context context, int appWidgetId) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(REFRESH);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        int requestCode = 0;
        PendingIntent pendIntent = PendingIntent.getBroadcast(context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        // 5秒后发送广播，然后每个10秒重复发广播。广播都是直接发到AlarmReceiver的
        long triggerAtTime = SystemClock.elapsedRealtime() + 10 * 1000;
        int interval = 10 * 1000;
        alarmMgr.setRepeating(AlarmManager.RTC, triggerAtTime, interval, pendIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
//            setAlarm(context, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            NewAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, new Intent(context, ForthActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.background, pendingIntent);   //点击跳转
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}