package com.expertwebtech.boomer.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.HomeAdapter;
import com.expertwebtech.boomer.adapter.SubscriptionAdapter;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.pojo.Home;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    List<Home>subscriptionlist=new ArrayList<>();
    View toolbar;
    Button logoutbtn;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        //recyclerView1=view.findViewById(R.id.recyclerview1);
        recyclerView2=view.findViewById(R.id.recyclerview2);
        toolbar=view.findViewById(R.id.toolbar);
        logoutbtn=view.findViewById(R.id.logoutbtn);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(layoutManager);


        subscriptionlist.add(new Home("Dr Kunal Rastogi",R.drawable.image1));
        subscriptionlist.add(new Home("Pandit Raj Tripathi",R.drawable.image2));
        subscriptionlist.add(new Home("Dr Kuldeep Rana",R.drawable.image3));
        subscriptionlist.add(new Home("Anshu Thapper",R.drawable.image1));
        subscriptionlist.add(new Home("ShivRaj JI",R.drawable.image2));


        SubscriptionAdapter subscriptionAdapter=new SubscriptionAdapter(subscriptionlist,getContext());
        recyclerView2.setAdapter(subscriptionAdapter);
        subscriptionAdapter.notifyDataSetChanged();

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



        return view;
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
