package com.example.sportalk.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sportalk.R;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText password;
    Button loginButton;
    TextView alreadyRegisteredTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginBtn);
        alreadyRegisteredTextView = (TextView) findViewById(R.id.already_registered);

        alreadyRegisteredTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}
