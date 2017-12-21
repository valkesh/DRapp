package com.dr.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import com.dr.app.R;
import com.dr.app.model.EventProfileModel;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private ArrayList<EventProfileModel> moviesList;
    private OnDetailsItem mdListener;

    public interface OnDetailsItem {
        void onDetailsListener(ArrayList<EventProfileModel> mList, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        ImageView imgActive,imgEventPic;
        TextView txtEventName, txtEventDateTime, txtEventSport;
        LinearLayout lnEventLayer;

        public MyViewHolder(View convertView) {
            super(convertView);
            txtEventName = (TextView) convertView.findViewById(R.id.txtEventName);
            imgEventPic = (ImageView) convertView.findViewById(R.id.imgEventPic);
            imgActive = (ImageView) convertView.findViewById(R.id.imgActive);
            txtEventName = (TextView) convertView.findViewById(R.id.txtEventName);
            txtEventDateTime = (TextView) convertView.findViewById(R.id.txtEventDateTime);
            txtEventSport = (TextView) convertView.findViewById(R.id.txtEventSport);
            lnEventLayer = (LinearLayout) convertView.findViewById(R.id.lnEventLayer);
            convertView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
        switch (view.getId()){
            case  R.id.imgEventPic:
                    break;
                default:
                    mdListener.onDetailsListener(moviesList, getPosition());
                }
        }
    }


    public MoviesAdapter(List<EventProfileModel> moviesList) {
        this.moviesList = (ArrayList<EventProfileModel>) moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_movie_test, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventProfileModel movie = moviesList.get(position);
        holder.txtEventName.setText(moviesList.get(position).getEvent_title());
        holder.txtEventDateTime.setText(moviesList.get(position).getEvent_date_time());
        holder.txtEventSport.setText(moviesList.get(position).getEvent_sport_name());



//        holder.title.setText(movie.getTitle());
//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
