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
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.BlogData;
import com.expertwebtech.boomer.pojo.Home;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BlogListingActivity extends AppCompatActivity {

    RecyclerView recyclerView1,recyclerView2;
    private ArrayList<BlogData>blogData = new ArrayList<>();
    Bundle bundle;
    String id;
    private ArrayList<Home> homeList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_listing);
        recyclerView1=findViewById(R.id.recyclerview1);
        recyclerView2=findViewById(R.id.recyclerview2);
        bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
            id = bundle.getString("id");
        }




        getblogdata();
        getUsers();



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


                                    blogData.add(new BlogData(id,name,subject,discription,Url.IMAGE_BASE_URL+image,Url.IMAGE_BASE_URL+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getImage(),SharedPrefManager.getInstance(getApplicationContext()).getUser().getName()));
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
    private void getUsers()
    {

        final KProgressHUD progressDialog = KProgressHUD.create(BlogListingActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.HOME_DATA,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status_code");

                            if (status.equals("200")) {

                                JSONArray dataArray=obj.getJSONArray("data");



                                homeList.clear();

                                for (int i=0;i<dataArray.length();i++)
                                {
                                    JSONObject object=dataArray.getJSONObject(i);

                                    String id=object.getString("id");
                                    String name=object.getString("name");
                                    String image=object.getString("image");
                                    String speciality=object.getString("speciality");
                                    String total_exp=object.getString("total_exp");
                                    String no_of_like=object.getString("no_of_like");
                                    String no_of_share=object.getString("no_of_share");
                                    String email = object.getString("email");
                                    String phone_no = object.getString("phone");
                                    String location = object.getString("location");
                                    String date_of_bith = object.getString("dob");
                                    String address = object.getString("address");
                                    String  folloerrs = object.getString("no_of_followers");
                                    String posts = object.getString("no_of_post");
                                    String flowing = object.getString("no_of_following");
                                    // String id=object.getString("id");

                                    homeList.add(new Home(id,name,Url.IMAGE_BASE_URL+image,speciality,total_exp,no_of_like,"12",no_of_share,email,phone_no,location,date_of_bith,address,folloerrs,posts));
                                }

                                LinearLayoutManager layoutManager2=new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
                                recyclerView1.setLayoutManager(layoutManager2);

                                BloggerAdapter bloggerAdapter=new BloggerAdapter(BlogListingActivity.this,homeList);
                                recyclerView1.setAdapter(bloggerAdapter);
                                bloggerAdapter.notifyDataSetChanged();
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
