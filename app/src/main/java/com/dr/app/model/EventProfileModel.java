/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

public class EventProfileModel {

    public int event_id;
    public String event_title;
    public String event_description;
    public String event_image;
    public String event_date_time;
    public String event_sport_name;
    public boolean event_status;
    public int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public String getEvent_date_time() {
        return event_date_time;
    }

    public void setEvent_date_time(String event_date_time) {
        this.event_date_time = event_date_time;
    }

    public String getEvent_sport_name() {
        return event_sport_name;
    }

    public void setEvent_sport_name(String event_sport_name) {
        this.event_sport_name = event_sport_name;
    }

    public boolean isEvent_status() {
        return event_status;
    }

    public void setEvent_status(boolean event_status) {
        this.event_status = event_status;
    }

    public static String getStringFromObject(Context ct, ArrayList<EventProfileModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<EventProfileModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<EventProfileModel>();
        }
        ArrayList<EventProfileModel> obj = new ArrayList<EventProfileModel>();
        Type type = new TypeToken<ArrayList<EventProfileModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
