package com.a.jetpack;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.a.R;
import com.a.databinding.ActivityJetpackBinding;
import com.jeanboy.demo.jnitest.NdkTest;

public class TestActivity extends FragmentActivity {

    private static final String TAG = "TestActivity";
    TimerViewModel timerViewModel;
    ActivityJetpackBinding binding;
    Handler mHander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Snackbar.make(null,"sms/draft", Snackbar.LENGTH_SHORT).setAction("Action",new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Toast.makeText(TestActivity.this,R.string.action_settings,Toast.LENGTH_SHORT).show();
                }

            }).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_jetpack);
         binding= DataBindingUtil.setContentView(this, R.layout.activity_jetpack);
        binding.setLifecycleOwner(this);
        initWidgets();
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"),true,new SmsObserver(this,mHander));
    }

    private void initWidgets() {
        final TextView tv = (TextView) findViewById(R.id.tv_jet_pack1);
        final TextView tv2 = (TextView) findViewById(R.id.tv_jet_pack2);
        timerViewModel = new ViewModelProvider(TestActivity.this,
                new ViewModelProvider.NewInstanceFactory()).get(TimerViewModel.class);
        binding.setM(timerViewModel);
//        timerViewModel.setOnTimeChangeListener(new TimerViewModel.OnTimeChangeListener() {
//            @Override
//            public void onTimeChanged(final int second) {
//                //更新UI
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv.setText("TIME:" + second);
//                    }
//                });
//            }
//        });
        timerViewModel.getMutableLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer second) {
                tv.setText(Integer.toString(second));
                Log.i(TAG,"BBBBBBBBBBBBBBB");
                tv2.setText(NdkTest.getString());
                Toast.makeText(TestActivity.this, String.valueOf(NdkTest.doAdd(5, 12)), Toast.LENGTH_SHORT).show();
            }
        });
//        try {
//            Toast.makeText(this,Settings.Global.getInt(getContentResolver(),Settings.Global.AIRPLANE_MODE_ON),0).show();
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }
//        setAirPlaneMode(this,true);
    }

    private void setAirPlaneMode(Context context, boolean enable) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);
        } else {
            Settings.Global.putInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, enable ? 1 : 0);
        }
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", enable);
        context.sendBroadcast(intent);
    }
}
