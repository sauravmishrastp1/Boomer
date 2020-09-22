package com.expertwebtech.boomer.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expertwebtech.boomer.adapter.HomeAdapter;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.NotificationAdapter;
import com.expertwebtech.boomer.pojo.Home;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    List<Home>notificationlist=new ArrayList<>();
    RecyclerView recyclerview;


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerview=view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);

        notificationlist.clear();


        notificationlist.add(new Home("Dr Kunal Rastogi","","","","","","","","","","","","","",""));

        NotificationAdapter homeAdapter=new NotificationAdapter(notificationlist,getContext());
        recyclerview.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();

        return view;
    }

}
