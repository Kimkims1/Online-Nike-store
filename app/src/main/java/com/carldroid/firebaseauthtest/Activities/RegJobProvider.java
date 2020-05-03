package com.carldroid.firebaseauthtest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.carldroid.firebaseauthtest.R;

public class RegJobProvider extends AppCompatActivity {

    private TextView regJobSeeker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_job_provider);


        regJobSeeker = findViewById(R.id.regJobSeeker);

        regJobSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegJobProvider.this, RegJobSeeker.class));
                finish();
            }
        });
    }
}
