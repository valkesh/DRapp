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
import android.widget.RelativeLayout;

import com.android.volley.Request;


import com.dr.app.R;
import com.dr.app.model.CategoriesModel;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An array adapter that knows how to render views when given CustomData classes
 */
public class CategoriesAdapter extends PagingBaseAdapter<String> {
    private LayoutInflater mInflater;
    Context mContext;
    Holder holder;
    Activity mContext_act;
    int mSelectedItem = -1;
    private int lastPosition = 0;
    public ArrayList<CategoriesModel> mList = new ArrayList<CategoriesModel>();
    private OnDetailsItem mdListener;
    ArrayList<Boolean> inProgress = new ArrayList<Boolean>();
    ImageLoader imageLoader = null;
    DisplayImageOptions options;

    public interface OnDetailsItem {
        void onDetailsListener(String index, int type, String image_url, ArrayList<CategoriesModel> mList, int pos);
    }

    public CategoriesAdapter(Activity context, OnDetailsItem mdListener,
                             ArrayList<CategoriesModel> mList) {

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
            convertView = mInflater.inflate(R.layout.adapter_categories, parent,
                    false);
            holder = new Holder();

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


//        if (mList.get(position).getIs_fav() == 1) {
//            holder.image_favourite.setImageResource(R.drawable.favourite_icon);
//        } else {
//            holder.image_favourite.setImageResource(R.drawable.unfavourite_icon);
//        }

//        if (mList.get(position).getVideo_thumb_image() == null && mList.get(position).getVideo_thumb_image().equals("") &&
//                mList.get(position).getVideo_thumb_image().length() > 6) {
//        } else {
//            imageLoader.displayImage(mList.get(position).getVideo_thumb_image(),
//                    holder.image_video_thumb, Golly.options, new SimpleImageLoadingListener() {
//                        @Override
//                        public void onLoadingStarted(String imageUri, View view) {
//                            // holder.progressBar.setProgress(0);
//                            // holder.progressBar.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onLoadingFailed(String imageUri, View view,
//                                                    FailReason failReason) {
//                            // holder.progressBar.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onLoadingComplete(String imageUri, View view,
//                                                      Bitmap loadedImage) {
//                            // holder.progressBar.setVisibility(View.GONE);
//                        }
//                    }, new ImageLoadingProgressListener() {
//                        @Override
//                        public void onProgressUpdate(String imageUri, View view,
//                                                     int current, int total) {
//                            // holder.progressBar.setProgress(Math.round(100.0f
//                            // * current / total));
//                        }
//                    });
//        }

        return convertView;
    }

    /**
     * View holder for the views we need access to
     */

    private static class Holder {

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


    public void setFavouriteItem(final int is_fav, final int position, String content_id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("status", "" + is_fav);
        params.put("content_id", "" + content_id);
        params.put("user_id", "" + Pref.getUserID());

        String Url = Constants.FAVOURITE_ITEM;
        Golly.showProgressDialog(mContext_act);
        Golly.FireWebCall(Url, params, Request.Method.POST, "API_CALL_HOME_FAVOURITE_ITEM",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {

//                              JSONArray results = new JSONArray(response);
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    mList.get(position).setIs_fav(is_fav);
                                    notifyDataSetChanged();
                                    Pref.setHomedataOffline(Constants.CACHED_DATA_HOME_KEY, "");
                                }
                            } catch (JSONException e) {
                                // message =
                                // "Something went wrong, please try again later!";
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void errorResponce() {

                    }
                }

        );
    }

}
