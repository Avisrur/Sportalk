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

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText password;
    Button registerBtn;
    TextView alreadyRegisteredTextView;
    ProgressBar progressBar;
    Authentication firebaseAuth;
    Validator validator;
    private FirebaseAuth mAuth;

    public RegisterActivity() {
        this.firebaseAuth = new Authentication();
        this.validator = new Validator();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        alreadyRegisteredTextView = (TextView) findViewById(R.id.already_registered);

        mAuth = FirebaseAuth.getInstance();

        alreadyRegisteredTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateRegister()){
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.registerFirebase(RegisterActivity.this,mAuth,email.getText().toString(),password.getText().toString(),username.getText().toString(),progressBar);
                }
            }
        });

    }

    private boolean validateRegister() {
        String usernameInput = username.getText().toString();
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();

        if (validator.validatePassword(passwordInput, password) &&
                validator.validateEmail(emailInput, email) &&
                    validator.validateField(usernameInput,username)) return true;

        return false;
    }


}
