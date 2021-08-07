package com.a.frag;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

public class MRecyclerView extends RecyclerView {

    private static final String TAG = "MRv";

    public MRecyclerView(Context context) {
        super(context);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "MRv() called with: context = [" + context + "], attrs = [" + attrs + "]");
    }



    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        Log.d(TAG, "onScreenStateChanged() called with: screenState = [" + screenState + "]");
    }
}
