package com.carldroid.firebaseauthtest.Activities;

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

import com.carldroid.firebaseauthtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterFindJob;
import Models.ModelFindJob;

public class FindJobDashBoard extends AppCompatActivity {

    private TextView nameTv, balanceTv, professionTv, phoneTv, emailTv, tabsJobsTv, tabAppliedJobsTv;


    private ImageButton logoutBtn, editProfileBtn;
    private ImageView profileIv;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ProgressDialog progressDialog;

    private RecyclerView recyclerView;
    private AdapterFindJob adapterFindJob;

    private ListenerRegistration firestoreListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findjob_dashboard);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        nameTv = findViewById(R.id.nameTv);
        professionTv = findViewById(R.id.professionTv);
        emailTv = findViewById(R.id.emailTv);
        tabsJobsTv = findViewById(R.id.tabsJobsTv);
        tabAppliedJobsTv = findViewById(R.id.tabAppliedJobsTv);
        recyclerView = findViewById(R.id.allJobsRv);
        balanceTv = findViewById(R.id.balanceTv);
        logoutBtn = findViewById(R.id.logoutBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        profileIv = findViewById(R.id.profileIv);
        phoneTv = findViewById(R.id.phoneTv);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        progressDialog = new ProgressDialog(this);
        checkUser();

        loadPostedJobs();

        firestoreListener = firebaseFirestore.collection("Find_Job_Users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(FindJobDashBoard.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<ModelFindJob> findJobList = new ArrayList<>();
                        for (DocumentSnapshot dc : documentSnapshots) {
                            ModelFindJob modelFindJob = dc.toObject(ModelFindJob.class);
                            modelFindJob.setId(dc.getId());
                            findJobList.add(modelFindJob);
                        }

                        adapterFindJob = new AdapterFindJob(getApplicationContext(), findJobList, firebaseFirestore);
                        recyclerView.setAdapter(adapterFindJob);
                    }
                });


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
                startActivity(new Intent(FindJobDashBoard.this, EditProfileActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        firestoreListener.remove();
    }

    private void checkUser() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(FindJobDashBoard.this, LoginActivity.class));

        } else {
            loadMyInfo();
        }

    }

    private void loadMyInfo() {

        firebaseFirestore.collection("Find_Job_Users")
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

                                loadPostedJobs();

                            }
                        } else {
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(FindJobDashBoard.this, "Error getting documents " + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(FindJobDashBoard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPostedJobs() {

        firebaseFirestore.collection("Post_Job_Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            List<ModelFindJob> findJobList = new ArrayList<>();
                            for (DocumentSnapshot dc : task.getResult()) {
                                ModelFindJob modelFindJob = dc.toObject(ModelFindJob.class);
                                modelFindJob.setId(dc.getId());
                                findJobList.add(modelFindJob);
                            }

                            adapterFindJob = new AdapterFindJob(getApplicationContext(), findJobList, firebaseFirestore);
                            recyclerView.setAdapter(adapterFindJob);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }


}
