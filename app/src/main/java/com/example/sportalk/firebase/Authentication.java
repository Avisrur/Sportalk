package com.example.sportalk.firebase;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sportalk.activities.HomeActivity;
import com.example.sportalk.activities.LoginActivity;
import com.example.sportalk.activities.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Authentication {

    Database database;

    public Authentication() {
        this.database = new Database();
    }

    public void registerFirebase(final RegisterActivity registerActivity, final FirebaseAuth mAuth, final String email, String password, final String username, final ProgressBar progressBar){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(registerActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Log.d("register","register");
                            Toast.makeText(registerActivity, "Registered successfully.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            database.registerUsersToDatabase(currentUser,email,username);
                            Intent registerIntent = new Intent(registerActivity, LoginActivity.class);
                            registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            registerActivity.startActivity(registerIntent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(registerActivity, "User already registered.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(registerActivity, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]

    }

    public void loginFirebase(final LoginActivity loginActivity, final FirebaseAuth mAuth, String email, String password, final ProgressBar progressBar){
        Log.d("login","login");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("login", String.valueOf(user));
                            Intent registerIntent = new Intent(loginActivity, HomeActivity.class);
                            registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            loginActivity.startActivity(registerIntent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(loginActivity, "User is not registered.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Log.d(TAG,"login failed");
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


}
