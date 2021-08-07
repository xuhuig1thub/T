package com.a.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @class description 远程服务供客户端调用
 */
public class RemoteService extends Service {

    private static final String TAG = "RemoteService";

    public RemoteService(){
        Log.d(TAG,"RemoteService() called"+android.os.Process.myUid()+"-----"+android.os.Process.myPid()+"-----"+android.os.Process.myTid()+ "-----"+android.os.Process.myUserHandle()+"-----"+android.os.Process.myUserHandle());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MainActivity.processFlag:" + NotificationTestActivity.processFlag);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            int i=0;
            @Override
            public void run() {
                while(true) {
                    Log.d(TAG, "run: " + (++i)+"----->Thread="+Thread.currentThread().getId());
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Log.d(TAG, "onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");
        return START_STICKY;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        return new IBinderImpl(this);
    }

    public  int getInt(){
        return ((MApplication)getApplicationContext()).getAidlInt();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind() called with: intent = [" + intent + "]");
        return super.onUnbind(intent);
    }
}
