package com.expertwebtech.boomer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.expertwebtech.boomer.R;

import com.expertwebtech.boomer.activity.BlogDetailsActivity;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.BlogData;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.skyhope.showmoretextview.ShowMoreTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {

    Context context;
    private ArrayList<BlogData>blogData;
    int type;

    public BlogAdapter(Context context, ArrayList<BlogData> blogData, int type) {
        this.context = context;
        this.blogData = blogData;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (type==1)
            view= LayoutInflater.from(context).inflate(R.layout.blog_item_layout,parent,false);
        else
            view= LayoutInflater.from(context).inflate(R.layout.blog_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.showMoreTextView.setShowingChar(200);
        holder.showMoreTextView.setShowingLine(3);
        holder.showMoreTextView.setText(blogData.get(position).getDiscription());
        holder.showMoreTextView.addShowMoreText("Read More");
        holder.showMoreTextView.addShowLessText("Read Less");
        holder.showMoreTextView.setShowMoreColor(context.getResources().getColor(R.color.blue)); // or other color
        holder.showMoreTextView.setShowLessTextColor(Color.RED);
         holder.heading.setText(blogData.get(position).getSubject());
         holder.heading.setText(blogData.get(position).getName());
         holder.name_heading.setText(blogData.get(position).getUser_name());
        Picasso.with(context).load(blogData.get(position).getProfile_profile()).placeholder(R.drawable.user_placeholder).into(holder.profile_img);
        Picasso.with(context).load(blogData.get(position).getImage()).placeholder(R.drawable.user_placeholder).into(holder.blog_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BlogDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title",blogData.get(position).getName());
                intent.putExtra("subject",blogData.get(position).getSubject());
                intent.putExtra("dis",blogData.get(position).getDiscription());
                intent.putExtra("id",blogData.get(position).getId());
                intent.putExtra("img",blogData.get(position).getImage());
                intent.putExtra("img_user",blogData.get(position).getProfile_profile());

                context.startActivity(intent);
            }
        });
        holder.see_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.layout_coment.setVisibility(View.VISIBLE);
            }
        });

       holder.postblog_cmnt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

             String  coment =  holder.write_comment.getText().toString();


                String url = "http://xpertwebtech.in/bloom/public/api/postBlogComment?loginId="+SharedPrefManager.getInstance(context).getUser().getId()+
               "&blogId="+blogData.get(position).getId()+"&comment="+coment;
               StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status_code");
                        if(status.equals("200")){
                            Toast.makeText(context, "Thanku for commenting", Toast.LENGTH_SHORT).show();
                            holder.layout_coment.setVisibility(View.GONE);

                            String  comentt =  holder.write_comment.getText().toString();
                            String url = "http://xpertwebtech.in/bloom/public/api/postUserComment?loginId="+SharedPrefManager.getInstance(context).getUser().getId()+
                                    "&userId="+blogData.get(position).getId()+"&comment="+comentt;
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String status = jsonObject.getString("status_code");
                                        if(status.equals("200")){
                                            Toast.makeText(context, "Thanku for commenting", Toast.LENGTH_SHORT).show();
                                            holder.layout_coment.setVisibility(View.GONE);

                                        }else {
                                            Toast.makeText(context, "not submit", Toast.LENGTH_SHORT).show();

                                        }


                                    }catch (Exception e){
                                        Toast.makeText(context, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, "Server error!!", Toast.LENGTH_SHORT).show();

                                }
                            });
                            request.setRetryPolicy(new DefaultRetryPolicy(
                                    50000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
                            VolleySingleton.getInstance(context).addToRequestQueue(request);

                        }else {
                            Toast.makeText(context, "not submit", Toast.LENGTH_SHORT).show();

                        }


                    }catch (Exception e){
                        Toast.makeText(context, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(context, "Server error!!", Toast.LENGTH_SHORT).show();

                   }
               });
               request.setRetryPolicy(new DefaultRetryPolicy(
                       50000,
                       DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                       DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

               VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
               VolleySingleton.getInstance(context).addToRequestQueue(request);
           }
       });
        holder.like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String url = "http://xpertwebtech.in/bloom/public/api/blogLike?loginId="+SharedPrefManager.getInstance(context).getUser().getId()+
                        "&blogId="+blogData.get(position).getId()+"&islike="+"1";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status_code");
                            if(status.equals("200")){
                                Toast.makeText(context, "Thanku for like", Toast.LENGTH_SHORT).show();
                                //holder.layout_coment.setVisibility(View.GONE);

                            }else {
                                Toast.makeText(context, "not submit", Toast.LENGTH_SHORT).show();

                            }


                        }catch (Exception e){
                            Toast.makeText(context, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Server error!!", Toast.LENGTH_SHORT).show();

                    }
                });
                request.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
                VolleySingleton.getInstance(context).addToRequestQueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ShowMoreTextView showMoreTextView;
        TextView heading ,name;
        EditText write_comment;
        View layout_coment,like_layout;
        private ImageView blog_image,see_coment;
        private TextView postblog_cmnt,name_heading;
        private ImageView profile_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showMoreTextView=itemView.findViewById(R.id.text_view_show_more);
            blog_image = itemView.findViewById(R.id.image_);
            heading = itemView.findViewById(R.id.heading_);
            name = itemView.findViewById(R.id.subject);
            write_comment = itemView.findViewById(R.id.mobilenoEt);
            postblog_cmnt = itemView.findViewById(R.id.send_post);
            see_coment = itemView.findViewById(R.id.see_coment);
            layout_coment = itemView.findViewById(R.id.layut_coment);
            profile_img = itemView.findViewById(R.id.profile_image);
            name_heading = itemView.findViewById(R.id.nemae_);
            like_layout = itemView.findViewById(R.id.like);




        }
    }
}
