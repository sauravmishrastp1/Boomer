package com.expertwebtech.boomer.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.Spinner_ItemAdapter;
import com.expertwebtech.boomer.constant.MultiPartHelperClass;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.constant.Url;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.Data;
import com.expertwebtech.boomer.pojo.Example;
import com.expertwebtech.boomer.pojo.Spinner_ItemModel;
import com.expertwebtech.boomer.pojo.User;
import com.expertwebtech.boomer.retrofitfileupload.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private Button registerbtn;
    private EditText usernameEt,emailEt,phoneEt,passEt;
    private String username,email,phone,pass;
    public  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressBar progressBar;
    private View signinpagelink;
    String filePatadhar = "";
    byte Pancard[] = "00.00.00".getBytes();
    private static final int GALLERY_IMAGE = 1;
    private List<Spinner_ItemModel> genderlist = new ArrayList<>();
    private Spinner editText;
    String gender;
    String usertype;
    ImageView uploadimg_icon,profileimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_register);
        editText = findViewById(R.id.spiner);
        usernameEt=findViewById(R.id.usernameEt);
        uploadimg_icon = findViewById(R.id.upload_img_icon);
        profileimg = findViewById(R.id.uploadimg);
        emailEt=findViewById(R.id.emailEt);
        phoneEt=findViewById(R.id.phoneEt);
        passEt=findViewById(R.id.passEt);
        progressBar=findViewById(R.id.progressbar);
        registerbtn=findViewById(R.id.registerbtn);
        signinpagelink=findViewById(R.id.signinpagelink);

        genderlist = getGenderList();
       uploadimg_icon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_PICK);
               startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_IMAGE);

           }
       });
        final Spinner_ItemAdapter genderadapter = new Spinner_ItemAdapter(getApplicationContext(), (ArrayList<Spinner_ItemModel>) genderlist);

        if (editText != null) {
            editText.setAdapter(genderadapter);

            editText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Spinner_ItemModel model = (Spinner_ItemModel) parent.getSelectedItem();

                    gender = model.getSpinnerItemName();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username=usernameEt.getText().toString();
                email=emailEt.getText().toString();
               // phone=phoneEt.getText().toString();
                pass=passEt.getText().toString();

               // Toast.makeText(RegisterActivity.this, ""+username+" "+email+" "+pass, Toast.LENGTH_SHORT).show();
                if(gender.equals("premium user")){
                    usertype ="2";
                }else {
                    usertype="1";
                }

                if (TextUtils.isEmpty(username))
                {
                    usernameEt.setError("Required");
                    usernameEt.requestFocus();
                }
                else if (TextUtils.isEmpty(email))
                {
                    emailEt.setError("Required");
                    emailEt.requestFocus();
                }
                else if (!email.trim().matches(emailPattern))
                {
                    emailEt.setError("Email Address is not valid");
                    emailEt.requestFocus();
                }
//                else if (TextUtils.isEmpty(phone))
//                {
//                    phoneEt.setError("Required");
//                    phoneEt.requestFocus();
//                }
//                else if (phone.length()>10 || phone.length()<10)
//                {
//                    phoneEt.setError("Phone number Must be 10 digits");
//                    phoneEt.requestFocus();
//                }
                else if (TextUtils.isEmpty(pass))
                {
                    passEt.setError("Required");
                    passEt.requestFocus();
                }
//                else if (!isValidPassword(pass))
//                {
//                    passEt.setError("Please Enter Valid Password");
//                    passEt.requestFocus();
//                }




                else
                {
                    uploadFile();
                }


            }
        });

        signinpagelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
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
                profileimg.setImageBitmap(bitmap);
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
    private ArrayList<Spinner_ItemModel> getGenderList() {
        genderlist.add(new Spinner_ItemModel("User Type"));
        genderlist.add(new Spinner_ItemModel("premium user"));
        genderlist.add(new Spinner_ItemModel("Non premium user"));
        return (ArrayList<Spinner_ItemModel>) genderlist;
    }
    private void registerUser()
    {
      //  Toast.makeText(this, "usertype="+usertype, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);

                            String status = obj.getString("status_code");

                            if (status.equals("200")) {

                                JSONObject userJson = obj.getJSONObject("data");

                                String userId = userJson.getString("id");
                                String username = userJson.getString("name");
                                String userEmail = userJson.getString("email");
                               // String userPhone = userJson.getString("phone");
                                String user_type = userJson.getString("user_type");
                               // String image = userJson.getString("image");

                                User user=new User(userId,username,userEmail,user_type,"","","");
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                finish();



                                progressBar.setVisibility(View.GONE);


                            } else {

                                Toast.makeText(getApplicationContext(), "Allready register", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "something went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {


//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                params.put("access-token", ACCESS_TOKEN);
//                return params;
//            }

            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password",pass);
                params.put("name", username);
                params.put("user_type",usertype);
                params.put("dob","1998-09-09");
                params.put("location","noida");
                params.put("speciality","doctor");
                params.put("total_exp","1");
                params.put("address","noida");


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void uploadFile() {
       {
            final KProgressHUD progressDialog = KProgressHUD.create(RegisterActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setMaxProgress(100)
                    .show();
            progressDialog.setProgress(90);

           RequestBody name= RequestBody.create(MediaType.parse("text/plain"),username);
           RequestBody email_= RequestBody.create(MediaType.parse("text/plain"),email);
           RequestBody pass_pass= RequestBody.create(MediaType.parse("text/plain"),pass);
           RequestBody user_type= RequestBody.create(MediaType.parse("text/plain"),usertype);

           RequestBody dob= RequestBody.create(MediaType.parse("text/plain"),"1998-6-2");
           RequestBody location= RequestBody.create(MediaType.parse("text/plain"),"location");
           RequestBody speciality= RequestBody.create(MediaType.parse("text/plain"),"speciality");
           RequestBody total_exp= RequestBody.create(MediaType.parse("text/plain"),"1");
           RequestBody address= RequestBody.create(MediaType.parse("text/plain"),"address");



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
            Call<Example> call = api.Register(MultiPartHelperClass.getMultipartData(new File(filePatadhar), "image"),name,email_,pass_pass,user_type,dob,location,speciality,total_exp,address);

            //finally performing the call
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(retrofit2.Call<Example>call, retrofit2.Response<Example> response) {
                    Toast.makeText(RegisterActivity.this, "Register sucessfully", Toast.LENGTH_SHORT).show();

                     String username = response.body().getData().getName();
                     String userId = String.valueOf(response.body().getData().getId());
                     String  email= response.body().getData().getEmail();
                     String user_tye = response.body().getData().getUserType();
                     String location = response.body().getData().getLocation();

                    progressDialog.dismiss();
                    User user=new User(userId,username,email,user_tye,location,"","");
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);


                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);



                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), "Something Went Wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            });
        }
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
