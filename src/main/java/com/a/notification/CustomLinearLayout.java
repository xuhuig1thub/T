package com.a.notification;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout {

    private static final String TAG = "CustomLinearLayout";

    public CustomLinearLayout(Context context) {
        super(context);
        Log.d(TAG, "CustomLinearLayout() called with: context = [" + context + "]");
//        LayoutInflater.from(context).inflate(R.layout.custom_layout,this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent() called with: ev = [" + ev + "]");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent() called with: ev = [" + ev + "]");
        return super.onInterceptTouchEvent(ev);
    }
}
