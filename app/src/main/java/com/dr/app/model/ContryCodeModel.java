/**
 * @author Valkesh patel
 */
package com.dr.app.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

public class ContryCodeModel {


    String country_name;
    String country_code;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public static String getStringFromObject(Context ct, ArrayList<ContryCodeModel> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static ArrayList<ContryCodeModel> getObjectListFromString(Context ct, String mListStr) {

        if (mListStr.trim().length() == 0) {
            return new ArrayList<ContryCodeModel>();
        }
        ArrayList<ContryCodeModel> obj = new ArrayList<ContryCodeModel>();
        Type type = new TypeToken<ArrayList<ContryCodeModel>>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);

        return obj;
    }
}
