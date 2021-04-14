package com.example.tilt_the_ball;

import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseHelper db;
    List<Marker> markerList;
    LatLng location = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        db = new DatabaseHelper(this);
        Geocoder coder = new Geocoder(this);
        markerList = new ArrayList<>();
        //List<User> placeList = db.checkplace();
        Cursor result = db.getAll();
       String address[] = new String[result.getCount()];
       String email[] = new String[result.getCount()];
       String id[] = new String[result.getCount()];

            List<Address> geocodeResults = null;
            int index=0;
            while(result.moveToNext()){
                address[index] = result.getString(4);
                email[index] = result.getString(1);
                id[index] = result.getString(0);
                index++;

            }
            for (int x=0; x<address.length; ++x) {
                //   String myInfo = "ID: " + p.getId() + " Latitude: " + p.getLatitude() + "Longitude"
                //  + p.getLongitude() + " Title: " + p.getName();
                //  Log.d("myInfo", myInfo);


                try {
                    geocodeResults = coder.getFromLocationName(address[x], 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Iterator<Address> locations = geocodeResults.iterator();
                while (locations.hasNext()) {
                    Address loc = locations.next();
                    //latt = loc.getLatitude();
                    // longg = loc.getLongitude();
                    location = new LatLng(loc.getLatitude(), loc.getLongitude());
                    if (location == null) {
                        Toast.makeText(this, "There is no employee in the database", Toast.LENGTH_SHORT).show();

                        mMap.addMarker(new MarkerOptions().position(location).title("canada"));
                    } else {

                        markerList.add(mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(email[x])
                                .snippet(address[x])));
                    }

            }


            for (Marker m : markerList) {
                LatLng latLng = new LatLng(m.getPosition().latitude, m.getPosition().longitude);
                mMap.addMarker(new MarkerOptions().position(latLng));
            }


        }
    }
}
