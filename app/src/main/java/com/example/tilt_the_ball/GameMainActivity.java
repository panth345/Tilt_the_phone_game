package com.example.tilt_the_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;


import java.util.Timer;
import java.util.TimerTask;

public class GameMainActivity extends AppCompatActivity implements SensorEventListener {
    ImageButton Demo_button;

    private boolean firstImageShown = true;
    private float circleX;
    private float circleY;

    private Timer timer;
    private Handler handler;

    private float sensorX;
    private float sensorY;
    private float sensorZ;
    private long lastSensorUpdateTime = 0;
    Bitmap bitmap, mBackground;

    public static int x;
    public static int y;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private  MediaPlayer mediaPlayer;
    private AnimatedView mAnimatedView = null;
    String counter, Score;
CountDownTimer count;
    String emailfromIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bg_music);
        mediaPlayer.start();
        emailfromIntent = getIntent().getStringExtra("email");
        /*Demo_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (firstImageShown) {
                    Demo_button.setImageResource(R.drawable.soundon);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bg_music);
                    if(mediaPlayer != null){
                        mediaPlayer.start();
                        mediaPlayer.setLooping(true);
                    }

                } else {
                    Demo_button.setImageResource(R.drawable.soundoff);
                    if(mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                }
                firstImageShown = !firstImageShown;
            }
        });
        setContentView(R.layout.activity_game_main);*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mAnimatedView = new AnimatedView(this);

       // mAnimatedView.setBackground(R.drawable.golf);
        setContentView(mAnimatedView);

        getSupportActionBar().hide();
         count = new CountDownTimer(60000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                counter = String.valueOf(millisUntilFinished/1000);
                Log.d("Timer Value: ", millisUntilFinished/1000+"");
            }

            @Override
            public void onFinish() {
               counter = "0";
            }
        }.start();


        //Demo_button = (ImageButton) findViewById(R.id.sound);


// when you click this demo button
/*        Demo_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (firstImageShown) {
                    Demo_button.setImageResource(R.drawable.soundon);

                } else {
                    Demo_button.setImageResource(R.drawable.soundoff);
                }
                firstImageShown = !firstImageShown;
            }
        });*/




    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAnimatedView.onSensorEvent(event);
        }
    }

    public class AnimatedView extends View {

        ImageButton Demo_button;

        private int circleRadius = 80;
        private static final int CIRCLE_RADIUS = 25; //pixels
Paint paint = new Paint();
        Paint paint1 = new Paint();
        private Paint mPaint;
        private int x;
        private int y;
        private int viewWidth;
        private int viewHeight;
        private int a;
        private int b;

        public AnimatedView(Context context) {
            super(context);

            mPaint = new Paint();
            mPaint.setColor(Color.WHITE);
           paint.setColor(Color.parseColor("#964B00"));
           paint1.setColor(Color.parseColor("#FF8C00"));


        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;

        }

        public void onSensorEvent (SensorEvent event) {
            x = x - (int) event.values[0];
            y = y + (int) event.values[1];
            //Make sure we do not draw outside the bounds of the view.
            //So the max values we can draw to are the bounds + the size of the circle
            if (x <= 0 + CIRCLE_RADIUS) {
                x = 0 + CIRCLE_RADIUS;
            }
            if (x >= viewWidth - CIRCLE_RADIUS) {
                x = viewWidth - CIRCLE_RADIUS;
            }
            if (y <= 0 + CIRCLE_RADIUS) {
                y = 0 + CIRCLE_RADIUS;
            }
            if (y >= viewHeight - CIRCLE_RADIUS) {
                y = viewHeight - CIRCLE_RADIUS;
            }




            int v = Integer.parseInt(counter.trim());
            if (((x >= viewWidth/2 - circleRadius/2 - 10 )&& (x<= viewWidth/2 + circleRadius/2 + 10) && (y >= viewHeight/2 - circleRadius/2 - 10) && (y <= viewHeight/2 + circleRadius/2 + 10)) || v == 0){

if(count!= null){
                count.cancel();}

                if(v<=60 && v>=50){
                    Score = "500";
                }else if(v<=49 && v>=30){
                    Score = "400";
                }else if(v<=29 && v>=20){
                    Score = "300";
                }else if(v<=19 && v>=5){
                    Score = "100";
                }else{
                    Score="0";
                }



               Intent i = new Intent(getApplicationContext(),EndMainActivity.class);
               i.putExtra("score",Score);
               i.putExtra("email", emailfromIntent);
               startActivity(i);
            }
            String hole  = String.valueOf(viewWidth/2) + " &" + String.valueOf(viewHeight/2);
            Log.d("value", hole);
            String ball = String.valueOf(x) + " &" + String.valueOf(y);
            Log.d("ball", ball);
        }

        @Override
        protected void onDraw(Canvas canvas) {


            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.golf);
            bmp= resizeImage(bmp, viewWidth,viewHeight);
            canvas.drawBitmap(bmp,0,0,null);
            canvas.drawBitmap(bmp,180,0,null);


                b = viewHeight/2;
                a = viewWidth/2;
            //anvas.drawPicture(bmp1);
            //canvas.drawBitmap(bmp1,500,800,paint1);
            canvas.drawCircle(a,b,circleRadius,paint);
            canvas.drawCircle(x, y, CIRCLE_RADIUS, mPaint);

            //We need to call invalidate each time, so that the view continuously draws
            invalidate();


        }
        public Bitmap resizeImage(Bitmap image,int maxWidth, int maxHeight)
        {
            Bitmap resizedImage = null;
            try {
                int imageHeight = image.getHeight();


                if (imageHeight > maxHeight)
                    imageHeight = maxHeight;
                int imageWidth = (imageHeight * image.getWidth())
                        / image.getHeight();

                if (imageWidth > maxWidth) {
                    imageWidth = maxWidth;
                    imageHeight = (imageWidth * image.getHeight())
                            / image.getWidth();
                }

                if (imageHeight > maxHeight)
                    imageHeight = maxHeight;
                if (imageWidth > maxWidth)
                    imageWidth = maxWidth;


                resizedImage = Bitmap.createScaledBitmap(image, imageWidth,
                        imageHeight, true);
            } catch (OutOfMemoryError e) {

                e.printStackTrace();
            }catch(NullPointerException e)
            {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return resizedImage;
        }


    }


}