package com.expertwebtech.boomer.retrofitfileupload;

import com.expertwebtech.boomer.pojo.Create_Post_Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("massage")
    @Expose
    private String massage;
    @SerializedName("data")
    @Expose
    private Create_Post_Model createPostModel;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Create_Post_Model getCreatePostModel() {
        return createPostModel;
    }

    public void setCreatePostModel(Create_Post_Model createPostModel) {
        this.createPostModel = createPostModel;
    }
}
