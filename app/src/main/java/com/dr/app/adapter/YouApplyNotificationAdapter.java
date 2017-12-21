package com.dr.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dr.app.R;
import com.dr.app.model.YouApplyNotificationModel;
import com.dr.app.utility.PagingBaseAdapter;
import com.dr.app.widgets.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class YouApplyNotificationAdapter extends PagingBaseAdapter<String> {
    private LayoutInflater mInflater;
    Context mContext;
    Activity mContext_act;
    public ArrayList<YouApplyNotificationModel> mList = new ArrayList<YouApplyNotificationModel>();
    private OnDetailsItem mdListener;
    ArrayList<Boolean> inProgress = new ArrayList<Boolean>();
    ImageLoader imageLoader = null;
    DisplayImageOptions options;
    DisplayImageOptions options_corner;

    Holder holder;

    public interface OnDetailsItem {
        void onDetailsListener(ArrayList<YouApplyNotificationModel> mList, int pos);
    }

    public YouApplyNotificationAdapter(Activity context, OnDetailsItem mdListener,
                                       ArrayList<YouApplyNotificationModel> mList) {

        mContext = context;
        mContext_act = context;
        this.mList = mList;
        this.mdListener = mdListener;

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.profile)
                .showImageForEmptyUri(R.drawable.profile)
                .showImageOnFail(R.drawable.profile).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        options_corner = new DisplayImageOptions.Builder()
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
            convertView = mInflater.inflate(R.layout.adapter_you_apply_notification, parent,
                    false);
            holder = new Holder();
            holder.imgUserImage = (CircleImageView) convertView.findViewById(R.id.imgUserImage);
            holder.imgEvent = (ImageView) convertView.findViewById(R.id.imgEvent);
            holder.imgPreview = (ImageView) convertView.findViewById(R.id.imgPreview);
            holder.imgStatus = (ImageView) convertView.findViewById(R.id.imgStatus);
            holder.txtUserName = (TextView) convertView.findViewById(R.id.txtUserName);
            holder.txtMessages = (TextView) convertView.findViewById(R.id.txtMessages);
            holder.lnMianLayer = (LinearLayout) convertView.findViewById(R.id.lnMianLayer);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }


        if (mList.get(position).is_online()) {
            holder.imgStatus.setImageResource(R.drawable.ic_active_circle);
        } else {
            holder.imgStatus.setImageResource(R.drawable.ic_inactive_circle);
        }
        holder.txtUserName.setText(mList.get(position).getUser_name());
        // holder.txtMessages.setText(mList.get(position).getMessages());
        holder.txtMessages.setText(Html.fromHtml(mList.get(position).getMessages()));
        if (mList.get(position).getUser_image() == null && mList.get(position).getUser_image().equals("") &&
                mList.get(position).getUser_image().length() > 6) {
        } else {
            imageLoader.displayImage(mList.get(position).getUser_image(),
                    holder.imgUserImage, options, new SimpleImageLoadingListener() {
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
        if (mList.get(position).getEvent_image() == null && mList.get(position).getEvent_image().equals("") &&
                mList.get(position).getEvent_image().length() > 6) {
        } else {
            imageLoader.displayImage(mList.get(position).getEvent_image(),
                    holder.imgEvent, options_corner, new SimpleImageLoadingListener() {
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
        if (mList.get(position).getType().equalsIgnoreCase("Rejected")) {
            holder.imgPreview.setVisibility(View.VISIBLE);
        } else {
            holder.imgPreview.setVisibility(View.GONE);
        }

        holder.lnMianLayer.setOnClickListener(new View.OnClickListener() {

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

    private static class Holder {

        CircleImageView imgUserImage;
        ImageView imgEvent, imgStatus, imgPreview;
        TextView txtUserName, txtMessages;
        LinearLayout lnMianLayer;

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
