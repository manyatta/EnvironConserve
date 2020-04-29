package com.example.manyatta.environconserve;

import android.app.Activity;
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

public class Register extends Activity {
    EditText ET_USER_NAME,ET_USER_PASS;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ET_USER_NAME = (EditText)findViewById(R.id.editTextUsername);
        ET_USER_PASS = (EditText)findViewById(R.id.editTextPassword);

        progressDialog = new ProgressDialog(this);

    }

    public void userReg(View view){

        String username = ET_USER_NAME.getText().toString();
        String password = ET_USER_PASS.getText().toString();

        if (username.length() == 0){
            ET_USER_NAME.setError("Name required!");
        }else if(username.length() < 4 ){
            ET_USER_NAME.setError("Username Too Short, Minimum is 4 Characters!");
        }else if(username.length() > 15 ){
            ET_USER_NAME.setError("Maximum Characters Required is 15!");
        }else if(password.length() == 0){
            ET_USER_PASS.setError("Password required!");
        }else if(password.length() <6){
            ET_USER_PASS.setError("Minimum Characters Required is 6!");
        }else{
            new SignUp().execute(username,password); //execute signup class passing parameters

        }
    }
    private class SignUp extends AsyncTask<String, String, String> {
        String response = null;
        HttpURLConnection hcon;
        URL url;

        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Signing up...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params){
            try{
                url = new URL("http://192.168.43.140/webapp/register.php"); //create url to connect to Register.php script
            }catch(MalformedURLException e){

            }
            try{
                hcon = (HttpURLConnection) url.openConnection(); //opens url connections and pass to hcon
                hcon.setConnectTimeout(10000); //set connection timeout in ms
                hcon.setReadTimeout(15000); //set read timeout
                hcon.setRequestMethod("POST");
                hcon.setDoOutput(true); // allows to send data
                hcon.setDoInput(true);

                //append parameters and pass to Builder
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);


                String data = builder.build().getEncodedQuery();//encodes  appended parameters and pass to string data

                //sends data to server(Apache)
                OutputStream out = hcon.getOutputStream();//writes data through hcon
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));//encodes output to utf-8

                bw.write(data);
                bw.flush(); //frees buffered writer
                bw.close();//closes buffered writer
                hcon.connect();//reconnects hcon

            }catch(Exception e){

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
            }catch (Exception e){}


            return response;

        }
        @Override
        protected void onPostExecute(String feedback){

            progressDialog.dismiss();
            // Toast.makeText(Register.this, feedback, Toast.LENGTH_SHORT).show();

            if (feedback.equals("Registered Succesifully")){
                Intent login = new Intent (Register.this, Login.class);
                startActivity (login);

                ET_USER_NAME.setText("");
                ET_USER_PASS.setText("");

            } else {
                Toast.makeText(Register.this, feedback, Toast.LENGTH_SHORT).show();
            }
        }
    }




}
