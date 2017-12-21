/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

public class SportEventModel {



    public int id;
    public String sport_event_image;
    public String sport_event_date;
    public String sport_event_time;
    public String sport_event_no_vacancy;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSport_event_image() {
        return sport_event_image;
    }

    public void setSport_event_image(String sport_event_image) {
        this.sport_event_image = sport_event_image;
    }

    public String getSport_event_date() {
        return sport_event_date;
    }

    public void setSport_event_date(String sport_event_date) {
        this.sport_event_date = sport_event_date;
    }

    public String getSport_event_time() {
        return sport_event_time;
    }

    public void setSport_event_time(String sport_event_time) {
        this.sport_event_time = sport_event_time;
    }

    public String getSport_event_no_vacancy() {
        return sport_event_no_vacancy;
    }

    public void setSport_event_no_vacancy(String sport_event_no_vacancy) {
        this.sport_event_no_vacancy = sport_event_no_vacancy;
    }

    public static String getStringFromObject(Context ct, ArrayList<SportEventModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<SportEventModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<SportEventModel>();
        }
        ArrayList<SportEventModel> obj = new ArrayList<SportEventModel>();
        Type type = new TypeToken<ArrayList<SportEventModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
