package com.example.sportalk.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sportalk.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    int counter=0;
    public static int SPLASH_TIME_OUT= 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent =  new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
//        database = FirebaseDatabase.getInstance();

//        Button addFB = findViewById(R.id.addFB);
//        addFB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myRef = database.getReference("message"+counter);
//                myRef.setValue("Hello"+counter);
//                counter+=1;
//            }
//        });
    }
}
