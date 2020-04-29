package com.example.manyatta.environconserve;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class kfm extends AppCompatActivity {

    public static final String ET_USERNAME = "com.example.manyatta.environconserve.ET_USERNAME";

    String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;
    private Button reports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kfm);

        //Getting the intent data and putting it into text view
        Intent intent = getIntent();
        String username = intent.getStringExtra(ET_USERNAME);
        TextView textView10 = (TextView) findViewById(R.id.textView10);
        textView10.setText(username);

        reports= (Button)findViewById(R.id.btn_report) ;
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMap();
            }
        });
    }

    public void captureImage(View view){
        Intent camersIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        if(camersIntent.resolveActivity(getPackageManager())!=null){
            File imageFile = null;
            try {
                imageFile = getImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(imageFile!=null){
                Uri imageUri = FileProvider.getUriForFile(this,"com.example.android.fileprovider",imageFile);
                camersIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(camersIntent, IMAGE_REQUEST);

            }

        }
    }

    public void displayImage(View view){

    }

    private File getImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyhhdd_hhmmss").format(new Date());
        String imageName = "jpg_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName,".jpg", storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public void getMap(){
        TextView textView10 = (TextView) findViewById(R.id.textView10);
        String username = textView10.getText().toString();
        Intent intent = new Intent(kfm.this,KGetMap.class);
        intent.putExtra(ET_USERNAME,username);
        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                Intent in = new Intent(kfm.this, MainActivity.class);
                startActivity(in);
                break;
            case R.id.menuHome:
                TextView textView6 = (TextView) findViewById(R.id.textView10);
                String username = textView6.getText().toString();
                Intent intent = new Intent(kfm.this,userAddData.class);
                intent.putExtra(ET_USERNAME,username);
                startActivity(intent);
                break;
        }
        return true;

    }


}
