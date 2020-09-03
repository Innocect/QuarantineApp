package com.example.quarintineinternship;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.List;

public class FindQuarintineMap extends AppCompatActivity implements OnMapReadyCallback {

    private final float zoomMulti = 18;

    private GoogleMap gMap;
    private FusedLocationProviderClient currLocation;
    private PlacesClient searchHints;
    private List<AutocompletePrediction> predictionList;

    private Location lastKnownLocation;
    private LocationCallback lastKnownLocNull; //will be used when last known location is null

    private MaterialSearchBar searchBar;
    private View mapView;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_quarintine_map);

        // Defining the vars

        searchBar = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.search_map_btn);

        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        assert mapFragment != null; // remove this st
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView(); // getting the fragment as View


    }

    // After the map gets Loaded we need to implement all the attributes here
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;
        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(true); // for current location button

        // to set the location button at the end

        if( mapView!=null && mapView.findViewById(Integer.parseInt("1"))!=null ){
           View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,0); // removing it from top to bellow
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,40,130);

        }

        // Check if Gps is enabled or not
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(FindQuarintineMap.this) ;
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        // 2 possibilities either the location will be On or they may not be on 1 00


    }
}
