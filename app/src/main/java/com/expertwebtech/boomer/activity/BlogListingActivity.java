package com.expertwebtech.boomer.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.BlogAdapter;
import com.expertwebtech.boomer.adapter.BloggerAdapter;
import com.expertwebtech.boomer.adapter.HomeAdapter;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.BlogData;
import com.expertwebtech.boomer.pojo.Home;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BlogListingActivity extends AppCompatActivity {

    RecyclerView recyclerView1,recyclerView2;
    private ArrayList<BlogData>blogData = new ArrayList<>();
    Bundle bundle;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_listing);

        bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
            id = bundle.getString("id");
        }

        recyclerView1=findViewById(R.id.recyclerview1);
        recyclerView2=findViewById(R.id.recyclerview2);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        BloggerAdapter bloggerAdapter=new BloggerAdapter(getApplicationContext());
        recyclerView1.setAdapter(bloggerAdapter);
        bloggerAdapter.notifyDataSetChanged();
        getblogdata();



    }
    private void getblogdata()
    {

        final KProgressHUD progressDialog = KProgressHUD.create(BlogListingActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        String url = "http://xpertwebtech.in/bloom/public/api/blog/"+id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status_code");

                            if (status.equals("200")) {

                                JSONArray dataArray=obj.getJSONArray("data");

                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                                recyclerView2.setLayoutManager(layoutManager);

                                blogData.clear();

                                for (int i=0;i<dataArray.length();i++)
                                {
                                    JSONObject object=dataArray.getJSONObject(i);

                                    String id=object.getString("user_id");
                                    String name=object.getString("name");
                                    String image=object.getString("image");
                                    String subject=object.getString("subject");
                                    String discription=object.getString("discription");


                                    blogData.add(new BlogData(id,name,subject,discription,Url.IMAGE_BASE_URL+image));
                                }

                                LinearLayoutManager layoutManager1=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                recyclerView2.setLayoutManager(layoutManager1);

                                BlogAdapter blogAdapter=new BlogAdapter(getApplicationContext(),blogData,1);
                                recyclerView2.setAdapter(blogAdapter);
                                blogAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();



                            } else {

                                Toast.makeText(getApplicationContext(), "There is no data", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                       progressDialog.dismiss();
                    }
                }) {


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

}
