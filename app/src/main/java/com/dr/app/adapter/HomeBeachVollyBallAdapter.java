package com.dr.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.dr.app.R;
import com.dr.app.model.EventProfileModel;
import com.dr.app.model.SportEventModel;
import com.dr.app.utility.Golly;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeBeachVollyBallAdapter extends RecyclerView.Adapter<HomeBeachVollyBallAdapter.MyViewHolder> {

    private ArrayList<SportEventModel> sportEventModels;
    private OnDetailsItem mdListener;

    Activity act;
    ImageLoader imageLoader = null;
    DisplayImageOptions options;

    public HomeBeachVollyBallAdapter(Activity act , OnDetailsItem mdListener , List<SportEventModel> homeBeachVollyBallAdapters) {
        this.sportEventModels = (ArrayList<SportEventModel>) homeBeachVollyBallAdapters;
        this.act = act;
        this.mdListener = mdListener;
    }

    public interface OnDetailsItem {
        void onDetailsListener(ArrayList<SportEventModel> mList, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        ImageView imgSportEventPic;
        TextView txtVacancyCount , txtDate , txtTime;
        LinearLayout LinearLayout1;

        public MyViewHolder(View convertView) {
            super(convertView);

            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(act));
            options = new DisplayImageOptions.Builder()
                    .displayer(new RoundedBitmapDisplayer(10))
                    .showImageOnLoading(R.drawable.img_section)
                    .showImageForEmptyUri(R.drawable.img_section)
                    .showImageOnFail(R.drawable.img_section).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();

            txtVacancyCount = (TextView) convertView.findViewById(R.id.txtVacancyCount);
            imgSportEventPic = (ImageView) convertView.findViewById(R.id.imgSportEventPic);
            txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            txtTime = (TextView) convertView.findViewById(R.id.txtTime);
            LinearLayout1 = (LinearLayout) convertView.findViewById(R.id.LinearLayout1);
            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        switch (view.getId()){
//            case  R.id.imgEventPic:
//                    break;
                default:
                  //  mdListener.onDetailsListener(homeBeachVollyBallAdapters, getPosition());
                }
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_home_beach_volly_ball, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        SportEventModel movie = sportEventModels.get(position);

        if (sportEventModels.get(position).getSport_event_image() == null && sportEventModels.get(position).getSport_event_image().equals("") &&
                sportEventModels.get(position).getSport_event_image().length() > 6) {
        } else {
            imageLoader.displayImage(sportEventModels.get(position).getSport_event_image(),
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
        holder.txtVacancyCount.setText(sportEventModels.get(position).getSport_event_no_vacancy());
        holder.txtDate.setText(sportEventModels.get(position).getSport_event_date());
        holder.txtTime.setText(sportEventModels.get(position).getSport_event_time());
        holder.LinearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdListener.onDetailsListener(sportEventModels, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sportEventModels.size();
    }
}
