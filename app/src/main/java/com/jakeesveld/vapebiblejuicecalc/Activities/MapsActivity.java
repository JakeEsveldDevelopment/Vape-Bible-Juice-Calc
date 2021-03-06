package com.jakeesveld.vapebiblejuicecalc.Activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jakeesveld.vapebiblejuicecalc.R;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    Context context;
    public static final int LOCATION_REQUEST_CODE = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String url = marker.getSnippet();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // Add a marker in Sydney and move the camera
        LatLng marker1 = new LatLng(33.506200, -112.084150);
        mMap.addMarker(new MarkerOptions().position(marker1).title("Liquid Nicotine Wholesalers").snippet("https://www.liquidnicotinewholesalers.com"));

        LatLng marker2 = new LatLng(33.417200, -111.961770);
        mMap.addMarker(new MarkerOptions().position(marker2).title("Ecig and Vape Wholesale").snippet("https://www.ecigandvapewholesale.com"));

        LatLng marker3 = new LatLng(42.672380, -83.339160);
        mMap.addMarker(new MarkerOptions().position(marker3).title("DIY Vapor Supply").snippet("https://www.diyvaporsupply.com"));

        LatLng marker4 = new LatLng(34.999890, -83.339160);
        mMap.addMarker(new MarkerOptions().position(marker4).title("Nic Vape").snippet("https://www.nicvape.com"));

        LatLng marker5 = new LatLng(34.276540, -118.786030);
        mMap.addMarker(new MarkerOptions().position(marker5).title("Liquid Barn").snippet("https://www.liquidbarn.com"));

        LatLng marker6 = new LatLng(33.192670, -96.554330);
        mMap.addMarker(new MarkerOptions().position(marker6).title("Central Vapors").snippet("https://www.centralvapers.com"));

        LatLng marker7 = new LatLng(38.787360, -90.626250);
        mMap.addMarker(new MarkerOptions().position(marker7).title("The Vape Mall").snippet("https://www.thevapemall.com"));

        getLocation();
    }


    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng postiion = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(postiion, 3f));
                mMap.addMarker(new MarkerOptions().position(postiion).title("You are Here"));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }
}
