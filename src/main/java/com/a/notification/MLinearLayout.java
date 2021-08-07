package com.a.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MLinearLayout extends LinearLayout {

    private static final String TAG = "MLinearLayout";

    public MLinearLayout(Context context) {
        super(context);
        Log.d(TAG, "MLinearLayout(Context context) called with: context = [" + context + "]");
//        LayoutInflater.from(context).inflate(R.layout.m_layout,this);
    }

    public MLinearLayout(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        Log.d(TAG, "MLinearLayout(Context context, AttributeSet attributeSet) called with: context = [" + context + "], attributeSet = [" + attributeSet + "]");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent() called with: ev = [" + ev + "]");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
        boolean b=true;
        Log.d(TAG, "onTouchEvent() returned: " + b);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent() called with: ev = [" + ev + "]");
        return super.onInterceptTouchEvent(ev);
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "onInterceptTouchEvent() returned: " + false);
//                return false;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "onInterceptTouchEvent() returned: " + true);
//                return true;
//        }
//        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate() called");
    }
}
