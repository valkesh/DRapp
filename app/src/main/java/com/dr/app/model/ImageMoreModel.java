/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ImageMoreModel {

    public String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public static String getStringFromObject(Context ct, ArrayList<ImageMoreModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<ImageMoreModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<ImageMoreModel>();
        }
        ArrayList<ImageMoreModel> obj = new ArrayList<ImageMoreModel>();
        Type type = new TypeToken<ArrayList<ImageMoreModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
