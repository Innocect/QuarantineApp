package com.example.quarintineinternship;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mikhaellopez.circularimageview.CircularImageView;

public class homePageHamBurger extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Intialize All

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    ImageButton rent;
    ImageButton search_quarintine;
    ImageButton medicine;
    ImageButton room_service;
    ImageButton food_service;
    ImageButton video_conference;
    ImageView drawerImage;

    public void rent(View view){
        Intent intent=new Intent(homePageHamBurger.this,MainActivity.class);
        startActivity(intent);
    }

    // for onclick search_quarintine using dexter
    public void search_quarintine(View view){

        if(ContextCompat.checkSelfPermission(homePageHamBurger.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(homePageHamBurger.this,FindQuarintineMap.class);
            startActivity(intent);
            //return;
        }
        else {
            Dexter.withContext(homePageHamBurger.this).
                    withPermission(Manifest.permission.ACCESS_FINE_LOCATION).
                    withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(homePageHamBurger.this, FindQuarintineMap.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            if (permissionDeniedResponse.isPermanentlyDenied()){
                                    Toast.makeText(homePageHamBurger.this, "Plz Give the Permissions", Toast.LENGTH_LONG).show();
                                    //if you want to take the user to settings then plz create an Alert Diologue
                            }
                            else
                                Toast.makeText(homePageHamBurger.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        // all the Variables declaration
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        rent = findViewById(R.id.houses);
        search_quarintine = findViewById(R.id.search);
        drawerImage = findViewById(R.id.drawer_image);



        // for menu
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //For item Listener

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }
}
