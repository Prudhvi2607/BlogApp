package com.praneeth.studio.blogapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.praneeth.studio.blogapp.R;

import java.util.HashMap;
import java.util.Map;

public class addPost extends AppCompatActivity {

    private ImageButton imageButton;
    private Button post;
    private TextView title;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private Uri imageUri;
    private FirebaseUser user;
    private static final int  galcode =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        auth =FirebaseAuth.getInstance();

        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BLOG");
        imageButton = findViewById(R.id.imageButton);
        title=findViewById(R.id.editText);
        post =findViewById(R.id.button2);
        storageReference = FirebaseStorage.getInstance().getReference();


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galIntent.setType("image/*");
                startActivityForResult(galIntent,galcode);
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==galcode&&resultCode==RESULT_OK)
        {
            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
        }
    }

    private void postData() {
        final String mtitle = title.getText().toString().trim();
        if(!TextUtils.isEmpty(mtitle)&&imageUri!=null)
        {
            StorageReference path = storageReference.child("BlogImages").child(imageUri.getLastPathSegment());


            //uploading image to firebasestorage
            path.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri firebaseURL = taskSnapshot.getDownloadUrl();
                    DatabaseReference newpost = databaseReference.push();
                    Map<String,String> dataToAdd = new HashMap<>();

                    dataToAdd.put("title",mtitle);
                    dataToAdd.put("userid",user.getUid());
                    dataToAdd.put("timestamp",String.valueOf(java.lang.System.currentTimeMillis()));
                    dataToAdd.put("image",String.valueOf(firebaseURL));
                    newpost.setValue(dataToAdd);

                    startActivity(new Intent(addPost.this,PostActivity.class));

                }
            });

        }

    }
}
