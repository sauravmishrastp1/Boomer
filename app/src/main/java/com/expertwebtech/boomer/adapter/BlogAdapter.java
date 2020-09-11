package com.expertwebtech.boomer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.expertwebtech.boomer.R;

import com.expertwebtech.boomer.activity.BlogDetailsActivity;
import com.expertwebtech.boomer.pojo.BlogData;
import com.skyhope.showmoretextview.ShowMoreTextView;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.showMoreTextView.setShowingChar(200);
        holder.showMoreTextView.setShowingLine(3);
        holder.showMoreTextView.setText(blogData.get(position).getDiscription());
        holder.showMoreTextView.addShowMoreText("Read More");
        holder.showMoreTextView.addShowLessText("Read Less");
        holder.showMoreTextView.setShowMoreColor(context.getResources().getColor(R.color.blue)); // or other color
        holder.showMoreTextView.setShowLessTextColor(Color.RED);
         holder.heading.setText(blogData.get(position).getSubject());
         holder.heading.setText(blogData.get(position).getName());
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
                context.startActivity(intent);
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
        private ImageView blog_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showMoreTextView=itemView.findViewById(R.id.text_view_show_more);
            blog_image = itemView.findViewById(R.id.image_);
            heading = itemView.findViewById(R.id.heading_);
            name = itemView.findViewById(R.id.subject);



        }
    }
}
