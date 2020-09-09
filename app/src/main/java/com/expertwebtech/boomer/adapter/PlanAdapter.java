package com.expertwebtech.boomer.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.activity.BlogListingActivity;
import com.expertwebtech.boomer.pojo.Plans;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    List<Plans>plansList;
    Context context;

    public PlanAdapter(List<Plans> plansList, Context context) {
        this.plansList = plansList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.plan_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.pricetv.setText("\u20B9"+plansList.get(position).getPrice());
        holder.titletv.setText(plansList.get(position).getTitle());
        holder.daystv.setText(plansList.get(position).getDay()+" days");
        holder.parentview.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(plansList.get(position).getBackgroundColor())));
    }

    @Override
    public int getItemCount() {
        return plansList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View parentview;
        TextView pricetv,daystv,titletv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentview=itemView.findViewById(R.id.parentview);
            pricetv=itemView.findViewById(R.id.pricetv);
            daystv=itemView.findViewById(R.id.daystv);
            titletv=itemView.findViewById(R.id.titletv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, BlogListingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
