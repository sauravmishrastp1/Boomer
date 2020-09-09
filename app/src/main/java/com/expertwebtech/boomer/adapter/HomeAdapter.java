package com.expertwebtech.boomer.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.activity.UserDetailsActivity;
import com.expertwebtech.boomer.pojo.Home;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    List<Home>homeList;
    Context context;

    public HomeAdapter(List<Home> homeList, Context context) {
        this.homeList = homeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_ite_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // holder.imageView.setImageResource(homeList.get(position).getPicture());

       // holder.imageView.setBackgroundResource(homeList.get(position).getPicture());
        Picasso.with(context).load(homeList.get(position).getImage()).placeholder(R.drawable.user_placeholder).into(holder.image);
        Log.d("image=",homeList.get(position).getImage());
        holder.nametv.setText(homeList.get(position).getName());
        holder.experincetv.setText(homeList.get(position).getExperience()+" Years Experienced");
        holder.totallikestv.setText(homeList.get(position).getLike());
        holder.totalsharetv.setText(homeList.get(position).getShare());
        holder.specialitytv.setText(homeList.get(position).getSpeciality());

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView imageView;
        ImageView image;
        TextView nametv,specialitytv,experincetv,totallikestv,totalsharetv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageview);
            nametv=itemView.findViewById(R.id.nametv);
            specialitytv=itemView.findViewById(R.id.specialitytv);
            experincetv=itemView.findViewById(R.id.experincetv);
            totallikestv=itemView.findViewById(R.id.totallikestv);
            totalsharetv=itemView.findViewById(R.id.totalsharetv);
            image=itemView.findViewById(R.id.image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, UserDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }
}
