/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EventModel {



    public int event_id;
    public int raw_id;
    public String event_name;
    public String event_image;
    public String event_date;
    public String event_time;
    public String event_vacancy_count;

    public int getRaw_id() {
        return raw_id;
    }

    public void setRaw_id(int raw_id) {
        this.raw_id = raw_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_vacancy_count() {
        return event_vacancy_count;
    }

    public void setEvent_vacancy_count(String event_vacancy_count) {
        this.event_vacancy_count = event_vacancy_count;
    }

    public static String getStringFromObject(Context ct, ArrayList<EventModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<EventModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<EventModel>();
        }
        ArrayList<EventModel> obj = new ArrayList<EventModel>();
        Type type = new TypeToken<ArrayList<EventModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
