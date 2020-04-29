package com.example.manyatta.environconserve;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class userAddData extends AppCompatActivity {

    //private Button neema;
    //private Button forestry;
    //private Button municiple;
    Dialog myDialog,myDialog2,myDialog3;
    private CardView nema,kfs,municiple;
    public static final String ET_USERNAME = "com.example.manyatta.environconserve.ET_USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_data);

        /**neema = (Button) findViewById(R.id.btn_neema);
        forestry = (Button) findViewById(R.id.btn_kfm);
        municiple = (Button) findViewById(R.id.btn_municiple);**/
        nema = (CardView) findViewById(R.id.cv);
        kfs= (CardView) findViewById(R.id.cv2);
        municiple = (CardView) findViewById(R.id.cv3);

        myDialog = new Dialog(this);
        myDialog2 = new Dialog(this);
        myDialog3 = new Dialog(this);


        //Getting the intent data and putting it into text view
        Intent intent = getIntent();
        String username = intent.getStringExtra(ET_USERNAME);
        TextView textView5 = (TextView) findViewById(R.id.textView3);
        textView5.setText(username);

        nema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNeema();
            }
        });

        kfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForestry();
            }
        });
        municiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMuniciple();
            }
        });

    }
//pop up xml for nema
    public void ShowPopup1(View v) {
        ImageView txtclose;
        //Button btnFollow;
        myDialog.setContentView(R.layout.nemapopup);
        txtclose = (ImageView) myDialog.findViewById(R.id.txtclose);
        //btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
//pop up xml for kfm
    public void ShowPopup2(View v) {
        ImageView txtclose;
        //Button btnFollow;
        myDialog2.setContentView(R.layout.municiplepopup);
        txtclose = (ImageView) myDialog2.findViewById(R.id.txtclose);
        //btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
            }
        });
        myDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog2.show();
    }

    //pop up xml for kfm
    public void ShowPopup3(View v) {
        ImageView txtclose;
        //Button btnFollow;
        myDialog3.setContentView(R.layout.kfmpopup);
        txtclose = (ImageView) myDialog3.findViewById(R.id.txtclose);
        //btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog3.dismiss();
            }
        });
        myDialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog3.show();
    }
//open nema activity
    public void openNeema(){
        //Getting the intent and carrying it to the next activity
        TextView tView = (TextView) findViewById(R.id.textView3);
        String username = tView.getText().toString();
        Intent intent = new Intent(userAddData.this,neema.class);
        intent.putExtra(ET_USERNAME,username);
        startActivity(intent);

    }
    //open kfm activity
    public void openForestry(){

        TextView tView = (TextView) findViewById(R.id.textView3);
        String username = tView.getText().toString();
        Intent intent = new Intent(userAddData.this,kfm.class);
        intent.putExtra(ET_USERNAME,username);
        startActivity(intent);

    }

    //open municiple activity
    public void openMuniciple(){

        TextView tView = (TextView) findViewById(R.id.textView3);
        String username = tView.getText().toString();
        Intent intent = new Intent(userAddData.this,municiple.class);
        intent.putExtra(ET_USERNAME,username);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                Intent in = new Intent(userAddData.this, MainActivity.class);
                startActivity(in);
        }
        return true;

    }
}
