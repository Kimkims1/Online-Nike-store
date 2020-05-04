package com.carldroid.firebaseauthtest.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.Date;
import java.util.HashMap;

public class PostJobUser extends AppCompatActivity {

    private TextView findJobTv, have_accountTv;
    private EditText nameEt, emailEt, phoneEt, jobTypeEt, budgetEt, addressEt, passwordEt;

    private Button registerBtn;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_job_user);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        have_accountTv = findViewById(R.id.have_accountTv);
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        phoneEt = findViewById(R.id.phoneEt);
        jobTypeEt = findViewById(R.id.jobTypeEt);
        budgetEt = findViewById(R.id.budgetEt);
        addressEt = findViewById(R.id.addressEt);
        passwordEt = findViewById(R.id.passwordEt);
        registerBtn = findViewById(R.id.registerBtn);
        findJobTv = findViewById(R.id.findJobTv);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Post_Job_Users");

        progressDialog = new ProgressDialog(this);

        have_accountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostJobUser.this,LoginActivity.class));
                finish();
            }
        });

        findJobTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostJobUser.this, FindJobUser.class));
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
                            String phoneNumber = phoneEt.getText().toString().trim();
                            String jobType = jobTypeEt.getText().toString().trim();
                            String budget = budgetEt.getText().toString().trim();
                            String address = addressEt.getText().toString().trim();
                            //final Date date = new Date();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name",name);
                            hashMap.put("phoneNumber",phoneNumber);
                            hashMap.put("jobType",jobType);
                            hashMap.put("budget",budget);
                            hashMap.put("address",address);
                            //hashMap.put("date",date);

                            collectionReference.add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    startActivity(new Intent(PostJobUser.this, PostJobDashboard.class));
                                    finish();

                                    Toast.makeText(PostJobUser.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(PostJobUser.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostJobUser.this, "Authentication failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
