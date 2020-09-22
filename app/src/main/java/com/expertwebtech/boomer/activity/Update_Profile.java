package com.expertwebtech.boomer.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.CursorLoader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.fading_entrances.FadeInDownAnimator;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.constant.MultiPartHelperClass;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.retrofitfileupload.Api;
import com.expertwebtech.boomer.retrofitfileupload.Example;
import com.expertwebtech.boomer.retrofitfileupload.UpdateProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Update_Profile extends AppCompatActivity {
     private Toolbar toolbar;
     private EditText username,email_id,phone_noi,locatin,address,dateofbirth,speclization_et,total_exprence_et;
     private Button update_btn;
     private String user_name, email_Id,phone_no,location_,address_,date_ofbirth,speclization="n/a",totalexprence="n/a",img;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final int GALLERY_IMAGE = 1;
    String filePatadhar = "";
    byte Pancard[] = "00.00.00".getBytes();
    private ImageView profile_img;
    View update_for_bloger;
    private String user_type;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__profile);
        email_id = findViewById(R.id.email_et);
        username = findViewById(R.id.user_nameet);
        phone_noi = findViewById(R.id.phone_et);
        locatin =findViewById(R.id.location_Et);
        update_btn = findViewById(R.id.post_now);
        address = findViewById(R.id.Address_Et);
        profile_img = findViewById(R.id.profile_img);
        dateofbirth  = findViewById(R.id.dateof_birth_Et);
        update_for_bloger = findViewById(R.id.forbloger_update);
        speclization_et = findViewById(R.id.speclization);
        total_exprence_et = findViewById(R.id.total_exp);

        bundle = getIntent().getExtras();
        user_name = bundle.getString("name");
        email_Id = bundle.getString("email");
        phone_no = bundle.getString("phone");
        location_ = bundle.getString("location");
        address_ = bundle.getString("address");
        date_ofbirth = bundle.getString("dob");
        speclization = bundle.getString("speclization");
        totalexprence = bundle.getString("exp");
        img = bundle.getString("img");

        username.setText(user_name);
        email_id.setText(email_Id);
        phone_noi.setText(phone_no);
        locatin.setText(location_);
        address.setText(address_);
        dateofbirth.setText(date_ofbirth);
        speclization_et.setText(speclization);
        total_exprence_et.setText(totalexprence);




        Picasso.with(getApplicationContext() ).load(Url.IMAGE_BASE_URL+img).into(profile_img);
        user_type = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUsertype();
          if(user_type.equals("2")){
              update_for_bloger.setVisibility(View.VISIBLE);
          }else {
              update_for_bloger.setVisibility(View.GONE);
          }
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update_Profile.this,MainActivity.class);
                startActivity(intent);
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate_user();
            }
        });
        dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Update_Profile.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                date_ofbirth = year  + "-" + (monthOfYear + 1) + "-" +dayOfMonth ;




                                    dateofbirth.setText(date_ofbirth);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_IMAGE);

            }
        });
    }

    private void validate_user() {
        user_name = username.getText().toString();
        email_Id = email_id.getText().toString();
        phone_no = phone_noi.getText().toString();
        location_ = locatin.getText().toString();
        address_ = address.getText().toString();
        date_ofbirth = dateofbirth.getText().toString();
        speclization = speclization_et.getText().toString();
        totalexprence = total_exprence_et.getText().toString();
        if(user_name.isEmpty()){
            username.setError("Required");
            username.requestFocus();

        }else if(email_Id.isEmpty()){
            email_id.setError("Required");
            email_id.requestFocus();
        }else if(phone_no.isEmpty()){
            phone_noi.setError("Required");
            phone_noi.requestFocus();
        }else if(date_ofbirth.isEmpty()){
            dateofbirth.setError("Required");
            dateofbirth.requestFocus();
        }else if(location_.isEmpty()){
            locatin.setError("Required");
            locatin.requestFocus();
        }else if(address_.isEmpty()){
            address.setError("Required");
            address.requestFocus();
        }else {
            if (user_type.equals("1")){
                update_profile();
            }if(user_type.equals("2")){
                update_profile_bloger();
            }

        }


    }

    private void callupdateapi() {
        String url = "http://xpertwebtech.in/bloom/public/api/updateUserProfile?name="+user_name+"&email="+email_Id+"&dob="+date_ofbirth+"&updated_user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               try {
                   JSONObject jsonObject = new JSONObject(response);
                   String status = jsonObject.getString("status_code");
                   if(status.equals("200")){
                       Toast.makeText(Update_Profile.this, "Profile update successfully", Toast.LENGTH_SHORT).show();
                   }else {

                   }

               }catch (Exception e){
                   Toast.makeText(Update_Profile.this, "Somthing went Wrong", Toast.LENGTH_SHORT).show();
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_Profile.this, "Server error!!", Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            //  Toast.makeText(this, "you selected="+filePathpic+uri, Toast.LENGTH_SHORT).show();

            try {
                filePatadhar = getRealPathFromURI(uri);
                //imageView.setImageDrawable(Drawable.createFromPath(filePathpic));
                //  type = FileUtils.getMimeType(getContext(), uri);
                // Toast.makeText(this, "type->"+type, Toast.LENGTH_SHORT).show();
                //extension = (String) com.azsm.reeduacare.constant.FileUtils.getExtension(String.valueOf(uri));
                // Toast.makeText(this, "ex->"+extension, Toast.LENGTH_SHORT).show();


                // Toast.makeText(this, ""+filePathpic, Toast.LENGTH_SHORT).show();
                //   ContentResolver cr =getRealPathFromURI(uri).getContentResolver();
                //  InputStream inputStream = cr.openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeFile(filePatadhar);
                profile_img.setImageBitmap(bitmap);
                // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                // ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                //Pancard = baos.toByteArray();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                Pancard = baos.toByteArray();
                //Toast.makeText(this, "pan-"+Pancard, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private  void update_profile(){
        final KProgressHUD progressDialog = KProgressHUD.create(Update_Profile.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        // Toast.makeText(getContext(), ""+SharedPrefManager.getInstance(getContext()).getUser().getId(), Toast.LENGTH_SHORT).show();

        RequestBody user_res= RequestBody.create(MediaType.parse("text/plain"),user_name);
        RequestBody email_res= RequestBody.create(MediaType.parse("text/plain"),email_Id);
        RequestBody dob= RequestBody.create(MediaType.parse("text/plain"),date_ofbirth);
        RequestBody user_id= RequestBody.create(MediaType.parse("text/plain"), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
        RequestBody location_res= RequestBody.create(MediaType.parse("text/plain"),location_);
        RequestBody address_res= RequestBody.create(MediaType.parse("text/plain"),address_);
        RequestBody phone_res = RequestBody.create(MediaType.parse("text/plan"),phone_no);

        //The gson builder
        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object
        //creating retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(com.expertwebtech.boomer.retrofitfileupload.Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //creating our api
        Api api = retrofit.create(Api.class);

        //creating a call and calling the upload image method
        Call<UpdateProfile> call = api.updateprofile(MultiPartHelperClass.getMultipartData(new File(filePatadhar), "image"), user_res,email_res,dob,phone_res,user_id,location_res,address_res);

        //finally performing the call
        call.enqueue(new Callback<UpdateProfile>() {
            @Override
            public void onResponse(retrofit2.Call<UpdateProfile> call, retrofit2.Response<UpdateProfile> response) {

               // showDialogue();
                Toast.makeText(Update_Profile.this, "update successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Update_Profile.this,MainActivity.class);
                startActivity(intent);
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<UpdateProfile> call, Throwable t) {

               // showDialogue();
                progressDialog.dismiss();
            }

        });
    }
    private  void update_profile_bloger(){
        final KProgressHUD progressDialog = KProgressHUD.create(Update_Profile.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        // Toast.makeText(getContext(), ""+SharedPrefManager.getInstance(getContext()).getUser().getId(), Toast.LENGTH_SHORT).show();

        RequestBody user_res= RequestBody.create(MediaType.parse("text/plain"),user_name);
        RequestBody email_res= RequestBody.create(MediaType.parse("text/plain"),email_Id);
        RequestBody dob= RequestBody.create(MediaType.parse("text/plain"),date_ofbirth);
        RequestBody user_id= RequestBody.create(MediaType.parse("text/plain"), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
        RequestBody location_res= RequestBody.create(MediaType.parse("text/plain"),location_);
        RequestBody address_res= RequestBody.create(MediaType.parse("text/plain"),address_);
        RequestBody phone_res = RequestBody.create(MediaType.parse("text/plan"),phone_no);
        RequestBody speclity_res= RequestBody.create(MediaType.parse("text/plain"),speclization);
        RequestBody exprence_res = RequestBody.create(MediaType.parse("text/plan"),totalexprence);


        //The gson builder
        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object
        //creating retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(com.expertwebtech.boomer.retrofitfileupload.Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //creating our api
        Api api = retrofit.create(Api.class);

        //creating a call and calling the upload image method
        Call<UpdateProfile> call = api.updatebloger(MultiPartHelperClass.getMultipartData(new File(filePatadhar), "image"), user_res,email_res,dob,phone_res,user_id,location_res,speclity_res,exprence_res,address_res);

        //finally performing the call
        call.enqueue(new Callback<UpdateProfile>() {
            @Override
            public void onResponse(retrofit2.Call<UpdateProfile> call, retrofit2.Response<UpdateProfile> response) {

                // showDialogue();
                Toast.makeText(Update_Profile.this, "update successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Update_Profile.this,MainActivity.class);
                startActivity(intent);
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<UpdateProfile> call, Throwable t) {

                // showDialogue();
                progressDialog.dismiss();
            }

        });
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}