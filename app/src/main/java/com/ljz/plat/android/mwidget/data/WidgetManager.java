package com.ljz.plat.android.mwidget.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.ljz.plat.android.mwidget.data.accession.CurveAccession;
import com.ljz.plat.android.mwidget.data.accession.DataAccession;
import com.ljz.plat.android.mwidget.data.accession.NumAccession;
import com.ljz.plat.android.mwidget.data.model.CurveData;
import com.ljz.plat.android.mwidget.data.model.LargeWidget;
import com.ljz.plat.android.mwidget.data.model.Num;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class WidgetManager {
    private final MergedObserver<List<LargeWidget>,
            Map<String, CurveData>,
            Map<String, Num>> mMergedObserver = new MergedObserver<>();
    @NonNull
    private CurveAccession mCurveAccession;
    @NonNull
    private DataAccession mDataAccession;
    @NonNull
    private NumAccession mNumAccession;
    private static final Map<Integer, WidgetManager> mMap = new HashMap<>();
    private CountDownLatch mCountDownLatch;

    private WidgetManager(int widgetId) {
        mCurveAccession = CurveAccession.of(widgetId);
        mDataAccession = DataAccession.of(widgetId);
        mNumAccession = NumAccession.of(widgetId);
        mMergedObserver.setObservers(mCurveAccession.getObserver(),
                mNumAccession.getObserver(),
                mDataAccession.getObserver());

        mCurveAccession.getObserver().observe(stringCurveDataMap -> mCountDownLatch.countDown());
        mNumAccession.getObserver().observe(stringNumMap -> mCountDownLatch.countDown());
        mDataAccession.getObserver().observe(largeWidgets -> mCountDownLatch.countDown());
    }

    public static WidgetManager of(int widgetId) {
        if (!mMap.containsKey(widgetId)) {
            mMap.put(widgetId, new WidgetManager(widgetId));
        }
        return mMap.get(widgetId);
    }

    public MergedObserver<List<LargeWidget>, Map<String, CurveData>, Map<String, Num>> getMergedObserver() {
        return mMergedObserver;
    }

    public void setTransformer(@NonNull Transformer<List<LargeWidget>,
            Map<String, CurveData>,
            Map<String, Num>> transformer) {
        mMergedObserver.setTransformer(transformer);
    }

    public WidgetManager setCountdown(@NonNull CountDownLatch countDownLatch) {
        this.mCountDownLatch = countDownLatch;
        return this;
    }

    public void request(@NonNull WidgetContext widgetContext) {
        mCurveAccession.AsyncData(widgetContext);
        mDataAccession.AsyncData(widgetContext);
        mNumAccession.AsyncData(widgetContext);
    }
}
