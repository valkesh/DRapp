/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

public class YouApplyNotificationModel {


    public int action_id;
    public int user_id;
    public int event_id;
    public String user_name;
    public String user_image;
    public String event_image;
    public String event_name;
    public String type;
    public String messages;
    public boolean is_online;

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public boolean is_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getStringFromObject(Context ct, ArrayList<YouApplyNotificationModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<YouApplyNotificationModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<YouApplyNotificationModel>();
        }
        ArrayList<YouApplyNotificationModel> obj = new ArrayList<YouApplyNotificationModel>();
        Type type = new TypeToken<ArrayList<YouApplyNotificationModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);
        return obj;
    }
}
