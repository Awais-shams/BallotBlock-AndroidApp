package com.example.ballotblock.navigation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ballotblock.R;
import com.example.ballotblock.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    private ActivityMapsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String loc = location.getLatitude()+" "+location.getLongitude();
                Toast.makeText(MapsActivity.this, "Location: " + loc, Toast.LENGTH_SHORT).show();
            }
        };

        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, locationListener);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, locationListener);
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        mMap = googleMap;
//
//        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//
//        // Add a marker in Sydney and move the camera
//        LatLng cfd = new LatLng(31.602069474880714, 73.0357313);
//        LatLng cfd2 = new LatLng(32.602069474880714, 73.0357313);
//        LatLng cfd3 = new LatLng(31.602069474880714, 74.0357313);
//        LatLng cfd4 = new LatLng(30.602069474880714, 72.0357313);
//        LatLng cfd5 = new LatLng(32.602069474880714, 72.0357313);
//
//        mMap.addMarker(new MarkerOptions().title("Jani why not?").position(cfd).draggable(true).
//                icon(BitmapDescriptorFactory.fromResource(R.drawable.aa)));
//
//        Polygon polygon = mMap.addPolygon(new PolygonOptions()
//                .add(cfd,cfd2,cfd3,cfd4,cfd5).fillColor(Color.LTGRAY).strokeColor(Color.RED));
//
////        PolylineOptions options = new PolylineOptions().add(cfd)
////                .add(cfd2).add(cfd3).add(cfd4).add(cfd5).add(cfd);
////        Polyline polyline = mMap.addPolyline(options);
////        polyline.setColor(Color.DKGRAY);
////        polyline.setClickable(true);
////        mMap.setOnPolylineClickListener((GoogleMap.OnPolylineClickListener) this);
//
//
////        CircleOptions options = new CircleOptions().center(cfd).fillColor(Color.CYAN).
////                strokeWidth(15).strokeColor(Color.RED).radius(400);
////        Circle circle = mMap.addCircle(options);
//
//        //icon changes icon
////        mMap.addMarker(new MarkerOptions().position(cfd).title("jani why not Loonawala").
////                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cfd, 5));
    }
}