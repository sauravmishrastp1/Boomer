package com.expertwebtech.boomer.pojo;

public class Home {

    private  String id,name,image,speciality,experience,like,comment,share;
    private int picture;

    public Home(String id, String name, String image, String speciality, String experience, String like, String comment, String share) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.speciality = speciality;
        this.experience = experience;
        this.like = like;
        this.comment = comment;
        this.share = share;
    }

    public Home(String name, int picture) {
        this.name = name;
        this.picture = picture;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }
}
