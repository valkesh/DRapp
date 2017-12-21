package com.dr.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dr.app.R;
import com.dr.app.model.YouEventNotificationModel;
import com.dr.app.utility.PagingBaseAdapter;
import com.dr.app.widgets.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class YouEventNotificationAdapter extends PagingBaseAdapter<String> {
    private LayoutInflater mInflater;
    Context mContext;
    Holder holder;
    Activity mContext_act;
    public ArrayList<YouEventNotificationModel> mList = new ArrayList<YouEventNotificationModel>();
    private OnDetailsItem mdListener;
    ArrayList<Boolean> inProgress = new ArrayList<Boolean>();
    ImageLoader imageLoader = null;
    DisplayImageOptions options;


    public interface OnDetailsItem {
        void onDetailsListener(ArrayList<YouEventNotificationModel> mList, int pos);

        void onAccept(ArrayList<YouEventNotificationModel> mList, int pos);

        void onReject(ArrayList<YouEventNotificationModel> mList, int pos);

        void onMessage(ArrayList<YouEventNotificationModel> mList, int pos);

        void onProfile(ArrayList<YouEventNotificationModel> mList, int pos);
    }

    public YouEventNotificationAdapter(Activity context, YouEventNotificationAdapter.OnDetailsItem mdListener,
                                       ArrayList<YouEventNotificationModel> mList) {

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

        for (int x = 0; x < mList.size(); x++) {
            inProgress.add(false);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.adapter_you_event_notification, parent,
                    false);
            holder = new YouEventNotificationAdapter.Holder();
            holder.txtUserName = (TextView) convertView.findViewById(R.id.txtUserName);
            holder.txtmessages = (TextView) convertView.findViewById(R.id.txtmessages);
            holder.imgUser = (CircleImageView) convertView.findViewById(R.id.imgUser);
            holder.imgOnline = (ImageView) convertView.findViewById(R.id.imgOnline);
            holder.img_message = (ImageView) convertView.findViewById(R.id.img_message);
            holder.img_reject = (ImageView) convertView.findViewById(R.id.img_reject);
            holder.img_accept = (ImageView) convertView.findViewById(R.id.img_accept);
            holder.lnEventLayer = (LinearLayout) convertView.findViewById(R.id.lnEventLayer);
            holder.frmProfilePicture = (FrameLayout) convertView.findViewById(R.id.frmProfilePicture);

            convertView.setTag(holder);
        } else {
            holder = (YouEventNotificationAdapter.Holder) convertView.getTag();
        }

        if (mList.get(position).is_online()) {
            holder.imgOnline.setImageResource(R.drawable.ic_active_circle);
        } else {
            holder.imgOnline.setImageResource(R.drawable.ic_inactive_circle);
        }

        if (mList.get(position).getType() == 0) {
            holder.img_message.setVisibility(View.VISIBLE);
            holder.img_reject.setVisibility(View.VISIBLE);
            holder.img_accept.setVisibility(View.VISIBLE);
        } else if (mList.get(position).getType() == 1) {
            holder.img_message.setVisibility(View.VISIBLE);
            holder.img_reject.setVisibility(View.GONE);
            holder.img_accept.setVisibility(View.GONE);
        } else if (mList.get(position).getType() == 2) {
            holder.img_message.setVisibility(View.VISIBLE);
            holder.img_reject.setVisibility(View.GONE);
            holder.img_accept.setVisibility(View.GONE);
        } else {
            holder.img_message.setVisibility(View.GONE);
            holder.img_reject.setVisibility(View.GONE);
            holder.img_accept.setVisibility(View.GONE);
        }

        holder.txtUserName.setText(mList.get(position).getUser_name());
        // holder.txtmessages.setText(mList.get(position).getMessages());
        holder.txtmessages.setText(Html.fromHtml(mList.get(position).getMessages()));
        if (mList.get(position).getUser_image() == null && mList.get(position).getUser_image().equals("") &&
                mList.get(position).getUser_image().length() > 6) {
        } else {
            imageLoader.displayImage(mList.get(position).getUser_image(),
                    holder.imgUser, options, new SimpleImageLoadingListener() {
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
        holder.img_message.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mdListener.onMessage(mList, position);
            }
        });
        holder.img_reject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mdListener.onReject(mList, position);
            }
        });
        holder.img_accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mdListener.onAccept(mList, position);

            }
        });
        holder.frmProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdListener.onProfile(mList, position);
            }
        });
        return convertView;
    }

    /**
     * View holder for the views we need access to
     */

    private static class Holder {

        CircleImageView imgUser;
        ImageView imgOnline, img_accept, img_reject, img_message;
        TextView txtUserName, txtmessages;
        LinearLayout lnEventLayer;
        FrameLayout frmProfilePicture;

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
