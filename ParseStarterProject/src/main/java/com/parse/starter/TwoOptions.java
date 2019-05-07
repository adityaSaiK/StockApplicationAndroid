package com.parse.starter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class TwoOptions extends AppCompatActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_options);
        setTitle("Stock Watch");


        stocks = (Button) findViewById(R.id.button2);
        chat = (Button) findViewById(R.id.button3);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        if(item.getItemId()== R.id.logout)
        {
            ParseUser.logOut();

            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }


}
