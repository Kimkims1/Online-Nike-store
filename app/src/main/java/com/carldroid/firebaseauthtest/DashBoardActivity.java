package com.carldroid.firebaseauthtest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class DashBoardActivity extends AppCompatActivity {

    private TextView nameTv,proffesionTv,emailTv,tabsJobsTv,tabAppliedJobsTv;
    private EditText searchJobEt;
    private RecyclerView AllJobsRv;

    private ImageButton checkBalBtn,logoutBtn,editProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
