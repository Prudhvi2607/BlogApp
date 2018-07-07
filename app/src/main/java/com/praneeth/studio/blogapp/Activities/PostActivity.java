package com.praneeth.studio.blogapp.Activities;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.praneeth.studio.blogapp.Data.recycleAdapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.praneeth.studio.blogapp.Model.Blog;
import com.praneeth.studio.blogapp.R;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private recycleAdapter recycleAdapter;
    private List<Blog> blogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BLOG");
        blogList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Blog blog = dataSnapshot.getValue(Blog.class);
                blogList.add(blog);



                recycleAdapter = new recycleAdapter(PostActivity.this,blogList);
                recyclerView.setAdapter(recycleAdapter);
                recycleAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.signout:
                if(auth!=null&&user!=null) {
                    auth.signOut();
                    Intent signintent = new Intent(PostActivity.this,MainActivity.class);
                    startActivity(signintent);
                    finish();
                }
                break;
            case R.id.add_post:
                if(auth!=null&&user!=null)
                {
                    Intent intent = new Intent(PostActivity.this,addPost.class);
                    startActivity(intent);
                }

        }

        if(item.getItemId()==R.id.signout) auth.signOut();
        else if(item.getItemId()==R.id.add_post)
        {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
