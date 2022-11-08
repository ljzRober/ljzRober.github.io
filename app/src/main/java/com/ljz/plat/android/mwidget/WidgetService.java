package com.ljz.plat.android.mwidget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    public WidgetService() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return WidgetViewsFactory.of(getApplicationContext(), intent);
    }
}