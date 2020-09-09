package com.expertwebtech.boomer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.expertwebtech.boomer.R;
import com.expertwebtech.boomer.pojo.Category_Model;
import com.expertwebtech.boomer.pojo.Spinner_ItemModel;

import java.util.ArrayList;


public class Spinner_ItemAdapter2 extends ArrayAdapter {


    public Spinner_ItemAdapter2(Context context, ArrayList<Category_Model> customList) {

        super(context,  0, customList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_layout2,null);
        }

        Category_Model spinnerItemModel= (Category_Model) getItem(position);


        TextView textView=convertView.findViewById(R.id.title1);

        if (spinnerItemModel!=null) {

            textView.setText(spinnerItemModel.getSpinnerItemName());
        }

        return convertView;


    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dropdown_layout2,null);
        }

        Category_Model spinnerItemModel= (Category_Model) getItem(position);

        TextView textView=convertView.findViewById(R.id.title);

        if (spinnerItemModel!=null) {
            textView.setText(spinnerItemModel.getSpinnerItemName());
        }

        return convertView;
    }
}
