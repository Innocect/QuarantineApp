package com.example.quarintineinternship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class AfterSplash3rd extends Activity {
    Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_splash_3rd);

        next = findViewById(R.id.next);
    }

    public void nextButton(View view){
        Intent intent=new Intent(AfterSplash3rd.this,homePageWithoutMenu.class);
        startActivity(intent);
        finish();
    }
}
