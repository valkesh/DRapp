/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

public class SearchEventModel {


    public int event_id;
    public int game_id;
    public int event_vacancy_count;
    public int total_events_count;
    public String event_title;
    public String event_description;
    public String event_image;
    public String game_name;
    public String event_date;
    public String event_time;
    public String event_sport_name;
    public int event_status;

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getEvent_vacancy_count() {
        return event_vacancy_count;
    }

    public void setEvent_vacancy_count(int event_vacancy_count) {
        this.event_vacancy_count = event_vacancy_count;
    }

    public int getTotal_events_count() {
        return total_events_count;
    }

    public void setTotal_events_count(int total_events_count) {
        this.total_events_count = total_events_count;
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

    public String getEvent_sport_name() {
        return event_sport_name;
    }

    public void setEvent_sport_name(String event_sport_name) {
        this.event_sport_name = event_sport_name;
    }

    public int getEvent_status() {
        return event_status;
    }

    public void setEvent_status(int event_status) {
        this.event_status = event_status;
    }



    public static String getStringFromObject(Context ct, ArrayList<SearchEventModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<SearchEventModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<SearchEventModel>();
        }
        ArrayList<SearchEventModel> obj = new ArrayList<SearchEventModel>();
        Type type = new TypeToken<ArrayList<SearchEventModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
