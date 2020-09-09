package com.expertwebtech.boomer.retrofitfileupload;


import com.expertwebtech.boomer.pojo.Data;
import com.expertwebtech.boomer.pojo.Register_Data;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Belal on 10/5/2017.
 */

public interface Api {

    //the base URL for our API
    //make sure you are not using localhost
    //find the ip usinc ipconfig command
    String BASE_URL = "https://xpertwebtech.in/bloom/public/api/";

    //this is our multipart request
    //we have two parameters on is name and other one is description


    @Multipart
    @POST("createBlog")
    Call<Data> Createblog(@Part MultipartBody.Part image,
                                  @Part("name") RequestBody name,
                                  @Part("discription") RequestBody discription,
                                      @Part("user_id") RequestBody user_id,
                                      @Part("cat_id") RequestBody cat_id,
                                      @Part("subject") RequestBody subject);

    @Multipart
    @POST("register")
    Call<Register_Data> Register(@Part MultipartBody.Part image,
                                 @Part("name") RequestBody name,
                                 @Part("email") RequestBody email,
                                 @Part("password") RequestBody password,
                                 @Part("user_type") RequestBody user_type,
                                 @Part("dob")RequestBody dob,
                                 @Part("location")RequestBody location,
                                 @Part("speciality")RequestBody speciality,
                                 @Part("total_exp")RequestBody total_exp,
                                 @Part("address")RequestBody address);







}
