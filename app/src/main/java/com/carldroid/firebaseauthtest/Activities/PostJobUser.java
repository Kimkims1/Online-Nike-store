package com.carldroid.firebaseauthtest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.carldroid.firebaseauthtest.R;

public class PostJobUser extends AppCompatActivity {

    private TextView findJobTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_job_user);


        findJobTv = findViewById(R.id.findJobTv);

        findJobTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostJobUser.this, FindJobUser.class));
                finish();
            }
        });
    }
}
