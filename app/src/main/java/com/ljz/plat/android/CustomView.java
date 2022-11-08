package com.ljz.plat.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.ljz.plat.android.mwidget.CurveView;

public class CustomView extends ImageView {

   public static final String TAG = "CustomView";
   private int mColor = Color.RED;
   private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
   CurveView curveView;

   public CustomView(Context context) {
      super(context);
      Log.d(TAG, "CustomView: 1个参数");
      initView();
   }

   public CustomView(Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
      Log.d(TAG, "CustomView: 2个参数");
      TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
      mColor = array.getColor(R.styleable.CustomView_circle_color, Color.RED);
      array.recycle();
      initView();
   }

   public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      Log.d(TAG, "CustomView: 3个参数");
      initView();
   }

   public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
      super(context, attrs, defStyleAttr, defStyleRes);
      Log.d(TAG, "CustomView: 4个参数");
      initView();
   }

   private void initView() {
      mPaint.setColor(mColor);
      curveView = new CurveView(getContext(), 1);
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      Log.d(TAG, "onMeasure: widthMeasureSpec = " + widthMeasureSpec + "heightMeasureSpec" + heightMeasureSpec);
   }

   @Override
   protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      super.onLayout(changed, left, top, right, bottom);
      Log.d(TAG, "onLayout: left = " + left + "top" + top + "changed" + changed);
   }

   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      Log.d(TAG, "onDraw: ");
//      final int paddingLeft = getPaddingLeft();
//      final int paddingRight = getPaddingRight();
//      final int paddingTop = getPaddingTop();
//      final int paddingBottom = getPaddingBottom();
//      int width = getWidth() - paddingLeft - paddingRight;
//      int height = getHeight() - paddingTop - paddingBottom;
//      int radius = Math.min(width, height) >> 1;
//      canvas.drawCircle(paddingLeft + width >> 1, paddingTop + height >> 1, radius, mPaint);

//      mPaint.setStyle(Paint.Style.STROKE);
//      mPaint.setAntiAlias(true);
//      mPaint.setStrokeWidth(2);
//      mPaint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
//      //绘制长度为4的实线后再绘制长度为4的空白区域，0位间隔
//      float y = getHeight() / 2;
//      Path path = new Path();
//      path.reset();
//      path.moveTo(0, y);
//      path.lineTo(getWidth(), y);
//      canvas.drawPath(path, mPaint);

      this.setImageURI(curveView.getCurvePic(getWidth(), getHeight()));
   }
}
