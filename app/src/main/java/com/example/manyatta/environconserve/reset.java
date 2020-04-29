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

public class reset extends AppCompatActivity {

    EditText ET_USERNAME, ET_PASS, ET_CONFIRM;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        ET_USERNAME = (EditText) findViewById(R.id.editTextUsername);
        ET_PASS = (EditText) findViewById(R.id.editTextPassword);
        ET_CONFIRM = (EditText) findViewById(R.id.editTextCPassword);

        progressDialog = new ProgressDialog(reset.this);


    }

    public void userReset(View v) {
        String username = ET_USERNAME.getText().toString();
        String password = ET_PASS.getText().toString();
        String cpassword = ET_CONFIRM.getText().toString();

        //ensures user inputs values
        if(username.length() == 0 ){
            ET_USERNAME.setError("Username required!");
        }else if(username.length() < 4 ){
            ET_USERNAME.setError("Username Too Short, Minimum is 4 Characters!");
        }else if(username.length() > 15 ){
            ET_USERNAME.setError("Maximum Characters Required is 15!");
        }else if(password.length() == 0){
            ET_PASS.setError("Password Required!");
        }else if(password.length() <6){
            ET_PASS.setError("Minimum Characters Required is 6!");
        }else if(cpassword.length() == 0){
            ET_CONFIRM.setError("Confirm Password Required!");
        }else if(cpassword.length() <6){
            ET_CONFIRM.setError("Minimum Characters Required is 6!");
        }else {
            new reset.signIn().execute(username,password,cpassword); //execute signIN class passing parameters

        }
    }

    private class signIn extends AsyncTask<String, String, String> {
        String response = null;
        HttpURLConnection httpURLConnection;
        URL url;
        @Override

        protected void onPreExecute(){
            progressDialog.setMessage("Resetting...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override

        protected String doInBackground(String... params){
            try{
                url = new URL("http://192.168.43.140/webapp/reset.php"); //create url to connect to Register.php script
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
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1])
                        .appendQueryParameter("cpassword", params[2]);

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

            if (feedback.equals("Your password has been reset")){



                Intent intent = new Intent(reset.this,Login.class);
                startActivity(intent);


                ET_USERNAME.setText("");
                ET_PASS.setText("");
                ET_CONFIRM.setText("");


            } else {
                Toast.makeText(reset.this, feedback, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
