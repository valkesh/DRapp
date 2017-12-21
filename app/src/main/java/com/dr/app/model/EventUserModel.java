/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EventUserModel {


    public int action_id;
    public int user_id;
    public int event_id;
    public String user_name;
    public String user_image;
    public String user_chat_id;
    public String event_name;
    public int type;
    public String messages;
    public boolean is_online;


    public boolean is_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
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

    public String getUser_chat_id() {
        return user_chat_id;
    }

    public void setUser_chat_id(String user_chat_id) {
        this.user_chat_id = user_chat_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static String getStringFromObject(Context ct, ArrayList<EventUserModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<EventUserModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<EventUserModel>();
        }
        ArrayList<EventUserModel> obj = new ArrayList<EventUserModel>();
        Type type = new TypeToken<ArrayList<EventUserModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);
        return obj;
    }
}
