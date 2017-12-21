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
import com.dr.app.model.SportModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.PagingBaseAdapter;
import com.dr.app.utility.Pref;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * An array adapter that knows how to render views when given CustomData classes
 */
public class SportAdapter extends PagingBaseAdapter<String>  {
    private LayoutInflater mInflater;
    Context mContext;
    Holder holder;
    Activity mContext_act;
    public ArrayList<SportModel> mList = new ArrayList<SportModel>();
    private OnDetailsItem mdListener;
    ArrayList<Boolean> inProgress = new ArrayList<Boolean>();
    ImageLoader imageLoader = null;
    DisplayImageOptions options;


    public interface OnDetailsItem {
        void onDetailsListener(String index, int type, String sport_name, ArrayList<SportModel> mList, int pos);
    }

    public SportAdapter(Activity context, OnDetailsItem mdListener,
                        ArrayList<SportModel> mList) {

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
            convertView = mInflater.inflate(R.layout.adapter_sport, parent,
                    false);
            holder = new Holder();

            holder.txtSportName = (TextView) convertView.findViewById(R.id.txtSportName);
            holder.txtSportEventCount = (TextView) convertView.findViewById(R.id.txtSportEventCount);
            holder.imgSportPic = (ImageView) convertView.findViewById(R.id.imgSportPic);
            holder.lnMain = (LinearLayout) convertView.findViewById(R.id.lnMain);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text

        holder.txtSportName.setText(mList.get(position).getSport_name());
        holder.txtSportEventCount.setText(mList.get(position).getSport_no_vacancy());
//        if (mList.get(position).getIs_fav() == 1) {
//            holder.image_favourite.setImageResource(R.drawable.favourite_icon);
//        } else {
//            holder.image_favourite.setImageResource(R.drawable.unfavourite_icon);
//        }

        if (mList.get(position).getSport_image() == null && mList.get(position).getSport_image().equals("") &&
                mList.get(position).getSport_image().length() > 6) {
        } else {
            imageLoader.displayImage(mList.get(position).getSport_image(),
                    holder.imgSportPic, Golly.options, new SimpleImageLoadingListener() {
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

        holder.lnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdListener.onDetailsListener(String.valueOf(mList.get(position).getId()), 0 ,mList.get(position).getSport_name(), mList, position);
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

        TextView txtSportName,txtSportEventCount;
        ImageView imgSportPic;
        LinearLayout lnMain;

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
