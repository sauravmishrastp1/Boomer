package com.expertwebtech.boomer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.PlanAdapter;
import com.expertwebtech.boomer.pojo.Plans;

import java.util.ArrayList;
import java.util.List;

public class PlansActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    private List<Plans> plansList=new ArrayList<>();
    View toolbar;
    Bundle bundle;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        recyclerView=findViewById(R.id.recyclerview);
        toolbar=findViewById(R.id.toolbar);
        bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
           id = bundle.getString("id");
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        plansList.add(new Plans(id,"300","Continue to your favourite",R.drawable.plan1,"365","#6EA2FF"));

        plansList.add(new Plans(id,"300","Continue to your favourite",R.drawable.plan1,"365","#6EA2FF"));

        plansList.add(new Plans(id,"300","Continue to your favourite",R.drawable.plan1,"365","#6EA2FF"));

        plansList.add(new Plans(id,"300","Continue to your favourite",R.drawable.plan1,"365","#6EA2FF"));

        plansList.add(new Plans(id,"300","Continue to your favourite",R.drawable.plan1,"365","#6EA2FF"));



        PlanAdapter planAdapter=new PlanAdapter(plansList,PlansActivity.this);
        recyclerView.setAdapter(planAdapter);
        planAdapter.notifyDataSetChanged();

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
