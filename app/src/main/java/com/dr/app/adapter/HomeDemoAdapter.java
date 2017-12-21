package com.dr.app.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dr.app.R;
import com.dr.app.model.EventModel;
import com.dr.app.model.HomeEventModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class HomeDemoAdapter extends RecyclerView.Adapter<HomeDemoAdapter.MyViewHolder> {

    private ArrayList<HomeEventModel> moviesList;
    private OnDetailsItem mdListener;
    private Activity activity;
    ImageLoader imageLoader = null;
    DisplayImageOptions options;

    public HomeDemoAdapter(Activity activity, OnDetailsItem mdListener, ArrayList<HomeEventModel> homeEventModels) {
        this.moviesList = homeEventModels;
        this.activity = activity;
        this.mdListener = mdListener;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_section)
                .showImageForEmptyUri(R.drawable.img_section)
                .showImageOnFail(R.drawable.img_section).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }


    public interface OnDetailsItem extends HomeDemoBeachVollyBallAdapter.OnDetailsItem {
        void onDetailsListener(ArrayList<HomeEventModel> mList, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecyclerView rvData;
        TextView txtGameName, txtGameEventNo;
        LinearLayout lnCountOne, lnMain;
        ImageView imgBackground;

        public MyViewHolder(View convertView) {
            super(convertView);
            rvData = (RecyclerView) convertView.findViewById(R.id.rvData);
            txtGameName = (TextView) convertView.findViewById(R.id.txtGameName);
            txtGameEventNo = (TextView) convertView.findViewById(R.id.txtGameEventNo);
            lnCountOne = (LinearLayout) convertView.findViewById(R.id.lnCountOne);
            imgBackground = (ImageView) convertView.findViewById(R.id.imgBackground);
            lnMain = (LinearLayout) convertView.findViewById(R.id.lnMain);
            lnCountOne.setOnClickListener(this);
            rvData.setHasFixedSize(true);
            rvData.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lnCountOne:
                    mdListener.onDetailsListener(moviesList, getPosition());
                    break;
                default:
                    //  mdListener.onDetailsListener(moviesList, getPosition());
            }
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_home_demo_test, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeEventModel movie = moviesList.get(position);
        holder.txtGameName.setText(moviesList.get(position).getGame_name());
        holder.txtGameEventNo.setText(moviesList.get(position).getTotal_events_count());
//        holder.txtEventDateTime.setText(moviesList.get(position).getEvent_date_time());
//        holder.txtEventSport.setText(moviesList.get(position).getEvent_sport_name());


        if(moviesList.get(position).getGame_id() == -1){
            holder.txtGameEventNo.setVisibility(View.GONE);
            holder.rvData.setVisibility(View.GONE);
            //holder.lnMain.setBackgroundColor(Color.parseColor("#f0f1f3"));
            holder.imgBackground.setVisibility(View.GONE);
        }
        else{
            holder.imgBackground.setVisibility(View.VISIBLE);
            holder.imgBackground.setImageResource(movie.getImage_background());
            holder.txtGameEventNo.setVisibility(View.VISIBLE);
            holder.rvData.setVisibility(View.VISIBLE);
        }

        /*if (moviesList.get(position).getImage() == null && moviesList.get(position).getImage().equals("") &&
                moviesList.get(position).getImage().length() > 6) {
        } else {
            imageLoader.displayImage(moviesList.get(position).getImage(),
                    holder.imgBackground, options, new SimpleImageLoadingListener() {
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
        }*/

        holder.rvData.setAdapter( new HomeDemoBeachVollyBallAdapter(activity, mdListener, moviesList.get(position).getEventModels()));

//        holder.title.setText(movie.getTitle());
//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
