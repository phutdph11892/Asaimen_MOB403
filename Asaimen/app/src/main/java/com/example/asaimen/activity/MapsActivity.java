package com.example.asaimen.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.asaimen.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import com.example.asaimen.activity.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbarThongTin;

    private GoogleMap mMap;
//    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

//        binding=ActivityMapsBinding.inflate( getLayoutInflater() );
        setContentView( R.layout.activity_maps );
        toolbarThongTin=(Toolbar) findViewById( R.id.toolbarThongTin );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        ActionToobar();
    }

    private void ActionToobar() {
        setSupportActionBar( toolbarThongTin );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbarThongTin.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
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
        mMap=googleMap;
//        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled( true );
        // Add a marker in Sydney and move the camera
        LatLng sydney=new LatLng( 21.038223465464622, 105.74683161746387 );
        mMap.addMarker( new MarkerOptions().position( sydney ).title( "Cao dang FPT Polytechnic" ).icon( BitmapDescriptorFactory.defaultMarker() ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
//        mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );
        CameraPosition cameraPosition=new CameraPosition.Builder().target( sydney ).zoom( 80 ).build();
        mMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
    }

}