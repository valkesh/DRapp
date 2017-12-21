/**
 * @author Valkesh patel
 */
package com.dr.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.dr.app.R;
import com.dr.app.model.CategoriesModel;
import com.dr.app.model.ContryCodeModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.PagingBaseAdapter;
import com.dr.app.utility.Pref;
import com.dr.app.widgets.CTextViewjustsport;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * An array adapter that knows how to render views when given CustomData classes
 */
public class CountryCodeAdapter extends PagingBaseAdapter<String> {
    private LayoutInflater mInflater;
    Context mContext;
    Holder holder;
    Activity mContext_act;
    int mSelectedItem = -1;
    private int lastPosition = 0;
    public ArrayList<ContryCodeModel> mList = new ArrayList<ContryCodeModel>();
    public OnDetailsItem mdListener;
    ArrayList<Boolean> inProgress = new ArrayList<Boolean>();
    ImageLoader imageLoader = null;
    DisplayImageOptions options;

    public interface OnDetailsItem {
        void onDetailsListener(String country_name, String country_code, ArrayList<ContryCodeModel> mList, int pos);
    }

    public CountryCodeAdapter(Activity context, OnDetailsItem mdListener,
                              ArrayList<ContryCodeModel> mList) {

        mContext = context;
        mContext_act = context;
        this.mList = mList;
        this.mdListener = mdListener;

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        for (int x = 0; x < mList.size(); x++) {
            inProgress.add(false);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.adapter_contry_code, parent,
                    false);
            holder = new Holder();
            convertView.setTag(holder);
            holder.labelContry = (CTextViewjustsport) convertView.findViewById(R.id.labelContry);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.labelContry.setText(mList.get(position).getCountry_name());
        holder.labelContry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdListener.onDetailsListener(mList.get(position)
                        .getCountry_name(), mList.get(position)
                        .getCountry_code(),mList,position );
            }
        });
        return convertView;
    }

    /**
     * View holder for the views we need access to
     */

    private static class Holder {
        CTextViewjustsport labelContry;
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
