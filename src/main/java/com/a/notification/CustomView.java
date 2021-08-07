package com.a.notification;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomView extends View implements View.OnClickListener {

    private static final String TAG = "CustomView";

    // 定义画笔
    private Paint mPaint;
    // 用于获取文字的宽和高
    private Rect mRect;
    // 计数值，每点击一次本控件，其值增加1
    private int mCount=0;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化画笔、Rect
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();
        // 本控件的点击事件
        setOnClickListener(this);
        Log.d(TAG, "CustomView() called with: context = [" + context + "], attrs = [" + attrs + "]");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure()1 called with: widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG, "onMeasure--widthMode=" + widthMode+"-----widthSize="
                +widthSize+"----heightMode="+heightMode+"-----"+"heightSize="+heightSize);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                //精确值模式，当控件的layout_width和layout_height属性指定为具体数值或match_parent时。
                Log.w(TAG,"widthMode="+"MeasureSpec.EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                //最大值模式，当空间的宽高设置为wrap_content时。
                Log.w(TAG,"widthMode="+"MeasureSpec.AT_MOST");
                break;
            case MeasureSpec.UNSPECIFIED:
                //未指定模式，View想多大就多大，通常在绘制自定义View时才会用。
                Log.w(TAG,"widthMode="+"MeasureSpec.UNSPECIFIED");
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                //精确值模式，当控件的layout_width和layout_height属性指定为具体数值或match_parent时。
                Log.w(TAG,"heightMode="+"MeasureSpec.EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                //最大值模式，当空间的宽高设置为wrap_content时。
                Log.w(TAG,"heightMode="+"MeasureSpec.AT_MOST");
                break;
            case MeasureSpec.UNSPECIFIED:
                //未指定模式，View想多大就多大，通常在绘制自定义View时才会用。
                Log.w(TAG,"heightMode="+"MeasureSpec.UNSPECIFIED");
                break;
        }
        //取最小边为控件的宽高的最小值
        int minWidth=widthSize>heightSize?heightSize:widthSize;
//        setMeasuredDimension(minWidth,minWidth);
        setMeasuredDimension(200,200);
        Log.d(TAG, "onMeasure()2 called with: widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        Log.d(TAG, "layout() 1called with: l = [" + l + "], t = [" + t + "], r = [" + r + "], b = [" + b + "]");
        super.layout(l,t,r-100,b);
//        super.layout(1, t+200, 1440-1, 2960-321);
        Log.d(TAG, "layout() 2 1called with: l = [" + l + "], t = [" + t + "], r = [" + r + "], b = [" + b + "]");

    }


//    protected boolean setFrame(int left, int top, int right, int bottom) {
//        return super.setFrame(left+300, top, right, bottom);
//    }


    //    protected boolean setFrame(int left, int top, int right, int bottom) {
//        Log.d(TAG, "setFrame() called with: left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
//
//
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout()1 called with: changed = [" + changed + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
        super.onLayout(changed, 660, top, right, bottom);
        Log.d(TAG, "onLayout()2 called with: changed = [" + changed + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw()1 called with: canvas = [" + canvas + "]");
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        // 绘制一个填充色为蓝色的矩形
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(50);
        String text = String.valueOf(mCount);
        // 获取文字的宽和高
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        float textWidth = mRect.width();
        float textHeight = mRect.height();

        // 绘制字符串
        canvas.drawText("点了我"+text+"次", getWidth() / 2 - textWidth / 2, getHeight() / 2
                + textHeight / 2, mPaint);

        Log.d(TAG, "onDraw()1 called with: canvas = [" + canvas + "]");
    }

    @Override
    public void onClick(View view) {
        mCount++;
        invalidate();

        getContext().startActivity(new Intent().setComponent(new ComponentName("com.u.notificationtest","com.u.notificationtest.NotificationActivity")));
    }
}
