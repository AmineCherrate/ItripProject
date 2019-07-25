/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public void redirectUser(){
        if (ParseUser.getCurrentUser()!=null){
            Intent intent = new Intent(getApplicationContext(),TweetActivity.class);
            startActivity(intent);
        }
    }

    Boolean signUpModeActive=true;
    TextView signuporloginText;

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.signuporloginText){
            Button singupBtn = (Button) findViewById(R.id.singupBtn);
            if (signUpModeActive){
                signUpModeActive=false;
                singupBtn.setText("Login");
                signuporloginText.setText("or, Signup");
            }else{
                signUpModeActive=true;
                singupBtn.setText("SignUp");
                signuporloginText.setText("or, Login");
            }
        }
    }


    public void signUp(View view){
        EditText usernameET = (EditText) findViewById(R.id.usernameET);
        EditText passwordET = (EditText) findViewById(R.id.passwordET);


        if(usernameET.getText().toString().matches("") || passwordET.getText().toString().matches("") )
        {
            Toast.makeText(this,"A ussername and password are required", Toast.LENGTH_SHORT).show();
        }else {
            if(signUpModeActive){ ParseUser user = new ParseUser();

                user.setUsername(usernameET.getText().toString());
                user.setPassword(passwordET.getText().toString());


                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){

                            Log.i("Signup","successful");
                            redirectUser();

                        }else {
                            Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }); }else {
                ParseUser.logInInBackground(usernameET.getText().toString(), passwordET.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                         if (user!=null){
                             Log.i("Login","Successful");
                             redirectUser();
                         }else{
                             Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                         }
                    }
                });

            }

        }


    }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      signuporloginText = (TextView) findViewById(R.id.signuporloginText);
      signuporloginText.setOnClickListener(this);
      redirectUser();

      setTitle("iTrip App");
/*
     ParseUser.logOut();
    if (ParseUser.getCurrentUser()!=null){

      Log.i("currentUser","user Logged in"+ ParseUser.getCurrentUser().getUsername());
    }else
    {
      Log.i("currentuser",  "User not logged in ");

    }
*/

/*
    ParseUser.logInInBackground("luq.wqw", "12345", new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(user!=null){
          Log.i("Login","Successful");

        }else {
          Log.i("Login","failed");
        }
      }
    });
*/
/*
      ParseUser user = new ParseUser();
      user.setUsername("luq.wqw1");
      user.setPassword("123456");


      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if(e==null){
            Log.i("Sign up","Successfull");
          }
          else{
            Log.i("Sign up", "Failed");
          }
        }
      });
*/



    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}