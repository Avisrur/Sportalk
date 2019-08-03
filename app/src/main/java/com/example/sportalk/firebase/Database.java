package com.example.sportalk.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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

    public DatabaseReference getUsersDB(){
        return FirebaseDatabase.getInstance().getReference("users");
    }

    public Query queryUsernameInUsersDB(String s){
        return FirebaseDatabase.getInstance().getReference("users").orderByChild("username").startAt(s).endAt(s+"\uf8ff");
    }

    public void setFollow(String userId, String userIdToFollow){
        FirebaseDatabase.getInstance().getReference().child("follow").child(userId)
                .child("following").child(userIdToFollow).setValue(true);
        FirebaseDatabase.getInstance().getReference().child("follow").child(userIdToFollow)
                .child("followers").child(userId).setValue(true);
    }

    public void setUnfollow(String userId, String userIdToFollow){
        FirebaseDatabase.getInstance().getReference().child("follow").child(userId)
                .child("following").child(userIdToFollow).removeValue();
        FirebaseDatabase.getInstance().getReference().child("follow").child(userIdToFollow)
                .child("followers").child(userId).removeValue();
    }

    public DatabaseReference getUserFollowers(FirebaseUser firebaseUser) {
        return FirebaseDatabase.getInstance().getReference()
                .child("follow").child(firebaseUser.getUid()).child("following");
    }

    public DatabaseReference getPostsDB() {
        return FirebaseDatabase.getInstance().getReference("posts");
    }

    public DatabaseReference getFollowingsByUserId(String currentUserId) {
        return FirebaseDatabase.getInstance().getReference("follow")
                .child(currentUserId)
                .child("following");
    }

    public DatabaseReference getFollowersByUserId(String currentUserId) {
        return FirebaseDatabase.getInstance().getReference("follow")
                .child(currentUserId)
                .child("followers");
    }

    public DatabaseReference getUserById(String userId) {
        return FirebaseDatabase.getInstance().getReference("users").child(userId);
    }

    public DatabaseReference getLikesByPostId(String postId) {
        return FirebaseDatabase.getInstance().getReference("likes").child(postId);
    }

    public DatabaseReference getCommentsByPostId(String postId) {
        return FirebaseDatabase.getInstance().getReference("comments").child(postId);
    }

    public void likePostForUserById(String postId, String uid) {
        FirebaseDatabase.getInstance().getReference().child("likes").child(postId).child(uid).setValue(true);
    }

    public void unlikePostForUserById(String postId, String uid) {
        FirebaseDatabase.getInstance().getReference().child("likes").child(postId).child(uid).removeValue();
    }

    public void savePostForUserId(String uid, String postId) {
        FirebaseDatabase.getInstance().getReference().child("saves").child(uid).child(postId).setValue(true);
    }

    public void unsavePostForUserId(String uid, String postId) {
        FirebaseDatabase.getInstance().getReference().child("saves").child(uid).child(postId).removeValue();
    }

    public DatabaseReference getSavedPostByUserId(String uid) {
        return FirebaseDatabase.getInstance().getReference().child("saves").child(uid);
    }

    public DatabaseReference getPostById(String postId) {
        return FirebaseDatabase.getInstance().getReference("posts").child(postId);
    }

    public DatabaseReference getNotificationByUserId(String uid) {
        return FirebaseDatabase.getInstance().getReference("notifications").child(uid);
    }

    public void deletePostById(String postId) {
        DatabaseReference post = FirebaseDatabase.getInstance().getReference("posts").child(postId);
        post.removeValue();
    }
}
