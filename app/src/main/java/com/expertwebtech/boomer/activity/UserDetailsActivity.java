package com.expertwebtech.boomer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.expertwebtech.boomer.R;
import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity {

    Button aboutMorebtn;
    Bundle bundle;
    String name ,image,id;
    private ImageView profile_img;
    private TextView name_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        aboutMorebtn=findViewById(R.id.aboutMorebtn);
        profile_img = findViewById(R.id.image);
        name_txt = findViewById(R.id.name);


        bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
            name = bundle.getString("name");
            id = bundle.getString("id");
            image =  bundle.getString("img");
            name_txt.setText(name);
            Picasso.with(getApplicationContext()).load(image).placeholder(R.drawable.user_placeholder).into(profile_img);

        }
        aboutMorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailsActivity.this,PlansActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });

        
    }
}
