package com.expertwebtech.boomer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.fragment.CreateBlogFragment;
import com.expertwebtech.boomer.fragment.HomeFragment;
import com.expertwebtech.boomer.fragment.NotificationsFragment;
import com.expertwebtech.boomer.fragment.PlansFragment;
import com.expertwebtech.boomer.fragment.SearchFragment;
import com.expertwebtech.boomer.fragment.UserFragment;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {

    private SpaceNavigationView spaceNavigationView;
    String usertype ="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceNavigationView=findViewById(R.id.space);



        usertype = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUsertype();
        //Toast.makeText(this, ""+usertype, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, ""+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), Toast.LENGTH_SHORT).show();
        if(usertype.equals("1")){
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.homeicon));
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.myplan));
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.notification));
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.homeuser));
            spaceNavigationView.setCentreButtonIcon(R.drawable.search);
        }if(usertype.equals("2")) {
            spaceNavigationView.setCentreButtonIcon(R.drawable.ic_baseline_add_24);
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.myplan));
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.notification));
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.homeuser));

        }else {
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.homeicon));
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.myplan));
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.notification));
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.homeuser));
            spaceNavigationView.setCentreButtonIcon(R.drawable.search);

        }



        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserFragment()).commit();


        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {

               // Toast.makeText(MainActivity.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();
                if(usertype.equals("1")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SearchFragment()).commit();

                }if (usertype.equals("2")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CreateBlogFragment()).commit();

                }else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SearchFragment()).commit();

                }

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if(usertype.equals("1")) {

                    if (itemIndex == 0)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserFragment()).commit();
                    else if (itemIndex == 1)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlansFragment()).commit();
                    else if (itemIndex == 2)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationsFragment()).commit();
                    else if (itemIndex == 3)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }if(usertype.equals("2")){
                    if (itemIndex == 0)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlansFragment()).commit();
                    else if (itemIndex == 1)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationsFragment()).commit();
                    else if (itemIndex == 2)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserFragment()).commit();

                }else {
                    if (itemIndex == 0)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserFragment()).commit();
                    else if (itemIndex == 1)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlansFragment()).commit();
                    else if (itemIndex == 2)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationsFragment()).commit();
                    else if (itemIndex == 3)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
              //  Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }
}
