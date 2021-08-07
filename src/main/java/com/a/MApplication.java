package com.a;

import android.app.Application;
import android.os.StrictMode;

public class MApplication extends Application {

    @Override
    public void onCreate() {
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork()   // or .detectAll() for all detectable problems
//                .penaltyDialog() //弹出违规提示对话框
//                .penaltyLog() //在Logcat 中打印违规异常信息
//                .penaltyFlashScreen() //API等级11
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects() //API等级11
//                .penaltyLog()
//                .penaltyDeath()
//                .build());
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        super.onCreate();
    }
}
