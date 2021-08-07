package com.a.notification;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.a.R;

public class CustomButton extends android.support.v7.widget.AppCompatButton {

    private static final String TAG = "CustomButton";
    
    public CustomButton(Context context) {
        super(context);
        Log.d(TAG, "CustomButton(Context context) called with: context = [" + context + "]");
    }

     public CustomButton(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
         Log.d(TAG, "CustomButton(Context context, AttributeSet attributeSet) called with: context = [" + context + "], attributeSet = [" + attributeSet + "]"+"----"+attributeSet.getAttributeCount());
         if(attributeSet.getAttributeCount()==10){
           Log.d(TAG, attributeSet.getIdAttribute()==null?"null":"not null"+"");
                Log.d(TAG, "attributeSet.getAttributeFloatValue('btn','btn_degree',0f):----->" + attributeSet.getAttributeFloatValue("http://schemas.android.com/apk/res-auto","btn_degree",0f));
                Log.d(TAG, attributeSet.getAttributeValue(1));
                Log.d(TAG, attributeSet.getAttributeValue(2));
                Log.d(TAG, attributeSet.getAttributeValue(3));
                Log.d(TAG, attributeSet.getAttributeValue(4));
                Log.d(TAG, attributeSet.getAttributeValue(5));
                Log.d(TAG, attributeSet.getAttributeValue(6));
                Log.d(TAG, attributeSet.getAttributeValue(7));
                Log.d(TAG, attributeSet.getAttributeValue(8));
                Log.d(TAG, attributeSet.getAttributeValue(9));
                Log.d(TAG, attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android","text"));

             TypedArray typedArray=context.obtainStyledAttributes(attributeSet,R.styleable.CustomBtn);
                int color=typedArray.getColor(R.styleable.CustomBtn_item_background,0XFFFFFFFF);
                setBackgroundColor(color);
             Log.d(TAG, typedArray.toString());
             typedArray.recycle();
         }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        Log.d(TAG, "getMeasuredWidth():" + getMeasuredWidth());
        switch(widthMode){
            case MeasureSpec.AT_MOST:
                Log.d(TAG, "MeasureSpec.AT_MOST:MeasureSpec.getSize(widthMeasureSpec):" + MeasureSpec.getSize(widthMeasureSpec));
                setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),getMeasuredHeight()+10);
                break;
            case MeasureSpec.EXACTLY:
                Log.d(TAG, "MeasureSpec.EXACTLY:MeasureSpec.getSize(widthMeasureSpec):" + MeasureSpec.getSize(widthMeasureSpec));break;
            case MeasureSpec.UNSPECIFIED:
                Log.d(TAG, "MeasureSpec.UNSPECIFIED:MeasureSpec.getSize(widthMeasureSpec):" + MeasureSpec.getSize(widthMeasureSpec));break;
        }
    }

    @Override
    public void layout(int left, int top, int right, int bottom) {
        super.layout(left, top, right, bottom);
        Log.d(TAG, "layout() called with: left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
    }

    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent() called with: event = [" + event + "]");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
        boolean b=super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent() returned: " +b );
        return b;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate() called");
    }
}
