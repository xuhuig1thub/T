package com.a;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "com.a.MainActivity";

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        Log.d(TAG, "onPostCreate() called with: savedInstanceState = [" + savedInstanceState + "], persistentState = [" + persistentState + "]");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != savedInstanceState)
        {
            int IntTest = savedInstanceState.getInt("IntTest");
            String StrTest = savedInstanceState.getString("StrTest");
            Log.e(TAG, "onCreate get the savedInstanceState+IntTest="+IntTest+"+StrTest="+StrTest);
        }
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        ViewServer.get(this).addWindow(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if(null != savedInstanceState)
        {
            int IntTest = savedInstanceState.getInt("IntTest");
            String StrTest = savedInstanceState.getString("StrTest");
            Log.e(TAG, "onCreate get the savedInstanceState+IntTest="+IntTest+"+StrTest="+StrTest);
        }
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "], persistentState = [" + persistentState + "]");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() called");
    }

    @Override
    public  void onSaveInstanceState(Bundle savedInstanceState) {
        // Save away the original text, so we still have it if the activity
        // needs to be killed while paused.
        savedInstanceState.putInt("IntTest", 0);
        savedInstanceState.putString("StrTest", "savedInstanceState test");
        super.onSaveInstanceState(savedInstanceState);
        Log.e(TAG, "onSaveInstanceState");
    }

    @Override
    public   void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int IntTest = savedInstanceState.getInt("IntTest");
        String StrTest = savedInstanceState.getString("StrTest");
        Log.e(TAG, "onRestoreInstanceState+IntTest="+IntTest+"+StrTest="+StrTest);
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow() called");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow() called");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged() called with: hasFocus = [" + hasFocus + "]");
    }
}
