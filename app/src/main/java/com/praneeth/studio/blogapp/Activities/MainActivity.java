package com.praneeth.studio.blogapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.praneeth.studio.blogapp.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
private FirebaseAuth mAuth;
private FirebaseAuth.AuthStateListener authStateListener;
FirebaseUser user;
EditText Email;
EditText Password;
Button login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user =firebaseAuth.getCurrentUser();
                if(user!=null) {

                    Toast.makeText(MainActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,PostActivity.class);
                    startActivity(intent);
                    finish();
                }
                    else Toast.makeText(MainActivity.this, "Not signed In", Toast.LENGTH_SHORT).show();

            }
        };

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(MainActivity.this, "Successfully created.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                 else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });



            }
        });






        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();

                if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)) {

                    login(email,password);

                }






            }
        });





    }

    private void login(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent intent = new Intent(MainActivity.this,PostActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.signout) mAuth.signOut();
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null) mAuth.removeAuthStateListener(authStateListener);
    }
}
