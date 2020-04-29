package com.example.manyatta.environconserve;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Retrieve_Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static final int MY_PERMISSION_REQUEST_LOCATION = 99;


    ProgressDialog pd;
    //String report_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve__map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestLocationPermission();
        }

        pd = new ProgressDialog(Retrieve_Map.this);

        //Intent getdetails = getIntent();
        //report_id = getdetails.getStringExtra("officer");

        //call class to retrieve coordinates
        new Retrieve_Coordinates();//.execute(report_id);

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
        try {
            //Shows my current location
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {

        }
        //  to add zoom buttons
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // to allow to zoom using gestures
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        // to add compass to your map
        mMap.getUiSettings().setCompassEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }

        } else {
            mMap.setMyLocationEnabled(true);

        }
    }


    public boolean requestLocationPermission(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);

            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);
            }
            return false;
        }else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],int[] grantResults){
        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION: {
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED){
                        mMap.setMyLocationEnabled(true);
                    }
                }else{
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    private class Retrieve_Coordinates extends AsyncTask<String, String, String> {
        String response = null;
        HttpURLConnection hcon;
        URL url;

        @Override

        protected void onPreExecute() {
            pd.setMessage("Retrieving coordinates...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override

        protected String doInBackground(String... params) {
            try {
                url = new URL("http://192.168.43.156/webapp/GRetrieve.php"); //create url to connect to Register.php script
            } catch (MalformedURLException e) {

            }
            try {
                hcon = (HttpURLConnection) url.openConnection(); //opens url connections and pass to hcon
                hcon.setConnectTimeout(10000); //set connection timeout in ms
                hcon.setReadTimeout(15000); //set read timeout
                hcon.setRequestMethod("POST");
                hcon.setDoOutput(true); // allows to send data
                hcon.setDoInput(true);

                //append parameters and pass to Builder
                Uri.Builder builder = new Uri.Builder();
                        //.appendQueryParameter("officer", params[0]);

                String data = builder.build().getEncodedQuery();//encodes  appended parameters and pass to string data

                //sends data to server(Apache)
                OutputStream out = hcon.getOutputStream();//writes data through hcon
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));//encodes output to utf-8

                bw.write(data);
                bw.flush(); //frees buffered writer
                bw.close();//closes buffered writer
                hcon.connect();//reconnects hcon
            } catch (Exception e) {

            }


            try {
                //reads responses from the server
                int responsecode = hcon.getResponseCode(); //gets response code if connection is ok
                if (responsecode == HttpURLConnection.HTTP_OK) {

                    InputStream in = hcon.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    StringBuilder sb = new StringBuilder();
                    String input = null;

                    while ((input = br.readLine()) != null) { //reads response from the server and pass to string input
                        sb.append(input); // puts responses into one string

                    }
                    response = sb.toString(); //converts appended data to string
                }
            } catch (Exception e) {
            }


            return response;

        }

        @Override
        protected void onPostExecute(String feedback) {

            pd.dismiss();
            //..Toast.makeText(Login.this, feedback, Toast.LENGTH_SHORT).show();
            if (feedback.equals("All reports resolved")) {

                Toast.makeText(Retrieve_Map.this, feedback, Toast.LENGTH_LONG).show();
            } else {

                String report = null, latitude = null, longitude = null; //initializes variables
                String activity = null, userName = null;

                try {

                    JSONArray coordinates = new JSONArray(feedback);

                    for (int i = 0; i < coordinates.length(); i++) {

                        JSONObject coordinate = coordinates.getJSONObject(i);

                        activity = coordinate.getString("activity");
                        userName = coordinate.getString("userName");
                        //report = coordinate.getString("report");

                        String details = "activity:" + activity + "\nuserName: +\n" + userName;

                        //Toast.makeText(Reports_On_M.this, details, Toast.LENGTH_SHORT).show();

                        try {


                            latitude = coordinate.getString("latitude");
                            double lat = Double.valueOf(latitude);

                            longitude = coordinate.getString("longitude");
                            double longt = Double.valueOf(longitude);


                            //add in markers to reported location and move the camera
                            LatLng areas = new LatLng(lat, longt);
                            mMap.addMarker(new MarkerOptions().position(areas).title(activity).snippet("userName:" + userName + "\nactivity:" + activity));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(areas));

                            //Add alert dialog to dispaly details
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(final Marker marker) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(Retrieve_Map.this);
                                    builder.setCancelable(false);
                                    builder.setTitle(marker.getTitle());
                                    builder.setMessage(marker.getSnippet());
                                    //Add  a positive button and set Button to listen to clicks
                                    builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {

                                            //call reporter
                                            Intent call = new Intent(Intent.ACTION_DIAL);

                                            //converts title(phone number) using parse to phone number
                                            call.setData(Uri.parse("tel:" + marker.getTitle()));
                                            startActivity(call);


                                        }
                                    })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int which) {
                                                    dialogInterface.dismiss();
                                                }
                                            });


                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    return false;
                                }

                            });

                        } catch (Exception e) {
                        }
                    }
                } catch (JSONException e) {
                }
            }
        }
    }
    public void mapType (View view){
        if(mMap.getMapType()== GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else{
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

}
