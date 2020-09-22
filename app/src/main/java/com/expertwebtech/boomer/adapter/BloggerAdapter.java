package com.expertwebtech.boomer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.activity.UserDetailsActivity;
import com.expertwebtech.boomer.pojo.Home;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BloggerAdapter extends RecyclerView.Adapter<BloggerAdapter.ViewHolder> {

    Context context;
    private ArrayList<Home>homeList;

    public BloggerAdapter(Context context, ArrayList<Home> homeList) {
        this.context = context;
        this.homeList = homeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.blogger_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String name = homeList.get(position).getName();
        String img = homeList.get(position).getImage();
        Picasso.with(context).load(img).into(holder.profile_img);
        Picasso.with(context).load(img).into(holder.getProfile_img);
        holder.name.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, UserDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",homeList.get(position).getName());
                intent.putExtra("id",homeList.get(position).getId());
                intent.putExtra("img",homeList.get(position).getImage());
                intent.putExtra("email",homeList.get(position).getEmail());
                intent.putExtra("location",homeList.get(position).getLocation());
                intent.putExtra("address",homeList.get(position).getAddress());
                intent.putExtra("phone",homeList.get(position).getPhone());
                intent.putExtra("post",homeList.get(position).getPost());
                intent.putExtra("flowers",homeList.get(position).getFlowers());
                intent.putExtra("dob",homeList.get(position).getDob());
                intent.putExtra("speciality",homeList.get(position).getSpeciality());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_img,getProfile_img;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_img = itemView.findViewById(R.id.profile_imag);
            getProfile_img = itemView.findViewById(R.id.profile_img2);
            name = itemView.findViewById(R.id.name_);
        }
    }
}
