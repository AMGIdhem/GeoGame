package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.location.LocationListener;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.test.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapClickListener,
        View.OnClickListener {

    MyDatabaseHelper myDB = new MyDatabaseHelper(this);
    private GoogleMap mMap;
    double end_latitude, end_longitude;
    public static final String SHARED_PREFS = "SharedPrefs";
    Polyline polyline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng morocco = new LatLng(31.699069, -7.877584);
        LatLng tiznit = new LatLng(29.696063, -9.727137);
        List<LatLng> latLngList = new ArrayList<>();
        latLngList.add(morocco);
        latLngList.add(tiznit);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(morocco, 6));
        mMap.addMarker(new MarkerOptions().position(morocco).title("Morocco"));
        mMap.addMarker(new MarkerOptions().position(tiznit).title("Tiznit"));


        if (polyline != null) polyline.remove();
        PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngList).clickable(true);
        polyline = mMap.addPolyline(polylineOptions);

        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener((GoogleMap.OnMapClickListener) this);

    }

    public void onClick(View v) {
        if(v.getId() == R.id.B_to) {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            int number = sharedPreferences.getInt("img", -1);
            String savedRandomNumbers = sharedPreferences.getString("randomNumbers", "");
            StringTokenizer st = new StringTokenizer(savedRandomNumbers, ",");
            int[] randomNumbers = new int[7];
            for (int i = 0; i < 7; i++) {
                randomNumbers[i] = Integer.parseInt(st.nextToken());
            }
            Cursor cursor = myDB.getLocation(randomNumbers[number]);
            cursor.moveToNext();
            double latitude = Double.parseDouble(cursor.getString(3));
            double longitude = Double.parseDouble(cursor.getString(4));
            Log.i("MapsActivity", "LAT = " + cursor.getString(3));

            mMap.clear();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(end_latitude, end_longitude));
            markerOptions.title("Destination");
            float results[] = new float[10];


            Location.distanceBetween(latitude, longitude, end_latitude, end_longitude, results);
            markerOptions.snippet("Distance = "+ results[0]/1000);
            mMap.addMarker(markerOptions);
            Intent intent;
            intent = new Intent(this, ThirdActivity.class);
            intent.putExtra("distance", results[0]/1000);
            startActivity(intent);
        }


    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        marker.setDraggable(true);
        return false;
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        end_latitude = marker.getPosition().latitude;
        end_longitude = marker.getPosition().longitude;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("You clicked here"));
        end_latitude = latLng.latitude;
        end_longitude = latLng.longitude;
    }
}