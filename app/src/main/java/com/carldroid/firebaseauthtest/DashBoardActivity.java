package com.carldroid.firebaseauthtest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DashBoardActivity extends AppCompatActivity {

    private TextView nameTv, balanceTv, professionTv, phoneTv, emailTv, tabsJobsTv, tabAppliedJobsTv;
    private RecyclerView AllJobsRv;

    private ImageButton logoutBtn, editProfileBtn;
    private ImageView profileIv;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        nameTv = findViewById(R.id.nameTv);
        professionTv = findViewById(R.id.professionTv);
        emailTv = findViewById(R.id.emailTv);
        tabsJobsTv = findViewById(R.id.tabsJobsTv);
        tabAppliedJobsTv = findViewById(R.id.tabAppliedJobsTv);
        AllJobsRv = findViewById(R.id.AllJobsRv);
        balanceTv = findViewById(R.id.balanceTv);
        logoutBtn = findViewById(R.id.logoutBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        profileIv = findViewById(R.id.profileIv);
        phoneTv = findViewById(R.id.phoneTv);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        progressDialog = new ProgressDialog(this);
        checkUser();


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open edit profile activity
                startActivity(new Intent(DashBoardActivity.this, EditProfileActivity.class));
                finish();
            }
        });
    }

    private void checkUser() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));

        } else {
            loadMyInfo();
        }

    }

    private void loadMyInfo() {

        firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = "" + document.getString("name");
                                String email = "" + document.getString("email");
                                String phone = "" + document.getString("phone");
                                String profession = "" + document.getString("profession");
                                String balance = "" + document.getString("balance");

                                nameTv.setText(name);
                                emailTv.setText(email);
                                phoneTv.setText(phone);
                                professionTv.setText(profession);
                                balanceTv.setText(balance);

                            }
                        } else {
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(DashBoardActivity.this, "Error getting documents " + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(DashBoardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
