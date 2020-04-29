package com.example.manyatta.environconserve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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

public class Login extends AppCompatActivity {
    EditText ET_NAME, ET_PASS;
    ProgressDialog progressDialog;
    public static final String ET_USERNAME = "com.example.manyatta.environconserve.ET_USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ET_NAME = (EditText) findViewById(R.id.editTextUsername);
        ET_PASS = (EditText) findViewById(R.id.editTextPassword);
        progressDialog = new ProgressDialog(Login.this);
        TextView textView = (TextView) findViewById(R.id.forgot_pass);

        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openReset();
            }
        });
    }

    public void openReset(){
        Intent intent = new Intent(this, reset.class);
        startActivity(intent);
    }

    public void userLogin(View v) {
        String username = ET_NAME.getText().toString();
        String password = ET_PASS.getText().toString();

        //ensures user inputs values
        if(username.length() == 0 ){
            ET_NAME.setError("Username required!");
        }else if(username.length() < 4 ){
            ET_NAME.setError("Username Too Short, Minimum is 4 Characters!");
        }else if(username.length() > 15 ){
            ET_NAME.setError("Maximum Characters Required is 15!");
        }else if(password.length() == 0){
            ET_PASS.setError("Password Required!");
        }else if(password.length() <6){
            ET_PASS.setError("Minimum Characters Required is 6!");
        }else {
            new signIn().execute(username,password); //execute signIN class passing parameters

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
                url = new URL("http://192.168.43.140/webapp/login.php"); //create url to connect to Register.php script
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

                if (feedback.equals("Login Successfull")){

                EditText userName = (EditText) findViewById(R.id.editTextUsername);
                String username = userName.getText().toString();
                Intent intent = new Intent(Login.this, userAddData.class);
                intent.putExtra(ET_USERNAME, username);
                startActivity(intent);


                ET_NAME.setText("");
                ET_PASS.setText("");


            }else {
                Toast.makeText(Login.this, feedback, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void userReg(View view) {
        startActivity(new Intent(this, Register.class));
    }
    public void userAddData() {
        startActivity(new Intent(this, Register.class));
    }
}



