package com.ljz.plat.android.mwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.net.Uri;
import android.util.Pair;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.ljz.plat.android.R;
import com.ljz.plat.android.mwidget.data.model.CurveData;
import com.ljz.plat.android.mwidget.data.model.LargeWidget;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class CurveView {
    @NonNull
    AppWidgetManager mAppWidgetManager;
    @NonNull
    Context mContext;
    int mWidgetId;
    private int mColor;

    public CurveView(@NonNull Context context) {
        this.mContext = context;
    }

    public CurveView(@NonNull Context context, int widgetId) {
        mAppWidgetManager = AppWidgetManager.getInstance(context);
        mContext = context;
        mWidgetId = widgetId;
    }

//    public Uri getCurvePic() {
//        Pair<Integer, Integer> pair = new Pair<>(dp2px(mContext,200), dp2px(mContext, 40));
//        Bitmap bitmap = drawCurve(mContext, pair.first, pair.second);
//        String path = saveBitmapToPic(mContext,"customview.png", bitmap);
//        bitmap.recycle();
//        File newFile = new File(path);
//        Uri imageUri = CustomViewFileProvider.getUriForFile(mContext, CustomViewFileProvider.AUTHORITIES, newFile);
//        return imageUri;
//    }

    public Bitmap getCurvePic(int width, int height, @NonNull LargeWidget data) {
        return drawCurve(width, height, data);
    }

    public Bitmap getCurvePic(@NonNull LargeWidget data) {
        Pair<Integer, Integer> pair = new Pair<>(dp2px(mContext,200), dp2px(mContext, 40));
        return drawCurve(pair.first, pair.second, data);
    }

    private Bitmap drawCurve(int width,
                             int height,
                             @NonNull LargeWidget largeWidget) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        float lastClosePrice  = Float.parseFloat(largeWidget.getMap().get("6"));
        CurveData curve = largeWidget.getCurveData();
        mColor = chooseColor(curve, lastClosePrice);

        float max = Collections.max(curve.getData());
        float min = Collections.min(curve.getData());
        float realMax = Float.max(max, lastClosePrice);
        float realMin = Float.min(min, lastClosePrice);
        float scaleX = (float) width / curve.getCount();
        float scaleY = (float) height / (realMax - realMin);

        float positionY = (lastClosePrice - realMin) * -scaleY;
        positionY = positionY + canvas.getHeight();
        drawBaseLine(canvas, positionY);
        Path path = createPath(scaleX, scaleY, realMax, curve.getData());
        drawBrokenLine(canvas, path, mColor);
        drawShadow(canvas, path, curve.getData().size() * scaleX);

        return bitmap;
    }

    @ColorInt
    private int chooseColor(@NonNull CurveData curveData, float lastClosePrice) {
        return Float.compare(lastClosePrice, curveData.getData().get(curveData.getData().size() - 1)) < 0
            ? mContext.getResources().getColor(R.color.up, null) : ContextCompat.getColor(mContext, R.color.down);
    }

    private Path createPath(float scaleX, float scaleY, float max, @NonNull List<Float> data) {
        Path path = new Path();
        path.moveTo(0, data.get(0));
        for (int i = 1;i < data.size(); i++) {
            path.lineTo(i, data.get(i));
        }
        return transPath(scaleX, scaleY, max, path);
    }

    private Path transPath(float scaleX, float scaleY, float max, @NonNull Path path) {
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, -scaleY);
        matrix.postTranslate(0, max * scaleY);
        Path path1 = new Path();
        path.transform(matrix, path1);
        return path1;
    }

    private void drawBrokenLine(@NonNull Canvas canvas, @NonNull Path path, @ColorInt int color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(color);
        canvas.drawPath(path, paint);
    }

    private void drawShadow(@NonNull Canvas canvas, @NonNull Path path, float positionX) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mColor);
        Path sPath = new Path();
        sPath.addPath(path);
        sPath.lineTo(positionX, canvas.getHeight());
        sPath.lineTo(0, canvas.getHeight());
        paint.setShader(createShader(canvas.getHeight()));
        canvas.drawPath(sPath, paint);
    }

    private Shader createShader(int height) {
        int transparent = Color.parseColor("#00FFFFFF");
        return new LinearGradient(0f , 0f,
                        0f, 0.9f * height, mColor, transparent, Shader.TileMode.CLAMP);
    }

    private void drawBaseLine(@NonNull Canvas canvas, float height) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setColor(Color.parseColor("#4d7aad"));
        paint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
        //绘制长度为4的实线后再绘制长度为4的空白区域，0位间隔
        Path path = new Path();
        path.reset();
        path.moveTo(0, height);
        path.lineTo(canvas.getWidth(), height);
        canvas.drawPath(path, paint);
    }

    private String saveBitmapToPic(Context context,@NonNull String name,@NonNull Bitmap b) {
        context.deleteFile(name);
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(name, android.content.Context.MODE_PRIVATE);
            //fos = new FileOutputStream(screenShotFile,Context.MODE_WORLD_READABLE);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                return context.getFilesDir() + "/" + name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Pair<Integer, Integer> getWidgetsSize(@NonNull Context context, int widgetId) {
        boolean isPortrait = context.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT;
        int width = getWidgetWidth(isPortrait, widgetId);
        int height = getWidgetHeight(isPortrait, widgetId);
        int widthInPx = dp2px(context, width);
        int heightInPx = dp2px(context, height);
        return new Pair<>(widthInPx, heightInPx);
    }

    private int getWidgetWidth(boolean isPortrait, int widgetId) {
        if (isPortrait) {
            return getWidgetSizeInDp(widgetId, AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        } else {
            return getWidgetSizeInDp(widgetId, AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        }
    }

    private int getWidgetHeight(boolean isPortrait, int widgetId) {
        if (isPortrait) {
            return getWidgetSizeInDp(widgetId, AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        } else {
            return getWidgetSizeInDp(widgetId, AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        }
    }

    private int getWidgetSizeInDp(int widgetId,@NonNull String key) {
        return mAppWidgetManager.getAppWidgetOptions(widgetId).getInt(key, 0);
    }

    private int dp2px(@NonNull Context context, int value) {
        return (int) (value * context.getResources().getDisplayMetrics().density);
    }
}
