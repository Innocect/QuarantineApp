package com.example.quarintineinternship;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        assert mapFragment != null; // remove this st
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView(); // getting the fragment as View
        currLocation = LocationServices.getFusedLocationProviderClient(FindQuarintineMap.this);
        Places.initialize(FindQuarintineMap.this, "AIzaSyAUaHdep4Enm0RdAR28Ee2i9dK8i08n9x8");
        searchHints = Places.createClient(this);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();


//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i("Himanshu", "Place: " + place.getName() + ", " + place.getId());
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i("Himanshu", "An error occurred: " + status);
//            }
//        });
//    }
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, false);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    searchBar.closeSearch();
                }
            }
        });

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setCountry("IN")
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery(charSequence.toString())
                        .build();
                searchHints.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if (task.isSuccessful()) {
                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                            if (predictionsResponse != null) {
                                predictionList = predictionsResponse.getAutocompletePredictions();
                                List<String> convertedPredictionList = new ArrayList<>();
                                for (AutocompletePrediction i : predictionList) {
                                    convertedPredictionList.add(i.getFullText(null).toString());
                                }
                                searchBar.updateLastSuggestions(convertedPredictionList);
                                if (!searchBar.isSuggestionsVisible()) {
                                    searchBar.showSuggestionsList();
                                }
                            }
                        } else {
                            Toast.makeText(FindQuarintineMap.this, "Places Clint error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
        // After the map gets Loaded we need to implement all the attributes here
        @Override
        public void onMapReady (GoogleMap googleMap){
            this.gMap = googleMap;
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setMyLocationButtonEnabled(true); // for current location button

            // to set the location button at the end

            if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0); // removing it from top to bellow
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                layoutParams.setMargins(0, 0, 150, 150);
//            layoutParams.rightMargin = 1950;
            }

            // Check if Gps is enabled or not
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(3000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            SettingsClient settingsClient = LocationServices.getSettingsClient(FindQuarintineMap.this);
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

            // 2 possibilities either the location will be On or they may not be on 1 00


            task.addOnSuccessListener(FindQuarintineMap.this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    currLocationFunc();
                }
            });

            task.addOnFailureListener(FindQuarintineMap.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;

                        try {
                            // 1 is code for Resolvable Exception that user enabled the location
                            resolvableApiException.startResolutionForResult(FindQuarintineMap.this, 1);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                            Toast.makeText(FindQuarintineMap.this, "Resolvable Exception", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            //If the user enabled the gps
            if (requestCode == 1) {
                if (resultCode == RESULT_OK)
                    currLocationFunc();
            }
        }

        // we are checking if the lastLocation is received or not, but it does not guarantee it, and we move the camera.
        private void currLocationFunc () {
            currLocation.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        lastKnownLocation = task.getResult(); // lastKnown location should not be null

                        if (lastKnownLocation != null) {
                            // now we will move the map camera
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), zoomMulti));
                        } else {
                            final LocationRequest locationRequest = LocationRequest.create();
                            locationRequest.setInterval(1000);
                            locationRequest.setFastestInterval(3000);
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            lastKnownLocNull = new LocationCallback() {
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    // If last known location is still null it means not on Earth
                                    if (locationRequest == null) {
                                        return;
                                    }
                                    lastKnownLocation = locationResult.getLastLocation();
                                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), zoomMulti));
                                    currLocation.removeLocationUpdates(lastKnownLocNull);
                                }
                            };

                            currLocation.requestLocationUpdates(locationRequest, lastKnownLocNull, null);
                        }
                    } else {
                        // task is not successful
                        Toast.makeText(FindQuarintineMap.this, "Location not fetched", Toast.LENGTH_SHORT).show();
                        Log.i("Himanshu", "Error in task not successfull");
                    }
                }
            });
        }

}
