package com.a.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MSurfaceView extends SurfaceView {
              LoopThread thread;
    private ZoomImgActivity.MyGestureListener mGestureListener;
    private GestureDetector mGestureDetector;
    private Context context;

    public MSurfaceView(Context context) {
                     super(context);
                 }
                 public MSurfaceView(Context context, AttributeSet attributeSet){
                  super(context,attributeSet);
                  this.context=context;
                     init(); //初始化,设置生命周期回调方法
                 }

              private void init(){

                     SurfaceHolder holder = getHolder();

                  //设置Surface生命周期回调
                     holder.addCallback(new SurfaceHolder.Callback() {
                         @Override
                         public void surfaceCreated(SurfaceHolder holder) {
                             thread.isRunning = true;
                             thread.start();
                         }

                         @Override
                         public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                         }

                         @Override
                         public void surfaceDestroyed(SurfaceHolder holder) {
                             thread.isRunning = false;
                             try {
                                 thread.join();
                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                         }
                     });



                     thread = new LoopThread(holder, getContext());

                 }


    public boolean onTouchEvent(MotionEvent event) {
                  super.onTouchEvent(event);
//                  mGestureDetector.onTouchEvent(event);
                  return false;
                  
    }

              /**
        * 执行绘制的绘制线程
        * @author Administrator
        *
     */
              class LoopThread extends Thread{

                  SurfaceHolder surfaceHolder;
          Context context;
        boolean isRunning;
          float radius = 10f;
          Paint paint;

                  public LoopThread(SurfaceHolder surfaceHolder,Context context){

                           this.surfaceHolder = surfaceHolder;
                             this.context = context;
                             isRunning = false;

                           paint = new Paint();
                             paint.setColor(Color.BLACK);
                             paint.setStyle(Paint.Style.FILL_AND_STROKE);
                       }

                  @Override
        public void run() {

                             Canvas c = null;

                           while(isRunning){

                                     try{
                                             synchronized (surfaceHolder) {

                                                     c = surfaceHolder.lockCanvas(null);
                                                   doDraw(c);
                                                     //通过它来控制帧数执行一次绘制后休息50ms
                                                     Thread.sleep(1);
                                                 }
                                       } catch (InterruptedException e) {
                                             e.printStackTrace();
                                         } finally {
                                             surfaceHolder.unlockCanvasAndPost(c);
                                         }
                    
                                 }
                
                         }  
 
                  public void doDraw(Canvas c){

                             //这个很重要，清屏操作，清楚掉上次绘制的残留图像
                             c.drawColor(Color.rgb(255,255,255));

                             c.translate(200, 200);
                             c.drawCircle(0,0, radius++, paint);

                             if(radius > 100){
                                     radius = 10f;
                               }

                         }

         }

       
}