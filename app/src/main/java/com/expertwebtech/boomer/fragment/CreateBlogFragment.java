package com.expertwebtech.boomer.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.adapter.PlanAdapter;
import com.expertwebtech.boomer.adapter.Spinner_ItemAdapter;
import com.expertwebtech.boomer.adapter.Spinner_ItemAdapter2;
import com.expertwebtech.boomer.constant.FileUtils;
import com.expertwebtech.boomer.constant.MultiPartHelperClass;
import com.expertwebtech.boomer.constant.SharedPrefManager;
import com.expertwebtech.boomer.constant.VolleySingleton;
import com.expertwebtech.boomer.pojo.Category_Model;
import com.expertwebtech.boomer.pojo.Data;
import com.expertwebtech.boomer.pojo.Plans;
import com.expertwebtech.boomer.pojo.Spinner_ItemModel;
import com.expertwebtech.boomer.retrofitfileupload.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


public class CreateBlogFragment extends Fragment {
       EditText subjecteT,bloget,description_et;
       Button post_now;
    private List<Category_Model> genderlist = new ArrayList<>();
    private Spinner categoryet;
    String gender;
    String usertype,id,cat_name,active;
    private Object Api;
    ImageView upload_pic,blog_img;
    private static final int GALLERY_IMAGE = 1;
    String filePatadhar = "";
    byte Pancard[] = "00.00.00".getBytes();
    String subject,blogtitle,discription,cat_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_blog, container, false);
        categoryet = view.findViewById(R.id.spinner);
        post_now = view.findViewById(R.id.post_now);
        subjecteT = view.findViewById(R.id.subject);
        bloget = view.findViewById(R.id.blog_title);
        description_et = view.findViewById(R.id.description_txt);
        blog_img = view.findViewById(R.id.imageview);
        upload_pic = view.findViewById(R.id.updloadimg);
        upload_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_IMAGE);


            }
        });
        post_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        category_apicall();

        if (categoryet != null) {


            categoryet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Category_Model model = (Category_Model) parent.getSelectedItem();

                    gender = model.getSpinnerItemName();
                    cat_id = model.getId();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        return view;
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
                blog_img.setImageBitmap(bitmap);
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
                Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void category_apicall(){
      //  Queue queue = (Queue) Volley.newRequestQueue(getContext());
        String url ="http://xpertwebtech.in/bloom/public/api/category";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String status = obj.getString("status_code");
                    if(status.equals("200")){
                        JSONArray dataArray=obj.getJSONArray("data");



                        for (int i=0;i<dataArray.length();i++)
                        {
                            JSONObject object=dataArray.getJSONObject(i);

                             id=object.getString("id");
                             cat_name=object.getString("name");
                             active=object.getString("active");
                           // Toast.makeText(getContext(), ""+id, Toast.LENGTH_SHORT).show();

                            genderlist.add(new Category_Model(cat_name,id,active));
                             Spinner_ItemAdapter2 genderadapter = new Spinner_ItemAdapter2(getContext(), (ArrayList<Category_Model>) genderlist);
                            categoryet.setAdapter(genderadapter);

                        }


                    }else {
                        Toast.makeText(getContext(), "Not found", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }


    private void uploadFile() {
        subject = subjecteT.getText().toString();
        blogtitle = bloget.getText().toString();
        discription = description_et.getText().toString();
        if(filePatadhar.equals("")){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(upload_pic);
        }
       else if(subject.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(subjecteT);
            subjecteT.setError("Enter Subject");
            subjecteT.setFocusable(true);
        }else if(blogtitle.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(bloget);
            bloget.setError("Enter blog tittle");
            bloget.setFocusable(true);

        }else if(discription.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(description_et);
            description_et.setError("Enter Description");
            description_et.setFocusable(true);
        }else if(cat_id.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(categoryet);
        }else {
            final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setMaxProgress(100)
                    .show();
            progressDialog.setProgress(90);
            Toast.makeText(getContext(), ""+cat_id, Toast.LENGTH_SHORT).show();

            RequestBody blogtitle_= RequestBody.create(MediaType.parse("text/plain"),blogtitle);
            RequestBody discription_= RequestBody.create(MediaType.parse("text/plain"),discription);
            RequestBody subject_= RequestBody.create(MediaType.parse("text/plain"),subject);
            RequestBody user_id= RequestBody.create(MediaType.parse("text/plain"),"22");
            RequestBody cat_id_= RequestBody.create(MediaType.parse("text/plain"),cat_id);

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
            Call<Data> call = api.Createblog(MultiPartHelperClass.getMultipartData(new File(filePatadhar), "image"), blogtitle_, discription_, user_id, cat_id_, subject_);

            //finally performing the call
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(retrofit2.Call<Data> call, retrofit2.Response<Data> response) {

                    Toast.makeText(getContext(), "Uploaded Successfully"+response.body(), Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();


                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {

                    Toast.makeText(getContext(), "Something Went Wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            });
        }
    }





    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}