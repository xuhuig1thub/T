package com.a.jetpack;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

//用来观察system表里飞行模式所在行是否发生变化 ， “行”内容观察者

/**
 * @author H
 */
public class SmsObserver extends ContentObserver {

    private static String TAG = "SmsObserver" ;

    private static int MSG_AIRPLANE = 1 ;

    private Context mContext;
    private Handler mHandler ;  //此Handler用来更新UI线程

    public SmsObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler ;
    }

    /**
     * 当所监听的Uri发生改变时，就会回调此方法
     *
     * @param selfChange 此值意义不大 一般情况下该回调值false
     */
    @Override
    public void onChange(boolean selfChange) {



        // 系统是否处于飞行模式下
//            int isAirplaneOpen = Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON);
//            Log.i(TAG, " isAirplaneOpen -----> " +isAirplaneOpen) ;
//            mHandler.obtainMessage(MSG_AIRPLANE,isAirplaneOpen).sendToTarget() ;

    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
//        super.onChange(selfChange, uri);
        Log.i(TAG, "-----the airplane mode has changed-----"+uri.getAuthority()+"-"+uri.getScheme()+"-"+uri.getHost()+"-"+uri.getPath()+"-"+uri.getQuery()+"-"+uri.getPort());
    }
}
