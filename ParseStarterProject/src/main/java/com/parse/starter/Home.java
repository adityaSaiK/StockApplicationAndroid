package com.parse.starter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {


    Button stocks;
    Button chat;


    public void moveToChatActivity(View view){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btn_click);
        mp.start();
        Intent i = new Intent(this, ListUsers.class);
        startActivity(i);
    }

    public void moveToStockActivity(View view){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btn_click);
        mp.start();
        Intent i = new Intent(this, VariousStockViews.class);
        startActivity(i);
    }

    public void moveToAboutActivity(View view){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btn_click);
        mp.start();
        Intent i = new Intent(this, About.class);
        startActivity(i);
    }

    public void moveToFeedbackFragment(View view){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btn_click);
        mp.start();

        Feedback newfeedback = new Feedback();
        newfeedback.show(getSupportFragmentManager(), "fragment");
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
