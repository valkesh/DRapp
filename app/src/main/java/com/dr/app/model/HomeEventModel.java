/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeEventModel {


    public int game_id;
    public String game_name;
    public String total_events_count;
    public String image;
    public int image_background;
    public int image_menu_background;

    ArrayList<EventModel> eventModels = new ArrayList<>();


    public HomeEventModel(int game_id, String game_name, String total_events_count, String image, ArrayList<EventModel> eventModels) {
        this.game_id = game_id;
        this.game_name = game_name;
        this.total_events_count = total_events_count;
        this.image = image;
        this.eventModels = eventModels;
    }
    public HomeEventModel(){
    }

    public int getImage_background() {
        return image_background;
    }

    public void setImage_background(int image_background) {
        this.image_background = image_background;
    }

    public int getImage_menu_background() {
        return image_menu_background;
    }

    public void setImage_menu_background(int image_menu_background) {
        this.image_menu_background = image_menu_background;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getTotal_events_count() {
        return total_events_count;
    }

    public void setTotal_events_count(String total_events_count) {
        this.total_events_count = total_events_count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<EventModel> getEventModels() {
        return eventModels;
    }

    public void setEventModels(ArrayList<EventModel> eventModels) {
        this.eventModels = eventModels;
    }

    public static String getStringFromObject(Context ct, ArrayList<HomeEventModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<HomeEventModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<HomeEventModel>();
        }
        ArrayList<HomeEventModel> obj = new ArrayList<HomeEventModel>();
        Type type = new TypeToken<ArrayList<HomeEventModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
