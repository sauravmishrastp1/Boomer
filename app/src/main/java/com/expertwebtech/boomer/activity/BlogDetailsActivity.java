package com.expertwebtech.boomer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.BlogAdapter;

public class BlogDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        recyclerView=findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        BlogAdapter blogAdapter=new BlogAdapter(getApplicationContext(),2);
        recyclerView.setAdapter(blogAdapter);
    }
}
