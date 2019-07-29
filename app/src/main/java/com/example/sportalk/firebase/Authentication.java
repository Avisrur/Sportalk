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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Authentication {

    DatabaseReference reference;

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
                            reference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
                            registerToDatabase(reference,currentUser,email,username);
                            Intent registerIntent = new Intent(registerActivity, LoginActivity.class);
                            registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            registerActivity.startActivity(registerIntent);
                        } else {
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
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(loginActivity, "Authentication failed.",
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

    public void registerToDatabase(DatabaseReference reference, FirebaseUser currentUser, String email, String username){
        Map userToRegister = new HashMap<String,String>();
        userToRegister.put("id",currentUser.getUid());
        userToRegister.put("email",email);
        userToRegister.put("username", username);
        userToRegister.put("profileImage", "https://firebasestorage.googleapis.com/v0/b/sportalk-66484.appspot.com/o/username.png?alt=media&token=c3e59b7d-fc48-4ef9-99be-5510a6084f11");
        reference.setValue(userToRegister).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    return;
                }
            }
        });
    }
}
