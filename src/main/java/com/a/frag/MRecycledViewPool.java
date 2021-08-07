package com.a.frag;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MRecycledViewPool extends RecyclerView.RecycledViewPool {

    private static final String TAG = "MRecycledViewPool";

    public MRecycledViewPool() {
        super();
        Log.d(TAG, "MRecycledViewPool() called");
    }

    @Override
    public void clear() {
        super.clear();
        Log.d(TAG, "clear() called");
    }

    @Override
    public void setMaxRecycledViews(int viewType, int max) {
        super.setMaxRecycledViews(viewType, max);
        Log.d(TAG, "setMaxRecycledViews() called with: viewType = [" + viewType + "], max = [" + max + "]");
    }

    @Override
    public RecyclerView.ViewHolder getRecycledView(int viewType) {
        RecyclerView.ViewHolder viewHolder=super.getRecycledView(viewType);
        Log.d(TAG, "getRecycledView() called with: viewType = [" + viewType + "]returned: " +viewHolder );
        return viewHolder;

    }

    @Override
    public void putRecycledView(RecyclerView.ViewHolder scrap) {
        super.putRecycledView(scrap);
        Log.d(TAG, "putRecycledView() called with: scrap = [" + scrap + "]");
    }


}
