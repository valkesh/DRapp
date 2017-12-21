package com.dr.app.utility;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    public CustomSpinnerAdapter(Context context, int resource,
                                List<String> objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return super.getItem(position);
    }

    @Override
    public int getPosition(String item) {
        // TODO Auto-generated method stub
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (position == 0) {

        }
        return super.getView(position, convertView, parent);

    }


}