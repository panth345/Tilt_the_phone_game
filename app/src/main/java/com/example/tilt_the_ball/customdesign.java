package com.example.tilt_the_ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class customdesign extends LinearLayout {
private Paint paint;
private Bitmap bitmap;
  public customdesign(Context context, AttributeSet attrs)
  {
super(context, attrs);
paint = new Paint();

  }
}
