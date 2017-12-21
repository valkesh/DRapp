package com.dr.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.dr.app.R;
import com.dr.app.model.EventMoreModel;
import com.dr.app.model.SearchEventModel;
import com.dr.app.model.SportModel;
import com.dr.app.utility.Golly;
import com.dr.app.utility.PagingBaseAdapter;
import com.dr.app.utility.PagingListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchEventListAdapter extends PagingBaseAdapter<String> {
    private LayoutInflater mInflater;
    Context mContext;
    Holder holder;
    Activity act;
    private ArrayList<SearchEventModel> mList;
    private OnDetailsItem mdListener;
    ImageLoader imageLoader = null;
    DisplayImageOptions options;

    public interface OnDetailsItem {
        void onDetailsListener(ArrayList<SearchEventModel> searchEventModels, int pos);
    }

    public SearchEventListAdapter(Activity context, OnDetailsItem mdListener,
                                  ArrayList<SearchEventModel> searchEventModels) {

        this.mList = (ArrayList<SearchEventModel>) searchEventModels;
        this.act = context;
        this.mdListener = mdListener;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(act));
        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(10))
                .showImageOnLoading(R.drawable.img_section)
                .showImageForEmptyUri(R.drawable.img_section)
                .showImageOnFail(R.drawable.img_section).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        for (int x = 0; x < mList.size(); x++) {
            // inProgress.add(false);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.adapter_search_event, parent,
                    false);
            holder = new Holder();
            holder.imgEventPic = (ImageView) convertView.findViewById(R.id.imgEventPic);
            holder.imgActive = (ImageView) convertView.findViewById(R.id.imgActive);
            holder.txtEventName = (TextView) convertView.findViewById(R.id.txtEventName);
            holder.txtEventDateTime = (TextView) convertView.findViewById(R.id.txtEventDateTime);
            holder.txtEventSport = (TextView) convertView.findViewById(R.id.txtEventSport);
            holder.lnEventLayer = (LinearLayout) convertView.findViewById(R.id.lnEventLayer);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text
        holder.txtEventName.setText(mList.get(position).getEvent_title());
        holder.txtEventDateTime.setText(mList.get(position).getEvent_date() + " " + mList.get(position).getEvent_time());
        holder.txtEventSport.setText(mList.get(position).getEvent_sport_name());

        if (mList.get(position).getEvent_status() == 1) {
            holder.imgActive.setVisibility(View.VISIBLE);
        } else {
            holder.imgActive.setVisibility(View.GONE);
        }
        if (mList.get(position).getEvent_image() == null && mList.get(position).getEvent_image().equals("") &&
                mList.get(position).getEvent_image().length() > 6) {
        } else {
            imageLoader.displayImage(mList.get(position).getEvent_image(),
                    holder.imgEventPic, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            // holder.progressBar.setProgress(0);
                            // holder.progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view,
                                                    FailReason failReason) {
                            // holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view,
                                                      Bitmap loadedImage) {
                            // holder.progressBar.setVisibility(View.GONE);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view,
                                                     int current, int total) {
                            // holder.progressBar.setProgress(Math.round(100.0f
                            // * current / total));
                        }
                    });
        }

        holder.lnEventLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdListener.onDetailsListener(mList, position);
            }
        });


//        Animation animationY = new TranslateAnimation(0, 0,
//                holder.rel_image_video_thumb.getHeight() / 4, 0);
//        animationY.setDuration(1000);
//        convertView.startAnimation(animationY);
//        animationY = null;

        return convertView;
    }

    /**
     * View holder for the views we need access to
     */

    private static class Holder {

        ImageView imgActive, imgEventPic;
        TextView txtEventName, txtEventDateTime, txtEventSport;
        LinearLayout lnEventLayer;
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
