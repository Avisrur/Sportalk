package com.example.sportalk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportalk.R;
import com.example.sportalk.firebase.Authentication;
import com.example.sportalk.utils.Validator;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button loginButton;
    TextView registerTextView;
    Authentication firebaseAuth;
    Validator validator;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    public LoginActivity() {
        this.firebaseAuth = new Authentication();
        this.validator = new Validator();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        registerTextView = (TextView) findViewById(R.id.registerTxt);

        mAuth = FirebaseAuth.getInstance();

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLogin()) {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.loginFirebase(LoginActivity.this,mAuth,email.getText().toString(),password.getText().toString(),progressBar);
                }
            }
        });
    }

    private boolean validateLogin() {
        String usernameInput = email.getText().toString();
        String passwordInput = password.getText().toString();
        if(validator.validateEmail(usernameInput,email) &&
            validator.validatePassword(passwordInput,password))
            return true;
        return false;
    }
}
