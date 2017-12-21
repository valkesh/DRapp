

package com.dr.app.fragment;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.adapter.EventMoreAdapter;
import com.dr.app.adapter.HomeBeachVollyBallAdapter;
import com.dr.app.adapter.SportAdapter;
import com.dr.app.model.EventMoreModel;
import com.dr.app.model.SportEventModel;
import com.dr.app.model.SportGameModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EventMoreFragment extends Fragment implements EventMoreAdapter.OnDetailsItem, View.OnClickListener {
    private static final String ARG_ITEM_GAME_ID = "game_id";

    public String game_id;
    public static int game_id_image_;
    public static int event_image;
    public static String game_name;
    EventMoreAdapter eventMoreAdapter;
    RecyclerView gridView;
    //    EventMoreModel eventMoreModel;
    ArrayList<EventMoreModel> eventMoreModels = new ArrayList<EventMoreModel>();
    EventMoreAdapter.OnDetailsItem onDetailsItem;

    public EventMoreFragment() {
    }

    public static EventMoreFragment newInstance(String game_id, String game_name_, int game_id_image , int icon) {
        EventMoreFragment fragment = new EventMoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_GAME_ID, game_id);
        fragment.setArguments(args);
        game_name = game_name_;
        game_id_image_ = game_id_image;
        event_image = icon;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game_id = getArguments().getString(ARG_ITEM_GAME_ID);
        onDetailsItem = this;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

    private GridLayoutManager lLayout;
    private ImageView imgBack;
    private ImageView imgSearch;
    private TextView txtEventTilte;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        gridView = (RecyclerView) view.findViewById(R.id.gridEventList);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
        txtEventTilte = (TextView) view.findViewById(R.id.txtEventTilte);
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);


//        gridView.setHasFixedSize(true);
        lLayout = new

                GridLayoutManager(getActivity(),

                3);
        gridView.setLayoutManager(lLayout);
        Map<String, String> params = new HashMap<String, String>();
        params.put("game_id", game_id);
        params.put("user_id", Pref.getUserID());
        params.put("time_zone", UtilityPro.getTimezone());

        getEventList(params);

    }


    public void getEventList(Map<String, String> params) {
        String Url = Constants.EVENT_LIST;
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET EVENT_LIST",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    ((MainActivity) getActivity()).updateNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));
                                    JSONObject json2 = responce_obj.getJSONObject("results");
                                    //if(UtilityPro.getDataFromJsonBoolean(json2,"is_update") == false){
                                    //Pref.setUserID(json2.getString("user_id"));
                                    txtEventTilte.setText(UtilityPro.getDataFromJson(json2, "game_name"));
                                    EventMoreModel sportGameModelFirst = new EventMoreModel();
                                    sportGameModelFirst.setTotal_event_count(UtilityPro.getDataFromJson(json2, "total_events_count"));
                                    sportGameModelFirst.setGame_id(UtilityPro.getDataFromJsonInt(json2, "game_id"));
                                    sportGameModelFirst.setGame_name(UtilityPro.getDataFromJson(json2, "game_name"));
                                    sportGameModelFirst.setBackground_image(event_image);
                                    JSONArray event_array = json2.getJSONArray("events");
                                    eventMoreModels.add(sportGameModelFirst);
                                    for (int event_count = 0; event_count < event_array.length(); event_count++) {
                                        JSONObject event_object = event_array.getJSONObject(event_count);
                                        EventMoreModel sportEventModel = new EventMoreModel();
                                        sportEventModel.setId(UtilityPro.getDataFromJsonInt(event_object, "event_id"));
                                        sportEventModel.setSport_event_image(UtilityPro.getDataFromJson(event_object, "event_image"));
                                        sportEventModel.setSport_event_date(UtilityPro.getDataFromJson(event_object, "event_date"));
                                        sportEventModel.setSport_event_time(UtilityPro.getDataFromJson(event_object, "event_time"));
                                        sportEventModel.setSport_event_no_vacancy(UtilityPro.getDataFromJson(event_object, "event_vacancy_count"));
                                        eventMoreModels.add(sportEventModel);
                                    }
                                    EventMoreModel sportGameModelLast = new EventMoreModel();
                                    sportGameModelLast.setTotal_event_count(UtilityPro.getDataFromJson(json2, "total_events_count"));
                                    sportGameModelLast.setGame_id(UtilityPro.getDataFromJsonInt(json2, "game_id"));
                                    sportGameModelLast.setGame_name(UtilityPro.getDataFromJson(json2, "game_name"));
                                    eventMoreModels.add(sportGameModelLast);
                                    eventMoreAdapter = new EventMoreAdapter(getActivity(), onDetailsItem, eventMoreModels);
                                    gridView.setAdapter(eventMoreAdapter);

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
    public void onDetailsListener(ArrayList<EventMoreModel> mList, int pos) {
        ((MainActivity) getActivity()).showFragment(EventMoreDetailsFragment.newInstance(String.valueOf(pos)));
    }

    @Override
    public void onCreatePost() {
        if (Pref.getIsLogin()) {
            ((MainActivity) getActivity()).showFragment(CreateEventFragment.newInstance(getString(R.string.createpost)));
        } else {
            ((MainActivity) getActivity()).showFragment(IsNotLoginProfileFragment.newInstance(getString(R.string.isnotuserprofile)));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                ((MainActivity) getActivity()).onBackPressed();
                break;
            case R.id.imgSearch:
                ((MainActivity) getActivity()).showFragment(SearchFragment.newInstance(""));
                break;
        }
    }

}