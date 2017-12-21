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

import com.android.volley.Request;
import com.dr.app.R;
import com.dr.app.model.CategoriesModel;
import com.dr.app.model.ImageMoreModel;
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
public class ImageMoreAdapter extends PagingBaseAdapter<String> {
    private LayoutInflater mInflater;
    Context mContext;
    Holder holder;
    Activity mContext_act;
    public ArrayList<ImageMoreModel> mList = new ArrayList<ImageMoreModel>();
    private OnDetailsItem mdListener;
    ArrayList<Boolean> inProgress = new ArrayList<Boolean>();
    ImageLoader imageLoader = null;
    DisplayImageOptions options;

    public interface OnDetailsItem {
        void onDetailsListener(String index, int type, String image_url, ArrayList<ImageMoreModel> mList, int pos);
    }

    public ImageMoreAdapter(Activity context, OnDetailsItem mdListener,
                            ArrayList<ImageMoreModel> mList) {

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
            convertView = mInflater.inflate(R.layout.adapter_image_more, parent,
                    false);
            holder = new Holder();
            holder.imgOtherUseOne = (ImageView) convertView.findViewById(R.id.imgOtherUseOne);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (mList.get(position).getImage_url() == null && mList.get(position).getImage_url().equals("") &&
                mList.get(position).getImage_url().length() > 6) {
        } else {
            imageLoader.displayImage(mList.get(position).getImage_url(),
                    holder.imgOtherUseOne, options, new SimpleImageLoadingListener() {
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
        return convertView;
    }

    /**
     * View holder for the views we need access to
     */

    private static class Holder {
        ImageView imgOtherUseOne;
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
