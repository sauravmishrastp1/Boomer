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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.BlogAdapter;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.BlogData;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class Bloger_Blog extends Fragment {
    RecyclerView recyclerview_blog;
    private ArrayList<BlogData> blogData = new ArrayList<>();
    private TextView nul_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bloger__blog, container, false);
         recyclerview_blog = v.findViewById(R.id.myblog_recy);
         nul_layout = v.findViewById(R.id.nu_laayout);
        getblogdata();
        Toast.makeText(getContext(), ""+SharedPrefManager.getInstance(getContext()).getUser().getId(), Toast.LENGTH_SHORT).show();
        return v;
    }
    private void getblogdata()
    {

        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        String url = "http://xpertwebtech.in/bloom/public/api/blog/"+SharedPrefManager.getInstance(getContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status_code");

                            if (status.equals("200")) {

                                JSONArray dataArray=obj.getJSONArray("data");



                                blogData.clear();

                                for (int i=0;i<dataArray.length();i++)
                                {
                                    JSONObject object=dataArray.getJSONObject(i);

                                    String id=object.getString("id");
                                    String name=object.getString("name");
                                    String image=object.getString("image");
                                    String subject=object.getString("subject");
                                    String discription=object.getString("discription");


                                    blogData.add(new BlogData(id,name,subject,discription, Url.IMAGE_BASE_URL+image,Url.IMAGE_BASE_URL+SharedPrefManager.getInstance(getContext()).getUser().getImage(),SharedPrefManager.getInstance(getContext()).getUser().getName()));
                                }

                                LinearLayoutManager layoutManager1=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                                recyclerview_blog.setLayoutManager(layoutManager1);

                                BlogAdapter blogAdapter=new BlogAdapter(getContext(),blogData,2);
                                recyclerview_blog.setAdapter(blogAdapter);
                                blogAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                                nul_layout.setVisibility(View.GONE);



                            } else {

                                Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                nul_layout.setVisibility(View.VISIBLE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            nul_layout.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        nul_layout.setVisibility(View.VISIBLE);
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