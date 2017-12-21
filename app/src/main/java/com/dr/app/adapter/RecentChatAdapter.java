package com.dr.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dr.app.R;
import com.dr.app.model.ChatMessageNotification;
import com.dr.app.utility.PagingBaseAdapter;
import com.dr.app.widgets.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class RecentChatAdapter extends PagingBaseAdapter<String> implements Filterable {
    private LayoutInflater mInflater;
    Context mContext;
    Activity mContext_act;
    public ArrayList<ChatMessageNotification> mList = new ArrayList<ChatMessageNotification>();
    public ArrayList<ChatMessageNotification> search_mlist = new ArrayList<ChatMessageNotification>();
    private OnDetailsItem mdListener;
    ArrayList<Boolean> inProgress = new ArrayList<Boolean>();
    ImageLoader imageLoader = null;
    DisplayImageOptions options;

    Holder holder;


    public interface OnDetailsItem {
        void onDetailsListener(ArrayList<ChatMessageNotification> mList, int pos);
    }

    public RecentChatAdapter(Activity context, OnDetailsItem mdListener,
                             ArrayList<ChatMessageNotification> mList) {

        mContext = context;
        mContext_act = context;
        this.mList = mList;
        this.search_mlist = mList;
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
            convertView = mInflater.inflate(R.layout.adapter_recent_chat, parent,
                    false);
            holder = new Holder();
            holder.imgUserImage = (CircleImageView) convertView.findViewById(R.id.imgUserImage);
            holder.imgStatus = (ImageView) convertView.findViewById(R.id.imgStatus);
            holder.txtUserName = (TextView) convertView.findViewById(R.id.txtUserName);
            holder.txtMessages = (TextView) convertView.findViewById(R.id.txtMessages);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.lnMianLayer = (LinearLayout) convertView.findViewById(R.id.lnMianLayer);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (mList.get(position).getChatMessages().isUser_status()) {
            holder.imgStatus.setImageResource(R.drawable.ic_active_circle);
        } else {
            holder.imgStatus.setImageResource(R.drawable.ic_inactive_circle);
        }

//        holder.txtDate.setText(ChatComman.printDifference(mContext, mList.get(position).getChatMessages().getTimestamp()));
        holder.txtUserName.setText(mList.get(position).getChatMessages().getName());
        holder.txtMessages.setText(mList.get(position).getChatMessages().getMessage());
        try {
            if (mList.get(position).getChatMessages().getOtherUserImage() == null && mList.get(position).getChatMessages().getOtherUserImage().equals("") &&
                    mList.get(position).getChatMessages().getOtherUserImage().length() > 6) {
            } else {
                imageLoader.displayImage(mList.get(position).getChatMessages().getOtherUserImage(),
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
        } catch (Exception p) {
            p.printStackTrace();
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
        ImageView imgStatus;
        TextView txtUserName, txtMessages, txtDate;
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

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mList = (ArrayList<ChatMessageNotification>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                System.out.println("==con===" + constraint);
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<ChatMessageNotification> FilteredArrList = new ArrayList<ChatMessageNotification>();

                if (search_mlist == null) {
                    search_mlist = new ArrayList<ChatMessageNotification>(mList); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = search_mlist.size();
                    results.values = search_mlist;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < search_mlist.size(); i++) {
                        String data = search_mlist.get(i).getChatMessages().getName().toLowerCase();
                        if (data.contains(constraint.toString())) {
//                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(search_mlist.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    public String getDateFormate(String date) {
//        date = ChatComman.getFormater(date);
        return date;
    }
}






