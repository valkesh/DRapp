/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoriesModel {

    public String video_title;
    public String video_data;
    public String video_thumb_image;
    public String video_date;
    public int is_fav;
    public String video_id;
    public String is_video_type;
    public int is_video_free;
    public int is_video_purchased;
    public String video_details;
    ArrayList<String> art_image;


    public ArrayList<String> getArt_image() {
        return art_image;
    }

    public void setArt_image(ArrayList<String> art_image) {
        this.art_image = art_image;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_data() {
        return video_data;
    }

    public void setVideo_data(String video_data) {
        this.video_data = video_data;
    }

    public String getVideo_date() {
        return video_date;
    }

    public void setVideo_date(String video_date) {
        this.video_date = video_date;
    }

    public int getIs_fav() {
        return is_fav;
    }

    public void setIs_fav(int is_fav) {
        this.is_fav = is_fav;
    }

    public String getIs_video_type() {
        return is_video_type;
    }

    public void setIs_video_type(String is_video_type) {
        this.is_video_type = is_video_type;
    }

    public int getIs_video_free() {
        return is_video_free;
    }

    public void setIs_video_free(int is_video_free) {
        this.is_video_free = is_video_free;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_thumb_image() {
        return video_thumb_image;
    }

    public void setVideo_thumb_image(String video_thumb_image) {
        this.video_thumb_image = video_thumb_image;
    }

    public int getIs_video_purchased() {
        return is_video_purchased;
    }

    public void setIs_video_purchased(int is_video_purchased) {
        this.is_video_purchased = is_video_purchased;
    }


    public String getVideo_details() {
        return video_details;
    }

    public void setVideo_details(String video_details) {
        this.video_details = video_details;
    }

    public static String getStringFromObject(Context ct, ArrayList<CategoriesModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<CategoriesModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<CategoriesModel>();
        }
        ArrayList<CategoriesModel> obj = new ArrayList<CategoriesModel>();
        Type type = new TypeToken<ArrayList<CategoriesModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
