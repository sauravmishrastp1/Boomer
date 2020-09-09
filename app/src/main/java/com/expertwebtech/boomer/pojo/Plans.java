package com.expertwebtech.boomer.pojo;

public class Plans {

    private String id,imageicon,price,day,title,backgroundColor;
    private int picture;

    public Plans(String id, String imageicon, String price, String day, String title) {
        this.id = id;
        this.imageicon = imageicon;
        this.price = price;
        this.day = day;
        this.title = title;
    }

    public Plans(String id, String price,String title, int picture,String day,String backgroundColor) {
        this.id = id;
        this.price = price;
        this.picture = picture;
        this.backgroundColor=backgroundColor;
        this.title=title;
        this.day=day;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
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

    public String getImageicon() {
        return imageicon;
    }

    public void setImageicon(String imageicon) {
        this.imageicon = imageicon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
