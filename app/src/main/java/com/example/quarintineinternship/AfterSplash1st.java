package com.example.quarintineinternship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class AfterSplash1st extends Activity {

    Button skip;
    Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_splash_1st);

        skip = findViewById(R.id.skip);
        next = findViewById(R.id.next);
            }

    public void skipButton(View view){
        Intent intent=new Intent(AfterSplash1st.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void nextButton(View view){
        Intent intent=new Intent(AfterSplash1st.this,AfterSplash3rd.class);
        startActivity(intent);
        finish();
    }
}
