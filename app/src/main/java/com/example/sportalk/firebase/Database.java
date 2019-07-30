package com.example.sportalk.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Database {
    DatabaseReference reference;

    public void registerUsersToDatabase(FirebaseUser currentUser, String email, String username){
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
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
