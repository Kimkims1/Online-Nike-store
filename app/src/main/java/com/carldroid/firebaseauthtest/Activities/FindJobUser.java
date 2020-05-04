package com.carldroid.firebaseauthtest.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.carldroid.firebaseauthtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class FindJobUser extends AppCompatActivity {

    private EditText emailEt, passwordEt,nameEt,professionEt;
    private Button registerBtn;
    private TextView have_accountTv,postJobTv;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_job_user);

        emailEt = findViewById(R.id.emailEt);
        professionEt = findViewById(R.id.professionEt);
        passwordEt = findViewById(R.id.passwordEt);
        registerBtn = findViewById(R.id.registerBtn);
        have_accountTv = findViewById(R.id.have_accountTv);
        postJobTv = findViewById(R.id.postJobTv);
        nameEt = findViewById(R.id.nameEt);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Find_Job_Users");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..Please wait");
        progressDialog.setCanceledOnTouchOutside(true);


        have_accountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindJobUser.this, LoginActivity.class));
                finish();
            }
        });

        postJobTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindJobUser.this, PostJobUser.class));
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = emailEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEt.setError("Invalid email!");
                    emailEt.setFocusable(true);
                } else if (passwordEt.length() < 8) {
                    passwordEt.setError("Password length should be at least 8 characters or more");
                    passwordEt.setFocusable(true);
                } else {
                    registerUser(email, password);
                }


            }
        });

    }

    private void registerUser(String email, String password) {

        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String email = firebaseUser.getEmail();
                            String uid = firebaseUser.getUid();
                            String name = nameEt.getText().toString().trim();
                            String profession = professionEt.getText().toString().trim();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name",name);
                            hashMap.put("profession",profession);

                            collectionReference.add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    startActivity(new Intent(FindJobUser.this, FindJobDashBoard.class));
                                    finish();

                                    Toast.makeText(FindJobUser.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(FindJobUser.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FindJobUser.this, "Authentication failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }
}
