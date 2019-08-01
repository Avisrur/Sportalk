package com.example.sportalk.firebase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Storage {

    public StorageReference getPostsStorage(){
        return FirebaseStorage.getInstance().getReference("posts");
    }
}
