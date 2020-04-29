package com.example.manyatta.environconserve;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    private Button user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (Button) findViewById(R.id.user);



        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void help(View view){
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

}





