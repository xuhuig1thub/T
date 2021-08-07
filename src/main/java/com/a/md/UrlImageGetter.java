package com.a.md;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrlImageGetter implements Html.ImageGetter {

    private static final String TAG = "UrlImageGetter";

    Context mContext;
    public UrlImageGetter(Context context) {
        mContext = context;
    }

    @Override
    public Drawable getDrawable(String source) {
        Log.d(TAG, "getDrawable() called with: source = [" + source + "]");
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(source).build();
            Call call = client.newCall(request);
            Response response = call.execute();
            Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
            Drawable drawable = new BitmapDrawable(bitmap);
//            MyLog.i(drawable.getIntrinsicWidth()+"");
            DrawableUtils drawableUtils = new DrawableUtils(mContext);
            //调整图片大小
            drawable = drawableUtils.utils(drawable);
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

