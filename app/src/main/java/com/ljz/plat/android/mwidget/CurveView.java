package com.ljz.plat.android.mwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.net.Uri;
import android.util.Pair;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class CurveView {
    @NonNull
    AppWidgetManager mAppWidgetManager;
    @NonNull
    Context mContext;
    int mWidgetId;

    public CurveView(@NonNull Context context, int widgetId) {
        mAppWidgetManager = AppWidgetManager.getInstance(context);
        mContext = context;
        mWidgetId = widgetId;
    }

    public Uri getCurvePic(int width, int height) {
        Bitmap bitmap = drawCurve(mContext, width, height);
        return Uri.parse(saveBitmapToPic(mContext,"customview.png", bitmap));
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

    public Bitmap getCurvePic() {
        Pair<Integer, Integer> pair = new Pair<>(dp2px(mContext,200), dp2px(mContext, 40));
        return drawCurve(mContext, pair.first, pair.second);
    }

    private Bitmap drawCurve(@NonNull Context context, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.parseColor("#FFFF0000"));
        Canvas canvas = new Canvas(bitmap);

        Path path = createPath(width, height);
        drawBaseLine(canvas, width, height);
        drawBrokenLine(canvas, path);
        drawShadow(canvas, path, width, height);

        return bitmap;
    }

    private Path createPath(int width, int height) {
        Path path = new Path();
        path.moveTo(0, height / 2);
        path.lineTo(width / 4, 0);
        path.lineTo(width / 2, height / 2);
        path.lineTo(width * 3 / 4, height);
        path.lineTo(width, height / 2);
        return path;
    }

    private void drawBrokenLine(@NonNull Canvas canvas, @NonNull Path path) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawPath(path, paint);
    }

    private void drawShadow(@NonNull Canvas canvas, @NonNull Path path, int width, int height) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#009900"));
        Path sPath = new Path();
        sPath.addPath(path);
        sPath.lineTo(width, height);
        sPath.lineTo(0, height);
        paint.setShader(createShader(height));
        canvas.drawPath(sPath, paint);
    }

    private Shader createShader(int height) {
        int color = Color.parseColor("#009900");
        int transparent = Color.parseColor("#00FFFFFF");
        return new LinearGradient(0f , 0f,
                        0f, 0.9f * height, color, transparent, Shader.TileMode.CLAMP);
    }

    private void drawBaseLine(@NonNull Canvas canvas, int width, int height) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setColor(Color.parseColor("#4d7aad"));
        paint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
        //绘制长度为4的实线后再绘制长度为4的空白区域，0位间隔
        Path path = new Path();
        path.reset();
        path.moveTo(0, height / 2);
        path.lineTo(width, height / 2);
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
