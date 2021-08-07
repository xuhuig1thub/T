package com.a.jetpack;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class TimerViewModel extends ViewModel {

    private static final String TAG = "TimerViewModel";

    private Timer timer;
    // Create a LiveData with a String
    private MutableLiveData<Integer> mutableLiveData;

    public  int currentSecond=0;

    @Override
    protected void onCleared() {
        super.onCleared();
        //清理资源
        timer.cancel();
    }

    public MutableLiveData<Integer> getMutableLiveData() {
        if(mutableLiveData==null){
            mutableLiveData=new MutableLiveData<Integer>();
        }
        return mutableLiveData;
    }

    public void setMutableLiveData(int i){
        if(mutableLiveData==null){
            mutableLiveData=new MutableLiveData<Integer>();
        }
        mutableLiveData.setValue(i);
    }

    public void onClick(View v) {
      startTiming();
    }
    //开始计时
    public void startTiming() {
        if (timer == null) {
            currentSecond = 0;
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    currentSecond++;
                    mutableLiveData.postValue(currentSecond);
                    if (onTimeChangeListener != null) {
                        onTimeChangeListener.onTimeChanged(currentSecond);
                    }
                }
            };
            timer.schedule(timerTask, 1000, 1000);
        }
    }

    private OnTimeChangeListener onTimeChangeListener;

    public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
        this.onTimeChangeListener = onTimeChangeListener;
    }

    public interface OnTimeChangeListener {
        void onTimeChanged(int second);
    }

    public String getCurrentSecond() {
        if(mutableLiveData==null){
            mutableLiveData=new MutableLiveData<Integer>();
            mutableLiveData.setValue(0);
        }
        Log.d(TAG, "getCurrentSecond: "+mutableLiveData.getValue());
        return String.valueOf(mutableLiveData.getValue());
    }

    public void setCurrentSecond(int currentSecond) {
        this.currentSecond = currentSecond;
    }
}
