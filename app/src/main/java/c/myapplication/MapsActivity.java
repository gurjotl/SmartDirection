package c.myapplication;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String google_maps_key = "AIzaSyA56lxPWdH-ZoZRQ6CquRq1xZrzZsOpsNs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front);
        //BUTTON
        Button button1 = (Button) findViewById(R.id.button1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Button Pressed");

                //GETS FROM
                EditText editText = (EditText)findViewById(R.id.editText);
                String from_input = editText.getText().toString();


                //GETS IN
                EditText editText2 = (EditText)findViewById(R.id.editText2);
                String to_input = editText2.getText().toString();

                double from_latitude = 0;
                double from_longitude = 0;
                List<Address> geocodeMatches = null;

                double to_latitude = 0;
                double to_longitude = 0;
                List<Address> geocodeMatches2 = null;

                try {
                    geocodeMatches =
                            new Geocoder(MapsActivity.this).getFromLocationName(
                                    from_input, 1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                if (!geocodeMatches.isEmpty())
                {
                    from_latitude = geocodeMatches.get(0).getLatitude();
                    from_longitude = geocodeMatches.get(0).getLongitude();
                }

                //Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.ENGLISH);
                try {
                    geocodeMatches2 =
                            new Geocoder(MapsActivity.this).getFromLocationName(
                                    to_input, 1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (!geocodeMatches2.isEmpty())
                {
                    to_latitude = geocodeMatches2.get(0).getLatitude();
                    to_longitude = geocodeMatches2.get(0).getLongitude();
                }


                LatLng from_LatLong = new LatLng(from_latitude, from_longitude);
                LatLng to_LatLong   = new LatLng(to_latitude,   to_longitude);
                LatLngBounds LLbounds = new LatLngBounds(from_LatLong, to_LatLong);


                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LLbounds,0));

                PolylineOptions path = new PolylineOptions()
                        .add(from_LatLong)
                        .add(to_LatLong);


                /*Marker from_marker = new Marker(mMap);
                from_marker.setPosition(from_LatLong);
                from_marker.setTitle("from Location");


                Marker to_marker = new Marker(mMap);
                to_marker.setPosition(to_LatLong);
                to_marker.setTitle("to Location");*/

                //URL
                String link = "http://maps.googleapis.com/maps/api/directions/origin=";
                String from_replaced = from_input.replaceAll(" ", "+");
                String to_replaced = to_input.replaceAll(" ", "+");
                String requestURL = String.format(link, Uri.encode(from_replaced), "&destination=", Uri.encode(to_replaced), "&key="+ google_maps_key);//AIzaSyBtdDsFlvMdxofySaum765Wm0DAapvKAwQ");


            }

        });

        //SWITCH
        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            boolean on_off = false;

            public void onClick(View v) {
                count++;
                if (count % 2 == 1) {
                    on_off = true;
                    System.out.println("ON");
                } else {
                    on_off = false;
                    System.out.println("OFF");
                }

                //SHIT WITH SWITCH HERE



            }
        });

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
        //System.out.println("Enter starting Location: ");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-18.142, 178.431), 2));

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
