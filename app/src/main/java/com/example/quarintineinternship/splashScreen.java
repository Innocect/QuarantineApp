package com.example.quarintineinternship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;

public class splashScreen extends Activity {

    Handler handler;
    SharedPreferences loginPreference;
    String MY_PREF = "my_pref";
    @Override
    /*
    The logic beneath will open splash screen only  once
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        loginPreference = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);

        if(loginPreference.getString("tag", "notok").equals("notok")){


            SharedPreferences.Editor edit = loginPreference.edit();
            edit.putString("tag", "ok");
            edit.apply();


            setContentView(R.layout.splash);
            runner();

        }else if(loginPreference.getString("tag", null).equals("ok")){
            Intent i = new Intent(splashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }


    }

    private void runner(){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashScreen.this,AfterSplash1st.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }


}
