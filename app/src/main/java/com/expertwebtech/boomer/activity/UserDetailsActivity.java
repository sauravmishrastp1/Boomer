package com.expertwebtech.boomer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.expertwebtech.boomer.R;

public class UserDetailsActivity extends AppCompatActivity {

    Button aboutMorebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        aboutMorebtn=findViewById(R.id.aboutMorebtn);

        aboutMorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserDetailsActivity.this,PlansActivity.class));
            }
        });
    }
}
