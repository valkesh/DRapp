/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

public class SportGameModel {



    public int game_id;
    public String  total_event_count;
    public String  game_name;
    public ArrayList<SportEventModel>  sportEventModels;


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

    public ArrayList<SportEventModel> getSportEventModels() {
        return sportEventModels;
    }

    public void setSportEventModels(ArrayList<SportEventModel> sportEventModels) {
        this.sportEventModels = sportEventModels;
    }

    public static String getStringFromObject(Context ct, ArrayList<SportGameModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<SportGameModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<SportGameModel>();
        }
        ArrayList<SportGameModel> obj = new ArrayList<SportGameModel>();
        Type type = new TypeToken<ArrayList<SportGameModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
