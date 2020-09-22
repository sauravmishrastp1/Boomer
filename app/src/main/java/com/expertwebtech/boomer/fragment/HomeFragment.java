package com.expertwebtech.boomer.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.activity.BlogDetailsActivity;
import com.expertwebtech.boomer.activity.Update_Profile;
import com.expertwebtech.boomer.adapter.BlogAdapter;
import com.expertwebtech.boomer.adapter.HomeAdapter;
import com.expertwebtech.boomer.adapter.SubscriptionAdapter;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.BlogData;
import com.expertwebtech.boomer.pojo.Home;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    List<Home>subscriptionlist=new ArrayList<>();
    View toolbar;
    Button logoutbtn;
    private ArrayList<BlogData> blogData = new ArrayList<>();
    private String uertype;
    private TextView name1txt,usernametxt,emailtxt,locationtxt,addresstxt,dateofbirthtxt,speclizationtxt,phonetxt;
    private String name,emailid,profileic;
    private ImageView profileImg;
    private Button update_profile;
    private String id,phone,location,address,speciality,dob,email,image,exp;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        //recyclerView1=view.findViewById(R.id.recyclerview1);
        recyclerView2=view.findViewById(R.id.recyclerview2);
        toolbar=view.findViewById(R.id.toolbar);
        name1txt = view.findViewById(R.id.profile_name);
        usernametxt = view.findViewById(R.id.profile_nametxt);
        update_profile = view.findViewById(R.id.update_profile);
        emailtxt = view.findViewById(R.id.profile_email_txt);
        locationtxt = view.findViewById(R.id.lovatintct);
        logoutbtn=view.findViewById(R.id.logoutbtn);
        profileImg = view.findViewById(R.id.profile_image);
        addresstxt = view.findViewById(R.id.address_txt);
        dateofbirthtxt=view.findViewById(R.id.dateofbirth);
        speclizationtxt = view.findViewById(R.id.speclization);
        phonetxt = view.findViewById(R.id.phone_txt);

        uertype =SharedPrefManager.getInstance(getContext()).getUser().getUsertype();
        name = SharedPrefManager.getInstance(getContext()).getUser().getName();
        emailid = SharedPrefManager.getInstance(getContext()).getUser().getEmail();
        profileic = SharedPrefManager.getInstance(getContext()).getUser().getImage();
        if(uertype.equals("2")){
            speclizationtxt.setVisibility(View.VISIBLE);
        }else {
            speclizationtxt.setVisibility(View.GONE);
        }

       // Toast.makeText(getContext(), "profile="+profileic, Toast.LENGTH_SHORT).show();
        Picasso.with(getContext()).load(Url.IMAGE_BASE_URL+profileic).into(profileImg);

        if(uertype.equals("1")){
            LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
            recyclerView2.setLayoutManager(layoutManager);


//            subscriptionlist.add(new Home("Dr Kunal Rastogi",R.drawable.image1));
//            subscriptionlist.add(new Home("Pandit Raj Tripathi",R.drawable.image2));
//            subscriptionlist.add(new Home("Dr Kuldeep Rana",R.drawable.image3));
//            subscriptionlist.add(new Home("Anshu Thapper",R.drawable.image1));
//            subscriptionlist.add(new Home("ShivRaj JI",R.drawable.image2));
            SubscriptionAdapter subscriptionAdapter=new SubscriptionAdapter(subscriptionlist,getContext());
            recyclerView2.setAdapter(subscriptionAdapter);
            subscriptionAdapter.notifyDataSetChanged();

        }else {
            recyclerView2.setVisibility(View.GONE);
        }

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Update_Profile.class);
               intent.putExtra("speclization",speciality);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("phone",phone);
                intent.putExtra("exp",exp);
                 intent.putExtra("address",address);
                 intent.putExtra("location",location);
                 intent.putExtra("dob",dob);
                 intent.putExtra("img",image);
                startActivity(intent);
            }
        });


        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
            }
        });


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               showDialogue();
            }
        });

         getprofile();

        return view;
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
                                recyclerView2.setLayoutManager(layoutManager1);

                                BlogAdapter blogAdapter=new BlogAdapter(getContext(),blogData,2);
                                recyclerView2.setAdapter(blogAdapter);
                                blogAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();



                            } else {

                                Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
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

    private void getprofile()
    {
        blogData.clear();

        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        String url = "http://xpertwebtech.in/bloom/public/api/getUserProfile/"+SharedPrefManager.getInstance(getContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status_code");

                            if (status.equals("200")) {

                                JSONObject object=obj.getJSONObject("data");

                                     id=object.getString("id");
                                     name=object.getString("name");
                                     email=object.getString("email");
                                     dob=object.getString("dob");
                                     image=object.getString("image");
                                     location    = object.getString("location");
                                     speciality= object.getString("speciality");
                                      address= object.getString("address");
                                     phone= object.getString("phone");
                                     exp = object.getString("total_exp");
                                      name1txt.setText(name);
                                      usernametxt.setText(name);
                                      emailtxt.setText(email);
                                      dateofbirthtxt.setText(dob);
                                      locationtxt.setText(location);
                                      speclizationtxt.setText(speciality);
                                      addresstxt.setText(address);
                                      phonetxt.setText(phone);
                                     Picasso.with(getContext()).load(Url.IMAGE_BASE_URL+image).into(profileImg);
                                    progressDialog.dismiss();
                                    //blogData.add(new BlogData(id,name,subject,discription, Url.IMAGE_BASE_URL+image,Url.IMAGE_BASE_URL+SharedPrefManager.getInstance(getContext()).getUser().getImage(),SharedPrefManager.getInstance(getContext()).getUser().getName()));




                            } else {

                                Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
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

    private void showDialogue()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Are You Sure !! Want to Logout ?");
        builder1.setCancelable(true);
        builder1.setIcon(R.drawable.ic_launcher_foreground);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//                        SharedPrefManager.getInstance(getContext()).logout();
//                        getActivity().finish();

//                        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(getContext());
//                        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;



//                        if (alreadyloggedAccount != null) {
//
//                            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                                    .requestEmail()
//                                    .build();
//                            googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
//                            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    //On Succesfull signout we navigate the user back to LoginActivity
//                                    SharedPrefManager.getInstance(getContext()).logout();
//                                    getActivity().finish();
//                                }
//                            });
//
//                        }
//
//                        else if (!loggedOut) {
//
//                            LoginManager.getInstance().logOut();
//                            SharedPrefManager.getInstance(getContext()).logout();
//                            getActivity().finish();
//
//                        }
//                        else {
//                            Log.d(TAG, "Not logged in");
//                            SharedPrefManager.getInstance(getContext()).logout();
//                            getActivity().finish();
//                        }

                        SharedPrefManager.getInstance(getContext()).logout();
                        getActivity().finish();

                     }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();



    }


}
