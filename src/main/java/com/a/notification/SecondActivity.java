package com.a.notification;

import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a.R;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "NotificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
//        Toast.makeText(this, getIntent().getStringExtra("xixi"), Toast.LENGTH_SHORT).show();

        TagsLayout imageViewGroup = (TagsLayout) getWindow().getDecorView().findViewById(R.id.tagslayout);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String[] string={"从我写代码那天起，我就没有打算写代码","从我写代码那天起","我就没有打算写代码","没打算","写代码"};
        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText(string[i]);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(android.R.color.background_dark);
            textView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.d(TAG,"onClick----->thread="+Thread.currentThread().getId());
                    try {
                        Thread.currentThread().sleep(5100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            imageViewGroup.addView(textView, lp);
            try {
                Thread.currentThread().sleep(600);
                Log.d("NotificationActivity", "Thread.currentThread().getId():" + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
