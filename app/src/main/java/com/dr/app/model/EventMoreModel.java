/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

public class EventMoreModel {



    public int id;
    public int game_id;
    public String total_event_count;
    public String game_name;
    public String sport_event_image;
    public String sport_event_date;
    public String sport_event_time;
    public String sport_event_no_vacancy;
    public boolean is_grid_first;
    public int background_image;


    public int getBackground_image() {
        return background_image;
    }

    public void setBackground_image(int background_image) {
        this.background_image = background_image;
    }

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

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getTotal_event_count() {
        return total_event_count;
    }

    public void setTotal_event_count(String total_event_count) {
        this.total_event_count = total_event_count;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public boolean is_grid_first() {
        return is_grid_first;
    }

    public void setIs_grid_first(boolean is_grid_first) {
        this.is_grid_first = is_grid_first;
    }

    public static String getStringFromObject(Context ct, ArrayList<EventMoreModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<EventMoreModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<EventMoreModel>();
        }
        ArrayList<EventMoreModel> obj = new ArrayList<EventMoreModel>();
        Type type = new TypeToken<ArrayList<EventMoreModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
