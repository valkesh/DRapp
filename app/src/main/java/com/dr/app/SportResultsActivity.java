/*
 * Copyright (C) 2016 JetRadar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dr.app;


import com.android.volley.Request;
import com.dr.app.adapter.SportAdapter;
import com.dr.app.model.ContryCodeModel;
import com.dr.app.model.SportModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.GPSTracker;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.Utility;
import com.dr.app.utility.UtilityPro;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SportResultsActivity extends FragmentActivity implements View.OnClickListener, SportAdapter.OnDetailsItem {

    SportAdapter sportAdapter;
    ArrayList<SportModel> sportModels = new ArrayList<SportModel>();
    SportAdapter.OnDetailsItem getDetails;
    ImageView btnBack;
    ListView listSport;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_results);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        listSport = (ListView) findViewById(R.id.listSport);
        btnBack.setOnClickListener(this);
        getDetails = SportResultsActivity.this;
        sportAdapter = new SportAdapter(this, getDetails, sportModels);
        listSport.setAdapter(sportAdapter);
        Map<String, String> params = new HashMap<String, String>();
//      params.put("user_id", "");
        params.put("time_zone", UtilityPro.getTimezone());
        getSportEvent(params);
    }

    public void getSportEvent(Map<String, String> params) {
        String Url = Constants.GETSPORTNAME;
        Golly.showDarkProgressDialog(this);
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL_ SPORT_NAME",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    JSONArray json2 = responce_obj.getJSONArray("results");
                                    for (int i = 0; i < json2.length(); i++) {
                                        JSONObject value = json2.getJSONObject(i);
                                        SportModel sportModels_ = new SportModel();
                                        sportModels_.setId(UtilityPro.getDataFromJsonInt(value, "sport_id"));
                                        sportModels_.setSport_no_vacancy(UtilityPro.getDataFromJson(value, "sport_event_count"));
                                        sportModels_.setSport_name(UtilityPro.getDataFromJson(value, "sport_name"));
                                        sportModels_.setSport_image(UtilityPro.getDataFromJson(value, "sport_image"));
                                        sportModels.add(sportModels_);
                                    }
                                    sportAdapter.notifyDataSetChanged();
                                } else {
                                    if (!UtilityPro.getDataFromJson(responce_obj, "message").equalsIgnoreCase("")) {
                                        UtilityPro.toast(UtilityPro.getDataFromJson(responce_obj, "message"));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void errorResponce() {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                Intent data = new Intent();
                data.putExtra("is_back", true);
                data.putExtra("sport_name", "");
                data.putExtra("sport_id", "");
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }

    @Override
    public void onDetailsListener(String index, int type, String sport_name, ArrayList<SportModel> mList, int pos) {
        Intent data = new Intent();
        data.putExtra("is_back", false);
        data.putExtra("sport_name", sport_name);
        data.putExtra("sport_id", index);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("is_back", true);
        data.putExtra("sport_name", "");
        data.putExtra("sport_id", "");
        setResult(RESULT_OK, data);
        finish();
    }
}