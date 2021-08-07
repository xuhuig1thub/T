package com.a.jetpack;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class BuglyCrashHandler implements Thread.UncaughtExceptionHandler {


//    private final Object exceptionHandler;
    List<Activity> activities = new ArrayList<>();
    static BuglyCrashHandler instance;

    public BuglyCrashHandler(Application application) {
//        exceptionHandler = handler;
        registerActivityListener(application);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        clearAllActivity();
    }

    public void clearAllActivity() {
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
    }

    private void registerActivityListener(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    activities.add(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == activities && activities.isEmpty()) {
                        return;
                    }
                    if (activities.contains(activity)) {
                        /**
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        activities.remove(activity);
                    }
                }
            });
        }
    }
}
