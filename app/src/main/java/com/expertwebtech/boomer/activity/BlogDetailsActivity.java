package com.expertwebtech.boomer.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.BlogAdapter;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.BlogData;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.skyhope.showmoretextview.ShowMoreTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class BlogDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String title,subject,dis,img,id;
    TextView showMoreTextView;
    ImageView profile_img;
    TextView titletxt,subjecttxt;
    Bundle bundle;
    private ArrayList<BlogData> blogData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        recyclerView=findViewById(R.id.recyclerview);
        titletxt = findViewById(R.id.name);
        subjecttxt = findViewById(R.id.subject_);
        showMoreTextView = findViewById(R.id.text_view_show_more);
        profile_img = findViewById(R.id.imageView);
        bundle = getIntent().getExtras();

        if(!bundle.isEmpty()){
            title = bundle.getString("title");
            subject = bundle.getString("subject");
            dis = bundle.getString("dis");
            img = bundle.getString("img");
            id = bundle.getString("id");

            titletxt.setText(title);
            subjecttxt.setText(subject);
            showMoreTextView.setText(dis);
            Picasso.with(getApplicationContext()).load(img).placeholder(R.drawable.user_placeholder).into(profile_img);

        }
        getblogdata();


    }
    private void getblogdata()
    {

        final KProgressHUD progressDialog = KProgressHUD.create(BlogDetailsActivity.this)
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



                                blogData.clear();

                                for (int i=0;i<dataArray.length();i++)
                                {
                                    JSONObject object=dataArray.getJSONObject(i);

                                    String id=object.getString("id");
                                    String name=object.getString("name");
                                    String image=object.getString("image");
                                    String subject=object.getString("subject");
                                    String discription=object.getString("discription");


                                    blogData.add(new BlogData(id,name,subject,discription, Url.IMAGE_BASE_URL+image));
                                }

                                LinearLayoutManager layoutManager1=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                recyclerView.setLayoutManager(layoutManager1);

                                BlogAdapter blogAdapter=new BlogAdapter(getApplicationContext(),blogData,2);
                                recyclerView.setAdapter(blogAdapter);
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
