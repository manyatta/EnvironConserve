package com.example.manyatta.environconserve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class adminLogin extends AppCompatActivity {

    EditText ET_NUM, ET_PASS;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        ET_NUM = (EditText)findViewById(R.id.officeNumber);
        ET_PASS = (EditText)findViewById(R.id.password);

        progressDialog = new ProgressDialog(adminLogin.this);
    }

    public void adminLogin(View v){
        String officeNumber = ET_NUM.getText().toString();
        String password = ET_PASS.getText().toString();

        //ensures user inputs values
        if(officeNumber.length() == 0 ){
            ET_NUM.setError("Username required!");
        }else if(officeNumber.length() < 4){
            ET_NUM.setError("Office Number Too Short!");
        }else if(password.length() == 0){
            ET_PASS.setError("Password Required!");
        }else {
            new signIn().execute(officeNumber,password); //execute signIN class passing parameters

        }
    }
    private class signIn extends AsyncTask<String, String, String> {
        String response = null;
        HttpURLConnection httpURLConnection;
        URL url;
        @Override

        protected void onPreExecute(){
            progressDialog.setMessage("Signing in...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override

        protected String doInBackground(String... params){       
            try{
                url = new URL("http://192.168.43.140/webapp/adminLogin.php"); //create url to connect to Register.php script
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
                        .appendQueryParameter("officeNumber", params[0])
                        .appendQueryParameter("password", params[1]);

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

            if (feedback.equals("Login Succesfull")){

                Intent adminDash = new Intent (adminLogin.this, Retrieve_Map.class);
                startActivity (adminDash);

                ET_NUM.setText("");
                ET_PASS.setText("");


            } else {
                Toast.makeText(adminLogin.this, feedback, Toast.LENGTH_SHORT).show();
                ET_NUM.setText("");
                ET_PASS.setText("");
            }
        }
    }
}
