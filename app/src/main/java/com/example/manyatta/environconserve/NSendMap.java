package com.example.manyatta.environconserve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NSendMap extends AppCompatActivity {
    public static final String ET_LATITUDE = "com.example.manyatta.environconserve.ET_LATITUDE";
    public static final String ET_LONGITUDE = "com.example.manyatta.environconserve.ET_LONGITUDE";
    public static final String ET_USERNAME = "com.example.manyatta.environconserve.ET_USERNAME";

    private TextView tvLatitude;
    private TextView tvLongitude;
    private TextView user_last;
    private EditText etActivity;
    private Button bSendMap;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsend_map);

        user_last = (TextView) findViewById(R.id.tv_UserLast);
        tvLatitude = (TextView) findViewById(R.id.textViewLat);
        tvLongitude = (TextView) findViewById(R.id.textViewLong);

        etActivity = (EditText) findViewById(R.id.editTextActivity);

        bSendMap = (Button)findViewById(R.id.NSendMap);

        Intent intent = getIntent();
        String userName = intent.getStringExtra(ET_USERNAME);
        String latitude = intent.getStringExtra(ET_LATITUDE);
        String longitude = intent.getStringExtra(ET_LONGITUDE);

        user_last.setText(userName);
        tvLatitude.setText(latitude);
        tvLongitude.setText(longitude);

        progressDialog = new ProgressDialog(NSendMap.this);
    }

    public void SendData(View v) {
        String activity = etActivity.getText().toString();

        Intent intent = getIntent();
        String userName = intent.getStringExtra(ET_USERNAME);
        String latitude = intent.getStringExtra(ET_LATITUDE);
        String longitude = intent.getStringExtra(ET_LONGITUDE);

        //ensures user inputs values
        if(activity.length() == 0 ){
            etActivity.setError("Activity required");
        }else {
            new signIn().execute(activity,userName,latitude,longitude); //execute signIN class passing parameters

        }
    }

    private class signIn extends AsyncTask<String, String, String> {
        String response = null;
        HttpURLConnection httpURLConnection;
        URL url;
        @Override

        protected void onPreExecute(){
            progressDialog.setMessage("Sending...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override

        protected String doInBackground(String... params){
            try{
                url = new URL("http://192.168.43.140/webapp/SendMap.php"); //create url to connect to Register.php script
            }catch(MalformedURLException e){

            }
            try{
                httpURLConnection = (HttpURLConnection) url.openConnection(); //opens url connections and pass to hcon
                httpURLConnection.setConnectTimeout(10000); //set connection timeout in ms
                httpURLConnection.setReadTimeout(15000); //set read timeout
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true); // allows to send data
                httpURLConnection.setDoInput(true);

                //append parameters and pass to Builder
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("activity", params[0])
                        .appendQueryParameter("userName", params[1])
                        .appendQueryParameter("latitude", params[2])
                        .appendQueryParameter("longitude", params[3]);

                String data = builder.build().getEncodedQuery();//encodes  appended parameters and pass to string data

                //sends data to server(Apache)
                OutputStream out =  httpURLConnection.getOutputStream();//writes data through hcon
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));//encodes output to utf-8

                bw.write(data);
                bw.flush(); //frees buffered writer
                bw.close();//closes buffered writer
                httpURLConnection.connect();//reconnects hcon
            }catch(Exception e){

            }


            try {
                //reads responses from the server
                int responseCode =  httpURLConnection.getResponseCode(); //gets response code if connection is ok
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream in =  httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    StringBuilder sb = new StringBuilder();
                    String input = null;

                    while ((input = br.readLine()) != null) { //reads response from the server and pass to string input
                        sb.append(input); // puts responses into one string

                    }
                    response = sb.toString(); //converts appended data to string
                }
            }catch (Exception e){}


            return response;

        }
        @Override
        protected void onPostExecute(String feedback){

            progressDialog.dismiss();
            //..Toast.makeText(Login.this, feedback, Toast.LENGTH_SHORT).show();

            if (feedback.equals("Success")){


                Toast.makeText(NSendMap.this, feedback, Toast.LENGTH_SHORT).show();


                etActivity.setText("");
                user_last.setText("");
                tvLatitude.setText("");
                tvLongitude.setText("");


            } else {
                Toast.makeText(NSendMap.this, feedback, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
