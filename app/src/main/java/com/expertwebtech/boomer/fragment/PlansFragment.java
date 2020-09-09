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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.HomeAdapter;
import com.expertwebtech.boomer.adapter.PlanAdapter;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.Home;
import com.expertwebtech.boomer.pojo.Plans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlansFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Plans>plansList=new ArrayList<>();
    private View toolbar;
    private ProgressBar progressBar;



    public PlansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_plans, container, false);
        toolbar=view.findViewById(R.id.toolbar);

        recyclerView=view.findViewById(R.id.recyclerview);
        progressBar=view.findViewById(R.id.progressbar);

//        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//
////        plansList.add(new Plans("1","365",R.drawable.plan1,"#6EA2FF"));
////        plansList.add(new Plans("1","999",R.drawable.plan1,"#3FB444"));
////        plansList.add(new Plans("1","1299",R.drawable.plan1,"#FF5A60"));
////        plansList.add(new Plans("1","1999",R.drawable.plan1,"#E0AB00"));
//
//        PlanAdapter planAdapter=new PlanAdapter(plansList,getContext());
//        recyclerView.setAdapter(planAdapter);
//        planAdapter.notifyDataSetChanged();



        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
            }
        });

        getPlans();




        return view;
    }


    private void getPlans()
    {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.GET_ALL_PLANS,
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

                                plansList.clear();

                                for (int i=0;i<dataArray.length();i++)
                                {
                                    JSONObject object=dataArray.getJSONObject(i);

                                    String id=object.getString("id");
                                    String sub_name=object.getString("sub_name");
                                    String price=object.getString("price");
                                    String valid_no_of_days=object.getString("valid_no_of_days");

                                    plansList.add(new Plans(id,price,sub_name,R.drawable.plan1,valid_no_of_days,"#6EA2FF"));


                                }

                                PlanAdapter planAdapter=new PlanAdapter(plansList,getContext());
                                recyclerView.setAdapter(planAdapter);
                                planAdapter.notifyDataSetChanged();

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
