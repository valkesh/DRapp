/**
 * @author Valkesh patel
 */
package com.dr.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dr.app.R;
import com.dr.app.model.EventProfileModel;
import com.dr.app.utility.PagingBaseAdapter;
import com.dr.app.utility.Pref;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * An array adapter that knows how to render views when given CustomData classes
 */
public class EventProfileAdapter extends PagingBaseAdapter<String> {
    private LayoutInflater mInflater;
    Context mContext;
    Holder holder;
    Activity mContext_act;
    public ArrayList<EventProfileModel> mList = new ArrayList<EventProfileModel>();
    private OnDetailsItem mdListener;
    ArrayList<Boolean> inProgress = new ArrayList<Boolean>();
    ImageLoader imageLoader = null;
    DisplayImageOptions options;


    public interface OnDetailsItem {
        void onDetailsListener(ArrayList<EventProfileModel> mList, int pos);

        void onEditListener(ArrayList<EventProfileModel> mList, int pos, boolean isEdit);
    }

    public EventProfileAdapter(Activity context, OnDetailsItem mdListener,
                               ArrayList<EventProfileModel> mList) {

        mContext = context;
        mContext_act = context;
        this.mList = mList;
        this.mdListener = mdListener;

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(10))
                .showImageOnLoading(R.drawable.img_section)
                .showImageForEmptyUri(R.drawable.img_section)
                .showImageOnFail(R.drawable.img_section).cacheInMemory(true)
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
            convertView = mInflater.inflate(R.layout.adapter_event_profile, parent,
                    false);
            holder = new Holder();
            holder.imgEventPic = (ImageView) convertView.findViewById(R.id.imgEventPic);
            holder.imgActive = (ImageView) convertView.findViewById(R.id.imgActive);
            holder.txtEventName = (TextView) convertView.findViewById(R.id.txtEventName);
            holder.txtEventDateTime = (TextView) convertView.findViewById(R.id.txtEventDateTime);
            holder.txtEventSport = (TextView) convertView.findViewById(R.id.txtEventSport);
            holder.lnEventLayer = (LinearLayout) convertView.findViewById(R.id.lnEventLayer);
            holder.imgEditEvent = (ImageView) convertView.findViewById(R.id.imgEditEvent);
            holder.imgReEvent = (ImageView) convertView.findViewById(R.id.imgReEvent);
            holder.lnAction = (LinearLayout) convertView.findViewById(R.id.lnAction);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text

//        holder.txtSportName.setText(mList.get(position).getSport_name());
//        holder.txtSportEventCount.setText(mList.get(position).getSport_no_vacancy());
//        if (mList.get(position).getIs_fav() == 1) {
//            holder.image_favourite.setImageResource(R.drawable.favourite_icon);
//        } else {
//            holder.image_favourite.setImageResource(R.drawable.unfavourite_icon);
//        }
        holder.txtEventName.setText(mList.get(position).getEvent_title());
        holder.txtEventDateTime.setText(mList.get(position).getEvent_date_time());
        holder.txtEventSport.setText(mList.get(position).getEvent_sport_name());
        holder.txtEventSport.setSelected(true);

        holder.imgEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdListener.onEditListener(mList, position, true);
            }
        });
        holder.imgReEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdListener.onEditListener(mList, position, false);
            }
        });
        if (mList.get(position).isEvent_status()) {
            holder.imgActive.setVisibility(View.VISIBLE);
        } else {
            holder.imgActive.setVisibility(View.GONE);
        }
        if (Pref.getIsLogin() && mList.get(position).getUser_id() == Integer.parseInt(Pref.getUserID())) {

            holder.lnAction.setVisibility(View.VISIBLE);
        } else {
            holder.lnAction.setVisibility(View.GONE);
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
        return convertView;
    }

    /**
     * View holder for the views we need access to
     */

    public static class Holder {

        ImageView imgActive, imgEventPic;
        TextView txtEventName, txtEventDateTime, txtEventSport;
        LinearLayout lnEventLayer;
        ImageView imgEditEvent, imgReEvent;
        LinearLayout lnAction;
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
