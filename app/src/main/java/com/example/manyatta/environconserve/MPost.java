package com.example.manyatta.environconserve;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MPost extends AppCompatActivity implements View.OnClickListener {
    public static final String ET_LATITUDE = "com.example.manyatta.environconserve.ET_LATITUDE";
    public static final String ET_LONGITUDE = "com.example.manyatta.environconserve.ET_LONGITUDE";
    public static final String ET_USERNAME = "com.example.manyatta.environconserve.ET_USERNAME";

    private static final String UPLOAD_URL = "http://192.168.43.140/webapp/MSendReport.php";
    private static final int IMAGE_REQUEST_CODE = 3;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private ImageView imageView;
    private TextView tvPath;

    //private TextView tvLatitude;
    //private TextView tvLongitude;

    private EditText et_activity,et_county;

    private Button btnUpload;
    private Bitmap bitmap;
    private Uri filePath;
    private String latitude;
    private String longitude;

    Spinner counties;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpost);
        imageView = (ImageView) findViewById(R.id.image);
        tvPath = (TextView) findViewById(R.id.path);
        btnUpload = (Button) findViewById(R.id.btnUpload);

        et_activity = (EditText) findViewById(R.id.editTextActivity);



        et_activity = (EditText) findViewById(R.id.editTextActivity);

        requestStoragePermission();

        imageView.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

        //Getting the intent data and putting it into text view
        Intent intent = getIntent();

        latitude = intent.getStringExtra(ET_LATITUDE);
        longitude = intent.getStringExtra(ET_LONGITUDE);

        TextView textView8 = (TextView) findViewById(R.id.textViewLat);
        TextView textView9 = (TextView) findViewById(R.id.textViewLong);

        textView8.setText(latitude);
        textView9.setText(longitude);

        String username = intent.getStringExtra(ET_USERNAME);
        TextView textView5 = (TextView) findViewById(R.id.textView7);
        textView5.setText(username);

        counties = (Spinner)findViewById(R.id.sCounty);
        List<String> County = new ArrayList<>();
        County.add("Lamu");
        County.add("Mombasa");
        County.add("Nairobi");
        County.add("Kisumu");
        County.add("Narok");
        County.add("Bungoma");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,County);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        counties.setAdapter(arrayAdapter);

    }



    @Override
    public void onClick(View view) {
        if(view == imageView){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);
        }else if(view == btnUpload){
            uploadMultipart();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                tvPath.setText("Path: ". concat(getPath(filePath)));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadMultipart() {

        Intent intent = getIntent();

        String county = counties.getSelectedItem().toString();

        latitude = intent.getStringExtra(ET_LATITUDE);
        longitude = intent.getStringExtra(ET_LONGITUDE);

        TextView textView8 = (TextView) findViewById(R.id.textViewLat);
        TextView textView9 = (TextView) findViewById(R.id.textViewLong);

        textView8.setText(latitude);
        textView9.setText(longitude);

        String activity = et_activity.getText().toString();


        //String caption = etCaption.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("activity", activity) //Adding text parameter to the request
                    .addParameter("county", county) //Adding text parameter to the request
                    .addParameter("latitude", latitude) //Adding text parameter to the request
                    .addParameter("longitude", longitude) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                Intent in = new Intent(MPost.this, MainActivity.class);
                startActivity(in);
                break;
            case R.id.menuHome:
                TextView textView6 = (TextView) findViewById(R.id.textView7);
                String username = textView6.getText().toString();
                Intent intent1 = new Intent(MPost.this,userAddData.class);
                intent1.putExtra(ET_USERNAME,username);
                startActivity(intent1);
                break;
            case R.id.menuImage:
                TextView textView = (TextView) findViewById(R.id.textView7);
                String userName = textView.getText().toString();
                Intent intent2 = new Intent(MPost.this,municiple.class);
                intent2.putExtra(ET_USERNAME,userName);
                startActivity(intent2);
                break;
        }
        return true;

    }



}