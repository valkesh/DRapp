/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

public class SportModel {



    public int id;
    public String sport_name;
    public String sport_image;
    public String sport_no_vacancy;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSport_name() {
        return sport_name;
    }

    public void setSport_name(String sport_name) {
        this.sport_name = sport_name;
    }

    public String getSport_image() {
        return sport_image;
    }

    public void setSport_image(String sport_image) {
        this.sport_image = sport_image;
    }

    public String getSport_no_vacancy() {
        return sport_no_vacancy;
    }

    public void setSport_no_vacancy(String sport_no_vacancy) {
        this.sport_no_vacancy = sport_no_vacancy;
    }

    public static String getStringFromObject(Context ct, ArrayList<SportModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<SportModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<SportModel>();
        }
        ArrayList<SportModel> obj = new ArrayList<SportModel>();
        Type type = new TypeToken<ArrayList<SportModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
