package com.expertwebtech.boomer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.BlogAdapter;
import com.expertwebtech.boomer.adapter.BloggerAdapter;

public class BlogListingActivity extends AppCompatActivity {

    RecyclerView recyclerView1,recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_listing);

        recyclerView1=findViewById(R.id.recyclerview1);
        recyclerView2=findViewById(R.id.recyclerview2);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        BloggerAdapter bloggerAdapter=new BloggerAdapter(getApplicationContext());
        recyclerView1.setAdapter(bloggerAdapter);
        bloggerAdapter.notifyDataSetChanged();


        LinearLayoutManager layoutManager1=new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager1);

        BlogAdapter blogAdapter=new BlogAdapter(getApplicationContext(),1);
        recyclerView2.setAdapter(blogAdapter);
        blogAdapter.notifyDataSetChanged();
    }
}
