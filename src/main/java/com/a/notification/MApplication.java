package com.a.notification;

import android.app.Application;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MApplication extends Application {

    private static final String TAG = "MApplication";

    public MApplication(){

        Log.d("MApplication() called",android.os.Process.myUid()+"-----"+android.os.Process.myPid()+"-----"+android.os.Process.myTid()+ "-----"+android.os.Process.myUserHandle()+"-----"+Thread.currentThread().getId());
    }
    private int aidlInt=33;

    public int getAidlInt() {
        return aidlInt;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        String processName = getProcessName();
//        Log.d(TAG, "Hidden method ----->UserHandle.getUserId(android.os.Process.myUid()):" + UserHandle.getUserId(android.system.Os.getuid()));
        try {
            Method m=UserHandle.class.getMethod("getUserId",int.class);
             int userId=(Integer) m.invoke(null,android.os.Process.myUid());
            Log.d(TAG, "userId:" + userId);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
//判断进程名，保证只有主进程运行
        if (!TextUtils.isEmpty(processName) &&processName.equals("notification.test")) {
            //在这里进行主进程初始化逻辑操作
            Log.d(TAG, "onCreate() called----->thread="+Thread.currentThread().getId()+"-----pid="+android.os.Process.myPid());
        }


    }

    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            Log.d(TAG, "getProcessName() returned: " + processName);
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
