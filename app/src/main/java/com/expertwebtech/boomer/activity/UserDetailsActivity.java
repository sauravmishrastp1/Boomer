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
    String name ,image,id,email,location,addrss,dateofbirth,phone_no,poast,flowers,speciality;
    private ImageView profile_img;
    private TextView name_txt,dateofbittxt,locationttxt,addrsstxt,emailtxt,flowerstxt,posttxt,phonetxt,speclitytxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        speclitytxt = findViewById(R.id.profesion);
        posttxt = findViewById(R.id.post);
        dateofbittxt = findViewById(R.id.date_of_birth);
        locationttxt = findViewById(R.id.location_txt);
        addrsstxt = findViewById(R.id.addresstxt);
        emailtxt = findViewById(R.id.email_id);
        phonetxt = findViewById(R.id.phone_no);
        flowerstxt = findViewById(R.id.flowers);
        aboutMorebtn=findViewById(R.id.aboutMorebtn);
        profile_img = findViewById(R.id.image);
        name_txt = findViewById(R.id.name);


        bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
            name = bundle.getString("name");
            id = bundle.getString("id");
            image =  bundle.getString("img");
            email = bundle.getString("email");
            location = bundle.getString("location");
            addrss = bundle.getString("address");
            phone_no = bundle.getString("phone");
            poast = bundle.getString("post");
            flowers = bundle.getString("flowers");
            dateofbirth= bundle.getString("dob");
            speciality = bundle.getString("speciality");

            name_txt.setText(name);
            speclitytxt.setText(speciality);
            posttxt.setText(poast);
            dateofbittxt.setText(dateofbirth);
            locationttxt.setText(location);
            addrsstxt.setText(addrss);
            emailtxt.setText(email);
            flowerstxt.setText(flowers);
            phonetxt.setText(phone_no);
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
