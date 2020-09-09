package com.expertwebtech.boomer.pojo;

public class Category_Model {

    private String spinnerItemName;
    private String id;
    private String active;

    public Category_Model(String spinnerItemName, String id, String active) {
        this.spinnerItemName = spinnerItemName;
        this.id = id;
        this.active = active;
    }

    public String getSpinnerItemName() {
        return spinnerItemName;
    }

    public void setSpinnerItemName(String spinnerItemName) {
        this.spinnerItemName = spinnerItemName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
