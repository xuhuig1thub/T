package com.a.notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.a.R;

import java.util.List;

public class NotificationTestActivity extends AppCompatActivity  implements View.OnClickListener{



    private static final String TAG = "MainActivity";

    public static boolean processFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CustomLinearLayout cll=new CustomLinearLayout(this);
//        LayoutInflater.from(this).inflate(R.layout.activity_main,cll);
//        setContentView(cll);
        DisplayMetrics dm=getResources().getDisplayMetrics();
        Log.d(TAG, dm.toString());
        Log.d(TAG, "dm.density:" + dm.density+"dm.densityDpi:"
                +dm.densityDpi+"dm.scaledDensity"+dm.scaledDensity+
                "dm.widthPixels"+dm.widthPixels+"dm.xdpi"+dm.xdpi);
        setContentView(R.layout.activity_notification_test);
        initWidgets();
        processFlag=true;
        startService(new Intent(getApplicationContext(), RemoteService.class));
        startService(new Intent(getApplicationContext(), RemoteService.class));
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "] Thread----->"+Thread.currentThread().getId());
    }

    private void initWidgets() {
    }

    public void amKillProcess(String process) {
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        Log.d("proc", "" + runningProcesses.size());
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningProcesses) {
            Log.d(TAG, " runningAppProcessInfo.processName="+runningAppProcessInfo.processName);
            if (runningAppProcessInfo.processName.equals(process)) {
                Log.d("proc", runningAppProcessInfo.processName+"-----"+runningAppProcessInfo.uid);
                Process.sendSignal(Process.myPid(), Process.SIGNAL_KILL);
            }
        }
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick() called with: v = [" + v + "]");
//        amKillProcess("com.u.notificationtest:mys");
        amKillProcess("notification.test");
//        android.os.Process.killProcess(Process.myPid());
        finish();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder mBuilder = new Notification.Builder(this);
        Intent intent = new Intent();
        ComponentName cName = new ComponentName("com.a.notification", "com.a.notification.SecondActivity");
        intent.setComponent(cName);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
//        mBuilder.setContentIntent(pi);
        mBuilder.setFullScreenIntent(pi,true);
        switch (v.getId()) {
            case R.id.send_notice:
                Notification notification = mBuilder
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                //        .setVibrate(new long[]{0, 1000, 1000, 1000})
                //        .setLights(Color.GREEN, 1000, 1000)
                        .setDefaults(Notification.DEFAULT_ALL)
                //        .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
                        .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.big_image)))
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .build();
                manager.notify(1, notification);
                break;

            case R.id.show_normal_notification:

                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle("normal Notification Title");
                mBuilder.setContentText("normal Notification Text");
                mBuilder.setWhen(System.currentTimeMillis()+1000*10);
                intent.putExtra("xixi","hehe");
                PendingIntent pi1 = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(pi1);
                Notification n = mBuilder.build();
                manager.notify(99, n);
                intent.putExtra("xixi","hehehe3");

                break;
            case R.id.show_big_picture_notification:
                break;
            case R.id.show_inbox_notification:
                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle("inbox Notification Title");
                mBuilder.setContentText("inbox Notification Text");
                Intent mIntent2 = new Intent();
                ComponentName name2 = new ComponentName("com.u.notificationtest", "com.a.notification.NotificationActivity");
                mIntent2.setComponent(name2);
                PendingIntent mPendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 0, mIntent2, 0);
                mBuilder.setContentIntent(mPendingIntent2);
                mBuilder.setPriority(Notification.PRIORITY_LOW);
                Notification.InboxStyle mInboxStyle = new Notification.InboxStyle();
                mInboxStyle.setBigContentTitle("This is Inbox BigContentTitle");
                mInboxStyle.setSummaryText("This is SummaryText");
                for(int i = 0;i<6;i++){ mInboxStyle.addLine("---------"+i); }
                mBuilder.setStyle(mInboxStyle);
                Notification n2 = mBuilder.build();
                manager.notify(96, n2);

                break;
            case R.id.show_media_notification:
//                Notification.Builder mBuilder1=new Notification.Builder(this) ;
               mBuilder.setContentIntent(pi);
               mBuilder.setSmallIcon(R.mipmap.ic_launcher);
               mBuilder.setContentTitle("this is Notification Title");
               mBuilder.setContentText("this is Notification Text");
               mBuilder.setPriority(Notification.PRIORITY_MAX);
                mBuilder.addAction(
                        new Notification.Action.Builder(R.mipmap.ic_launcher, "1", null)
                                .build());
                mBuilder.addAction(
                        new Notification.Action.Builder(R.mipmap.ic_launcher, "2", null)
                        .build());
                mBuilder.addAction(
                        new Notification.Action.Builder(R.mipmap.ic_launcher, "3", null)
                        .build());
               Notification.MediaStyle mMediaStyle = new Notification.MediaStyle();
               mMediaStyle.setShowActionsInCompactView(0,1,2);
               mBuilder.setStyle(mMediaStyle);
               Notification n3 = mBuilder.build();
               manager.notify(95, n3);
                break;
            case R.id.show_message_notification:

                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle("this is Notification Title");
                mBuilder.setContentText("this is Notification Text");
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                Notification.MessagingStyle mMessagingStyle = new Notification.MessagingStyle("zhuyuqiang");
                mMessagingStyle.addMessage("Message Content", 10*1000, "sender");
                mMessagingStyle.setConversationTitle("10086");
                mBuilder.setStyle(mMessagingStyle);
                Notification n4 = mBuilder.build();
                manager.notify(94, n4);

                break;
            case R.id.show_big_text_notification:
                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle("big text Notification Title");
                mBuilder.setContentText("big text Notification Text");
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
                bigTextStyle.setBigContentTitle("This is BigContentTitle");
                bigTextStyle.setSummaryText("This is BigSummaryText");
                bigTextStyle.bigText("This is BigText \n 如果。所有的伤痕都能够痊愈。 如果。所有的真心都能够换来真意。 如果。所有的相信都能够坚持。如果。所有的情感都能够完美。如果。依然能相遇在某座城。单纯的微笑。微微的幸福。肆意的拥抱。 该多好。可是真的只是如果。");
                mBuilder.setStyle(bigTextStyle);
                Notification n1= mBuilder.build();
                manager.notify(98, n1);

                break;
            case R.id.show_reply_notification:

                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle("reply Notification Title");
                mBuilder.setContentText("reply Notification Text");
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply").setLabel("reply label").build();
//                NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "reply",
//                        PendingIntent.getService(MainActivity.this, 0,new Intent("start.notification.service"),0)) .addRemoteInput(remoteInput).build();
//                mBuilder.addAction(action); mBuilder.setGroup("hello");
                Notification n5 = mBuilder.build();
                manager.notify(92, n5);

                break;
            case R.id.show_head_up_notification:
                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle("this is Notification Title");
                mBuilder.setContentText("this is Notification Text");
                mBuilder.setFullScreenIntent(pi,true); mBuilder.setPriority(Notification.PRIORITY_MAX);
//                mBuilder.setCustomHeadsUpContentView(new RemoteViews(getPackageName(),R.layout.heads_up));
                Notification n6 = mBuilder.build();
                manager.notify(91, n6);

                break;
            default:
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent() called with: ev = [" + ev + "]");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
        return super.onTouchEvent(event);
    }


}
