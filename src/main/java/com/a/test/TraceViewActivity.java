package com.a.test;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.a.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TraceViewActivity extends AppCompatActivity {

    private static final String TAG = "TraceViewActivity";

    Button b1;

    Button b2;

    Button b3;

     Button b4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        getWindow().setContentView(R.layout.activity_traceview);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "], persistentState = [" + persistentState + "]");
        setContentView(R.layout.activity_traceview);
        b1= (Button) findViewById(R.id.method_1);
        b2= (Button) findViewById(R.id.method_2);
        b3= (Button) findViewById(R.id.method_3);
        b4= (Button) findViewById(R.id.method_4);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        setContentView(R.layout.activity_traceview);
    }


  public void m1(View v){
        for(int i=0;i<10000;i++){
            method5(i);
        }
    }

    private void method5(int i) {
        System.out.println(i);
    }


    public void m2(View v){
        SystemClock.sleep(2000);
    }


    public void m3(View v){
        int sum=0;
        for(int i=0;i<5000;i++){
            sum+=i;
        }
        System.out.println("sum="+sum);
    }

    /**###Jason 2021/8/7 21:58 */
    public void m4(View v){
            try {
                URL url = new URL("https://www.baidu.com");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String lines = null;
                StringBuffer sb = new StringBuffer();
                while ((lines = reader.readLine()) != null) {
                    sb.append(lines);
                }
            } catch (Exception e) {
                e.printStackTrace();
        }
        Toast.makeText(this,"www.baidu.com",Toast.LENGTH_SHORT).show();
    }
}
