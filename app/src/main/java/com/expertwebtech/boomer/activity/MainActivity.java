package com.expertwebtech.boomer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.fragment.Bloger_Blog;
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
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
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

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserFragment()).commit();
            spaceNavigationView.setCentreButtonIcon(R.drawable.search);

        }if(usertype.equals("2")) {
            spaceNavigationView.setCentreButtonIcon(R.drawable.homeuser);


            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.mypost));
            spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.ic_baseline_add_24));

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();


        }



     //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserFragment()).commit();


        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {

               // Toast.makeText(MainActivity.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();
                if(usertype.equals("1")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SearchFragment()).commit();

                }if (usertype.equals("2")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

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
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Bloger_Blog()).commit();
                    else if (itemIndex == 1)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateBlogFragment()).commit();

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

    protected void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            Toast.makeText(this,"Permissions already granted",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        requestStoragePermission();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        )
                ){
                    // Permissions are granted
                    Toast.makeText(this,"Permissions granted.",Toast.LENGTH_SHORT).show();
                }else {
                    // Permissions are denied
                    Toast.makeText(this,"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }
}
