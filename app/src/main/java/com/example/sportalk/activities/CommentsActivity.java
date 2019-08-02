package com.example.sportalk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportalk.R;
import com.example.sportalk.adapters.CommentAdapter;
import com.example.sportalk.entities.Comment;
import com.example.sportalk.entities.User;
import com.example.sportalk.firebase.Authentication;
import com.example.sportalk.firebase.Database;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    EditText add_comment;
    ImageView image_profile;
    TextView post;

    String postId;
    String publisherId;

    FirebaseUser firebaseUser;

    Authentication authentication;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,commentList);
        recyclerView.setAdapter(commentAdapter);

        authentication = new Authentication();
        database = new Database();
        add_comment = findViewById(R.id.add_comment);
        post = findViewById(R.id.post);
        image_profile = findViewById(R.id.image_profile);

        firebaseUser = authentication.getCurrentUser();

        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        publisherId = intent.getStringExtra("publisherId");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_comment.getText().toString().equals("")){
                    Toast.makeText(CommentsActivity.this,"You cant post empty comment",Toast.LENGTH_SHORT).show();
                } else {
                    addComment();
                }
            }
        });

        getImage();
        readComment();
    }

    private void addComment() {
        DatabaseReference reference = database.getCommentsByPostId(postId);

        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("comment", add_comment.getText().toString());
        hashMap.put("publisher", firebaseUser.getUid());

        reference.push().setValue(hashMap);
        addNotifications();
        add_comment.setText("");
    }

    private void addNotifications(){
        DatabaseReference reference = database.getNotificationByUserId(publisherId);
        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("userId",firebaseUser.getUid());
        hashMap.put("text", "commented: "+add_comment.getText().toString());
        hashMap.put("postId", postId);
        hashMap.put("isPost",true);

        reference.push().setValue(hashMap);
    }

    private void getImage(){
        DatabaseReference reference = database.getUserById(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getProfileImage()).into(image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readComment(){
        DatabaseReference reference = database.getCommentsByPostId(postId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Comment comment = snapshot.getValue(Comment.class);
                    commentList.add(comment);
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
