/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    EditText name;
    EditText pass;
    TextView login;
    boolean logInActive;
    Button button;
    RelativeLayout layout;
    Toolbar toolbar;


    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            signOrLog(view);
        }
        return false;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.textView2){

            if(logInActive == true){
                logInActive = false;
                login.setText("Click here to Log In");
                button.setText("Sign Up");
            }else{
                logInActive = true;
                login.setText("Click here to Sign Up");
                button.setText("Log In");
            }

        }else if(view.getId()==R.id.layout ){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }



    public void signOrLog(View view){

        if(logInActive == true){

            ParseUser.logInInBackground(String.valueOf(name.getText()), String.valueOf(pass.getText()), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if(e==null){

                        if(isNetworkAvailable()){

                            if(user != null){
                                Toast.makeText(getApplicationContext(), "Welcome " + ParseUser.getCurrentUser().getUsername() + " !!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Error : You are not Registered :( " , Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext()," Sorry :(  No internet connection", Toast.LENGTH_SHORT).show();
                        }


                    }else{

                        if(isNetworkAvailable()){
                            Toast.makeText(getApplicationContext(),"Error : " + e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext()," Sorry :(, No internet connection", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });

        }else{

            ParseUser user = new ParseUser();
            user.setUsername(String.valueOf(name.getText()));
            user.setPassword(String.valueOf(pass.getText()));
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Toast.makeText(getApplicationContext(), "Successfully Signed Up :)", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      // making objects from xml
      name = (EditText) findViewById(R.id.editTextName);
      pass = (EditText) findViewById(R.id.editTextPassword);
      login = (TextView) findViewById(R.id.textView2);
      layout = (RelativeLayout) findViewById(R.id.layout);
      button = (Button) findViewById(R.id.button);
      logInActive = true;

      //setting listeners
      login.setOnClickListener(this);
      layout.setOnClickListener(this);
      name.setOnKeyListener(this);
      pass.setOnKeyListener(this);

      if(ParseUser.getCurrentUser() != null){
          Intent i = new Intent(this, Home.class);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
          i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
          startActivity(i);
      }

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
