package com.expertwebtech.boomer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.pojo.Home;

import java.util.List;

public class SubscriptionAdapter  extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    List<Home> homeList;
    Context context;

    public SubscriptionAdapter(List<Home> homeList, Context context) {
        this.homeList = homeList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_ite_layout,parent,false);

        return new SubscriptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.ViewHolder holder, int position) {

        // holder.imageView.setImageResource(homeList.get(position).getPicture());

       // holder.imageView.setBackgroundResource(homeList.get(position).getPicture());
        holder.nametv.setText(homeList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView imageView;
        TextView nametv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageview);
            nametv=itemView.findViewById(R.id.nametv);

        }
    }

}
