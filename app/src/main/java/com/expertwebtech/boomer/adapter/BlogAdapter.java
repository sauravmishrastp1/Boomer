package com.expertwebtech.boomer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.expertwebtech.boomer.R;

import com.expertwebtech.boomer.activity.BlogDetailsActivity;
import com.skyhope.showmoretextview.ShowMoreTextView;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {

    Context context;
    int type;

    public BlogAdapter(Context context,int type) {
        this.context = context;
        this.type=type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (type==1)
            view= LayoutInflater.from(context).inflate(R.layout.blog_item_layout,parent,false);
        else
            view= LayoutInflater.from(context).inflate(R.layout.blog_details_page_blog_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.showMoreTextView.setShowingChar(200);
        holder.showMoreTextView.setShowingLine(3);
        holder.showMoreTextView.addShowMoreText("Read More");
        holder.showMoreTextView.addShowLessText("Read Less");
        holder.showMoreTextView.setShowMoreColor(context.getResources().getColor(R.color.blue)); // or other color
        holder.showMoreTextView.setShowLessTextColor(Color.RED);
    }

    @Override
    public int getItemCount() {
        if (type==1)
        return 10;
        else
            return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ShowMoreTextView showMoreTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showMoreTextView=itemView.findViewById(R.id.text_view_show_more);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, BlogDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });


        }
    }
}
