/**
 * @author Valkesh patel
 */
package com.dr.app.appointment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dr.app.R;
import com.dr.app.model.DoctorTimeModel;
import com.dr.app.utility.PagingBaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * An array adapter that knows how to render views when given CustomData classes
 */
public class DoctorTimeAdapter extends PagingBaseAdapter<String> {
    private LayoutInflater mInflater;
    Context mContext;
    Holder holder;
    Activity mContext_act;
    public ArrayList<DoctorTimeModel> mList = new ArrayList<DoctorTimeModel>();
    ArrayList<Boolean> inProgress = new ArrayList<Boolean>();


    public DoctorTimeAdapter(Activity context,
                             ArrayList<DoctorTimeModel> mList) {

        mContext = context;
        mContext_act = context;
        this.mList = mList;

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for (int x = 0; x < mList.size(); x++) {
            inProgress.add(false);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.adapter_doctor_time, parent,
                    false);
            holder = new Holder();
            holder.LinearLayout1 = (LinearLayout) convertView.findViewById(R.id.LinearLayout1);
            holder.txtDayName = (TextView) convertView.findViewById(R.id.txtDayName);
            holder.txtStartTime = (TextView) convertView.findViewById(R.id.txtStartTime);
            holder.txtTo = (TextView) convertView.findViewById(R.id.txtTo);
            holder.txtEndTime = (TextView) convertView.findViewById(R.id.txtEndTime);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        if (dayOfTheWeek.equalsIgnoreCase(mList.get(position).getDayName())) {
            holder.LinearLayout1.setBackgroundColor(Color.parseColor("#d2d2d2"));
        }else {
            holder.LinearLayout1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.txtDayName.setText(mList.get(position).getDayName());
        holder.txtStartTime.setText(mList.get(position).getDayStartTime());
        holder.txtEndTime.setText(mList.get(position).getDayEndTime());
        holder.txtTo.setText(mList.get(position).getDayTo());

        return convertView;
    }

    /**
     * View holder for the views we need access to
     */

    public static class Holder {

        TextView txtDayName, txtStartTime, txtTo, txtEndTime;
        LinearLayout LinearLayout1;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
