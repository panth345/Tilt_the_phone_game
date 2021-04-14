package com.example.tilt_the_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class EndMainActivity extends AppCompatActivity {
    private Button tryagain;
    SmileRating smileRating;
    MediaPlayer mediaPlayer;
    String timer, Score;
    TextView scoreboard;
    String emailfromIntent;
    DatabaseHelper db = new DatabaseHelper(this);
  private static final String TAG = "App";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_main);
        Intent i = getIntent();

        Score = i.getStringExtra("score");
        emailfromIntent = getIntent().getStringExtra("email");
        scoreboard = findViewById(R.id.sa_score);
        scoreboard.setText(Score);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bg_music);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.start();
        getSupportActionBar().hide();
         smileRating = (SmileRating) findViewById(R.id.smile_rating);
        tryagain = findViewById(R.id.retry);

        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               db.Scoreupdate(emailfromIntent, Score);
               Log.d("data:", emailfromIntent);
                Intent intent = new Intent(EndMainActivity.this,UserListActivity.class);
                intent.putExtra("EMAIL", emailfromIntent);
                EndMainActivity.this.startActivity(intent);
                EndMainActivity.this.finish();


            }
        });
smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
    @Override
    public void onSmileySelected(int smiley, boolean reselected) {
        switch (smiley) {
            case SmileRating.BAD:
                Toast.makeText(EndMainActivity.this,"Bad",Toast.LENGTH_SHORT).show();
               // Log.i(TAG, "Bad");
                break;
            case SmileRating.GOOD:
                Toast.makeText(EndMainActivity.this,"Good",Toast.LENGTH_SHORT).show();
                // Log.i(TAG, "Good");
                break;
            case SmileRating.GREAT:
                Toast.makeText(EndMainActivity.this,"Great",Toast.LENGTH_SHORT).show();
                //Log.i(TAG, "Great");
                break;
            case SmileRating.OKAY:
                Toast.makeText(EndMainActivity.this,"Okay",Toast.LENGTH_SHORT).show();
               // Log.i(TAG, "Okay");
                break;
            case SmileRating.TERRIBLE:
                Toast.makeText(EndMainActivity.this,"Terrible",Toast.LENGTH_SHORT).show();
               // Log.i(TAG, "Terrible");
                break;
        }
    }
});

smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
    @Override
    public void onRatingSelected(int level, boolean reselected) {
      //  Toast.makeText(EndMainActivity.this,"Selected rating"+ level,Toast.LENGTH_SHORT).show();

    }
});

    }
}