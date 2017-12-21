/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DoctorTimeModel {

    public String dayName;
    public String dayTo =  "to";
    public String dayStartTime;
    public String dayEndTime;

    public DoctorTimeModel(String dayName, String dayStartTime, String dayEndTime ,  String dayTo) {
        this.dayName = dayName;
        this.dayTo = dayTo;
        this.dayStartTime = dayStartTime;
        this.dayEndTime = dayEndTime;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayTo() {
        return dayTo;
    }

    public void setDayTo(String dayTo) {
        this.dayTo = dayTo;
    }

    public String getDayStartTime() {
        return dayStartTime;
    }

    public void setDayStartTime(String dayStartTime) {
        this.dayStartTime = dayStartTime;
    }

    public String getDayEndTime() {
        return dayEndTime;
    }

    public void setDayEndTime(String dayEndTime) {
        this.dayEndTime = dayEndTime;
    }

    public static String getStringFromObject(Context ct, ArrayList<DoctorTimeModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<DoctorTimeModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<DoctorTimeModel>();
        }
        ArrayList<DoctorTimeModel> obj = new ArrayList<DoctorTimeModel>();
        Type type = new TypeToken<ArrayList<DoctorTimeModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
