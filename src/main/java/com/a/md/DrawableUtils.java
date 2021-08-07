package com.a.md;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public class DrawableUtils {
    Context mContext;
    public DrawableUtils(Context context){
        mContext = context;
    }
    public Drawable utils(Drawable drawable){
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        int draWidth = drawable.getIntrinsicWidth();
        int draHeight = drawable.getIntrinsicHeight();

        int resWidth = draWidth, resHeight = draHeight;

        if(draWidth < width && draHeight < height){
            resWidth = (int) (draWidth * 2.5);
            resHeight = (int) (draHeight * 2.5);
        }else if(draWidth > width || draHeight > height){
            int value = draWidth / width;
            resWidth = draWidth / value;
            resHeight = draHeight / value;
        }
        drawable.setBounds(0, 0, resWidth, resHeight);
        return drawable;
    }
}

