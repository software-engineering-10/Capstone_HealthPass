package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class gpsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    TextView status;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest
                .permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);


    }

    // NULL이 아닌 GoogleMap 객체를 파라미터로 제공해 줄 수 있을 때 호출
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        BitmapDescriptor highlightedIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        mMap = googleMap;
        LatLng location = new LatLng(36.7726, 126.9347);
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("웰컴투짐 순천향대점"));

        // 정보 창 항상 표시
        marker.showInfoWindow();



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.7702,126.9332), 18));
    }
    public void call(View view){
        String phoneNumber = "0507-1362-3123"; // 전화 걸고자 하는 번호
        Uri phoneUri = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneUri);

        // 전화 앱 실행
        startActivity(callIntent);
    }
}