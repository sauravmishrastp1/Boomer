package com.expertwebtech.boomer.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
import com.expertwebtech.boomer.adapter.Comment_Adapter;
import com.expertwebtech.boomer.constant.SharedPrefManager;
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
    String title,subject,dis,img,id,profile_image;
    TextView showMoreTextView;
    ImageView profile_img,userprofileimg;
    ImageView BLOGIMG;
    TextView titletxt,subjecttxt;
    Bundle bundle;
    private ArrayList<BlogData> blogData = new ArrayList<>();
    private ImageView comment;
    private TextView liketxt,comenttxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        recyclerView=findViewById(R.id.recyclerview);
        titletxt = findViewById(R.id.name);
        userprofileimg = findViewById(R.id.imageView);
        BLOGIMG  = findViewById(R.id.blogimg);
        comment = findViewById(R.id.comment);
        subjecttxt = findViewById(R.id.subject_);
        showMoreTextView = findViewById(R.id.text_view_show_more);
        profile_img = findViewById(R.id.imageView);
        liketxt = findViewById(R.id.like_);
        comenttxt = findViewById(R.id.commenttxt);
        bundle = getIntent().getExtras();

        if(!bundle.isEmpty()){
            title = bundle.getString("title");
            subject = bundle.getString("subject");
            dis = bundle.getString("dis");
            img = bundle.getString("img");
            id = bundle.getString("id");
            profile_image = bundle.getString("img_user");

            titletxt.setText(title);
            subjecttxt.setText(subject);
            showMoreTextView.setText(dis);
            Picasso.with(getApplicationContext()).load(img).placeholder(R.drawable.user_placeholder).into(BLOGIMG);
            Picasso.with(getApplicationContext()).load(profile_image).placeholder(R.drawable.user_placeholder).into(userprofileimg);

        }
        getblogdata();
        getlikendcomt();

    }
    private void getblogdata()
    {

        final KProgressHUD progressDialog = KProgressHUD.create(BlogDetailsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        String url = "http://xpertwebtech.in/bloom/public/api/getUserComment/"+id;
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
                                    String name=object.getString("user_name");
                                   // String image=object.getString("image");
                                  //  String subject=object.getString("subject");
                                    String discription=object.getString("comment");


                                    blogData.add(new BlogData(id,name,"",discription, "", "",""));

                                    LinearLayoutManager layoutManager1=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                    recyclerView.setLayoutManager(layoutManager1);

                                    Comment_Adapter blogAdapter=new Comment_Adapter(getApplicationContext(),blogData,2);
                                    recyclerView.setAdapter(blogAdapter);
                                    blogAdapter.notifyDataSetChanged();
                                    progressDialog.dismiss();


                                }if(dataArray.length()==0){
                                    progressDialog.dismiss();
                                }



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
    private void getlikendcomt()
    { // blogData.clear();

        final KProgressHUD progressDialog = KProgressHUD.create(BlogDetailsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        String url = "http://xpertwebtech.in/bloom/public/api/getBlogCount/"+id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status_code");

                            if (status.equals("200")) {








                                    JSONObject object=obj.getJSONObject("data");

                                    String blog_like_count=object.getString("blog_like_count");
                                    String blog_comment_count=object.getString("blog_comment_count");
                                   liketxt.setText(blog_comment_count);
                                   comenttxt.setText(blog_like_count);
                                    //blogData.add(new BlogData(id,name,subject,discription, Url.IMAGE_BASE_URL+image, Url.IMAGE_BASE_URL+SharedPrefManager.getInstance(getApplicationContext()).getUser().getImage(),SharedPrefManager.getInstance(getApplicationContext()).getUser().getName()));



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
