package com.expertwebtech.boomer.fragment;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.expertwebtech.boomer.adapter.HomeAdapter;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private List<Home>homeList=new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;



    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);

        recyclerView=view.findViewById(R.id.recyclerview);
        progressBar=view.findViewById(R.id.progressbar);

//        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        homeList.clear();
//
//        homeList.add(new Home("Dr Kunal Rastogi",R.drawable.image1));
//        homeList.add(new Home("Pandit Raj Tripathi",R.drawable.image2));
//        homeList.add(new Home("Dr Kuldeep Rana",R.drawable.image3));
//        homeList.add(new Home("Anshu Thapper",R.drawable.image1));
//        homeList.add(new Home("ShivRaj JI",R.drawable.image2));
//        homeList.add(new Home("Gagan Sharma",R.drawable.image3));
//        homeList.add(new Home("Kiran Sharma",R.drawable.image1));
//        homeList.add(new Home("Raghvendra Singh",R.drawable.image2));
//        homeList.add(new Home("Rajashav Shri",R.drawable.image3));
//        homeList.add(new Home("Jumman Lakh",R.drawable.image1));


        getUsers();


        return view;
    }

    private void getUsers()
    {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.HOME_DATA,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status_code");

                            if (status.equals("200")) {

                                JSONArray dataArray=obj.getJSONArray("data");

                                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(layoutManager);

                                homeList.clear();

                                for (int i=0;i<dataArray.length();i++)
                                {
                                    JSONObject object=dataArray.getJSONObject(i);

                                    String id=object.getString("id");
                                    String name=object.getString("name");
                                    String image=object.getString("image");
                                    String speciality=object.getString("speciality");
                                    String total_exp=object.getString("total_exp");
                                    String no_of_like=object.getString("no_of_like");
                                    String no_of_share=object.getString("no_of_share");
                                   // String id=object.getString("id");

                                    homeList.add(new Home(id,name,Url.IMAGE_BASE_URL+image,"Astrologer",total_exp,no_of_like,"12",no_of_share));
                                }

                                HomeAdapter homeAdapter=new HomeAdapter(homeList,getContext());
                                recyclerView.setAdapter(homeAdapter);
                                homeAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);



                            } else {

                                Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

}
