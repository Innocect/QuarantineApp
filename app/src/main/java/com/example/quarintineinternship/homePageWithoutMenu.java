package com.example.quarintineinternship;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class homePageWithoutMenu extends AppCompatActivity {
    ImageButton rent;
    ImageButton search_quarintine;
    ImageButton medicine;
    ImageButton room_service;
    ImageButton food_service;
    ImageButton video_conference;

    public void rent(View view){
        Intent intent=new Intent(homePageWithoutMenu.this,MainActivity.class);
        startActivity(intent);
    }

    public void search_quarintine(View view){
        Intent intent=new Intent(homePageWithoutMenu.this,MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_without_menu);

        rent = findViewById(R.id.houses);
        search_quarintine = findViewById(R.id.search);







    }
}
