package com.dr.app.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dr.app.R;
import com.dr.app.model.EventMoreModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class EventMoreAdapter extends RecyclerView.Adapter<EventMoreAdapter.MyViewHolder> {

    private ArrayList<EventMoreModel> eventMoreModels;
    private OnDetailsItem mdListener;

    Activity act;
    ImageLoader imageLoader = null;
    DisplayImageOptions options;

    public EventMoreAdapter(Activity act, OnDetailsItem mdListener, List<EventMoreModel> homeBeachVollyBallAdapters) {
        this.eventMoreModels = (ArrayList<EventMoreModel>) homeBeachVollyBallAdapters;
        this.act = act;
        this.mdListener = mdListener;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(act));
        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(10))
                .showImageOnLoading(R.drawable.img_section)
                .showImageForEmptyUri(R.drawable.img_section)
                .showImageOnFail(R.drawable.img_section).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public interface OnDetailsItem {
        void onDetailsListener(ArrayList<EventMoreModel> mList, int pos);

        void onCreatePost();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgSportEventPic, imgFirstImage;
        TextView txtVacancyCount, txtDate, txtTime;
        TextView txtEventName, txtEventCount;
        LinearLayout LinearLayout1, imgMainLayer, lnFirstIndex, lnLastIndex, lnFirstImage;

        public MyViewHolder(View convertView) {
            super(convertView);
            txtVacancyCount = (TextView) convertView.findViewById(R.id.txtVacancyCount);
            imgSportEventPic = (ImageView) convertView.findViewById(R.id.imgSportEventPic);
            imgFirstImage = (ImageView) convertView.findViewById(R.id.imgFirstImage);
            txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            txtTime = (TextView) convertView.findViewById(R.id.txtTime);
            txtEventName = (TextView) convertView.findViewById(R.id.txtEventName);
            txtEventCount = (TextView) convertView.findViewById(R.id.txtEventCount);
            LinearLayout1 = (LinearLayout) convertView.findViewById(R.id.LinearLayout1);
            imgMainLayer = (LinearLayout) convertView.findViewById(R.id.imgMainLayer);
            lnFirstIndex = (LinearLayout) convertView.findViewById(R.id.lnFirstIndex);
            lnLastIndex = (LinearLayout) convertView.findViewById(R.id.lnLastIndex);
            lnFirstImage = (LinearLayout) convertView.findViewById(R.id.lnFirstImage);
            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                    //  mdListener.onDetailsListener(homeBeachVollyBallAdapters, getPosition());
            }
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_event_more, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        EventMoreModel movie = eventMoreModels.get(position);

        if (position == eventMoreModels.size() - 1) {
            holder.imgMainLayer.setVisibility(View.GONE);
            holder.lnFirstIndex.setVisibility(View.GONE);
            holder.lnLastIndex.setVisibility(View.VISIBLE);
            holder.lnLastIndex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mdListener.onCreatePost();
                }
            });
        } else if (position == (eventMoreModels.size() - eventMoreModels.size())) {
            holder.lnFirstIndex.setVisibility(View.VISIBLE);
            holder.imgMainLayer.setVisibility(View.GONE);
            holder.lnLastIndex.setVisibility(View.GONE);
            holder.txtEventCount.setText(eventMoreModels.get(0).getTotal_event_count());
            holder.txtEventName.setText(eventMoreModels.get(0).getGame_name());
            holder.imgFirstImage.setImageResource(eventMoreModels.get(0).getBackground_image());

          //  holder.imgFirstImage.set(R.drawable.ic_main_hockey);
//            if (eventMoreModels.get(0).getBackground_image() == 0) {
//                holder.lnFirstImage.setBackgroundResource(R.drawable.ic_main_hockey);
//            } else if (eventMoreModels.get(0).getBackground_image() == 1) {
//                holder.lnFirstImage.setBackgroundResource(R.drawable.ic_main_basket_ball);
//            } else if (eventMoreModels.get(0).getBackground_image() == 2) {
//                holder.lnFirstImage.setBackgroundResource(R.drawable.ic_main_vollyball);
//            } else if (eventMoreModels.get(0).getBackground_image() == 3) {
//                holder.lnFirstImage.setBackgroundResource(R.drawable.ic_main_football_old);
//            } else if (eventMoreModels.get(0).getBackground_image() == 4) {
//                holder.lnFirstImage.setBackgroundResource(R.drawable.ic_main_baseball);
//            } else if (eventMoreModels.get(0).getBackground_image() == 5) {
//                holder.lnFirstImage.setBackgroundResource(R.drawable.ic_main_soccer);
//            }

        } else {
            holder.imgMainLayer.setVisibility(View.VISIBLE);
            holder.lnFirstIndex.setVisibility(View.GONE);
            holder.lnLastIndex.setVisibility(View.GONE);
            if (eventMoreModels.get(position).getSport_event_image() == null && eventMoreModels.get(position).getSport_event_image().equals("") &&
                    eventMoreModels.get(position).getSport_event_image().length() > 6) {
            } else {
                imageLoader.displayImage(eventMoreModels.get(position).getSport_event_image(),
                        holder.imgSportEventPic, options, new SimpleImageLoadingListener() {
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
            holder.txtVacancyCount.setText(eventMoreModels.get(position).getSport_event_no_vacancy());
            holder.txtDate.setText(eventMoreModels.get(position).getSport_event_date());
            holder.txtTime.setText(eventMoreModels.get(position).getSport_event_time());
            holder.imgMainLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mdListener.onDetailsListener(eventMoreModels, eventMoreModels.get(position).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return eventMoreModels.size();
    }
}
