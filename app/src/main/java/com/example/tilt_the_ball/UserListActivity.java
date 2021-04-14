package com.example.tilt_the_ball;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserListActivity extends AppCompatActivity {
    TextView textView;
    Dialog epicDialog;
    ImageView closepopimg,img6,img5,img3,img2,img4;
    ImageButton Demo_button;
    MediaPlayer  mediaPlayer;

    private boolean firstImageShown = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        getSupportActionBar().hide();
        epicDialog = new Dialog(this);
        textView = findViewById(R.id.textview);
        String emailfromIntent = getIntent().getStringExtra("EMAIL");
        textView.setText(emailfromIntent);
        ImageView img = (ImageView) findViewById(R.id.start);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mySuperIntent = new Intent(UserListActivity.this, GameMainActivity.class);
                mySuperIntent.putExtra("email", emailfromIntent);
                startActivity(mySuperIntent);

            }
        });
        img2 = (ImageView) findViewById(R.id.leader);
        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mySuperIntent = new Intent(UserListActivity.this, leaderboardMainActivity.class);
                startActivity(mySuperIntent);
            }
        });
         img3 = (ImageView) findViewById(R.id.location);
        img3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mySuperIntent = new Intent(UserListActivity.this, MapsActivity.class);
                startActivity(mySuperIntent);
            }
        });
        img4 = (ImageView) findViewById(R.id.profile);
        img4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mySuperIntent = new Intent(UserListActivity.this, UpdateprofileMainActivity.class);
                mySuperIntent.putExtra("EMAIL", textView.getText().toString().trim());
                startActivity(mySuperIntent);
            }
        });

       img6 = (ImageView) findViewById(R.id.logout);
        img6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mySuperIntent = new Intent(UserListActivity.this, MainActivity.class);
                startActivity(mySuperIntent);

            }
        });

        img5 = (ImageView) findViewById(R.id.help);
        img5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAlert();


            }
        });
        Demo_button = (ImageButton) findViewById(R.id.sound);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bg_music);
        Demo_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (firstImageShown) {
                    Demo_button.setImageResource(R.drawable.soundon);


                        mediaPlayer.start();
                       // mediaPlayer.setLooping(true);


                } else {
                    Demo_button.setImageResource(R.drawable.soundoff);

                        mediaPlayer.pause();
                       // mediaPlayer.release();
                    }

                firstImageShown = !firstImageShown;
            }
        });
    }


    public void showAlert()
    {
         epicDialog.setContentView(R.layout.epic_popup);
         closepopimg = (ImageView) epicDialog.findViewById(R.id.closepop);
         closepopimg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              epicDialog.dismiss();
          }
      });
       epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       epicDialog.show();
    }
}