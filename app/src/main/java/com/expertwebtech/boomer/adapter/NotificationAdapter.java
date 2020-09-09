package com.expertwebtech.boomer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.pojo.Home;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<Home> homeList;
    Context context;

    public NotificationAdapter(List<Home> homeList, Context context) {
        this.homeList = homeList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_ite_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {

        // holder.imageView.setImageResource(homeList.get(position).getPicture());

        holder.imageView.setBackgroundResource(homeList.get(position).getPicture());
        holder.nametv.setText(homeList.get(position).getName());

        if (position==1 || position==2||position==3 || position==0)
        {
            holder.newtag.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView imageView;
        TextView nametv;
        ImageView newtag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageview);
            nametv=itemView.findViewById(R.id.nametv);
            newtag=itemView.findViewById(R.id.newtag);

        }
    }

}
