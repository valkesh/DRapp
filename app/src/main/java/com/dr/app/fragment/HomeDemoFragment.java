

package com.dr.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.adapter.HomeDemoAdapter;
import com.dr.app.model.EventModel;
import com.dr.app.model.HomeEventModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.Utility;
import com.dr.app.utility.UtilityPro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dr.app.utility.Golly.notificaiton_count;

public class HomeDemoFragment extends Fragment implements HomeDemoAdapter.OnDetailsItem, View.OnClickListener {
    private static final String ARG_ITEM = "item";

    private String item;
    private RecyclerView sport_one;

    ImageView imgSearch, imgLogo;

    ArrayList<HomeEventModel> homeEventModels = new ArrayList<HomeEventModel>();
    HomeDemoAdapter.OnDetailsItem getDetails;
    LinearLayout lnCountOne, lnCountTwo, lnCountThree, lnCountFour, lnCountFive, lnCountSix, lnOtherSport;

    int[] drawable_background = new int[]{
            R.drawable.hockey,
            R.drawable.cricket,
            R.drawable.basketball,
            R.drawable.beach,
            R.drawable.football_old,
            R.drawable.baseball,
            R.drawable.soccer
    };

    int[] drawable_background_new = new int[]{
            R.drawable.hockey,
            R.drawable.cricket,
            R.drawable.basketball,
            R.drawable.netball,
            R.drawable.beach,
            R.drawable.cycling,
            R.drawable.aussie_rules,
            R.drawable.football_old,
            R.drawable.giridiron,
            R.drawable.walk_run,
            R.drawable.tennis,
            R.drawable.soccer,
            R.drawable.inddor_soccer,
            R.drawable.fishing,
            R.drawable.swimming,
            R.drawable.surfing,
            R.drawable.golf,
            R.drawable.diving,
            R.drawable.cross,
            R.drawable.sparing,
            R.drawable.baseball
    };


    int[] drawable_menu_background = new int[]{
            R.drawable.ic_main_hockey,
            R.drawable.ic_main_cricket,
            R.drawable.ic_main_basket_ball,
            R.drawable.ic_main_vollyball,
            R.drawable.ic_main_football_old,
            R.drawable.ic_main_baseball,
            R.drawable.ic_main_soccer
    };

    int[] drawable_menu_background_new = new int[]{
            R.drawable.ic_main_hockey,
            R.drawable.ic_main_cricket,
            R.drawable.ic_main_basket_ball,
            R.drawable.ic_main_netball,
            R.drawable.ic_main_vollyball,
            R.drawable.ic_main_cycling,
            R.drawable.ic_main_aussie_rules,
            R.drawable.ic_main_football_old,
            R.drawable.ic_main_giridiron,
            R.drawable.ic_main_walk_run,
            R.drawable.ic_main_tennis,
            R.drawable.ic_main_soccer,
            R.drawable.ic_main_inddor_soccer,
            R.drawable.ic_main_fishng,
            R.drawable.ic_main_swimming,
            R.drawable.ic_main_surfing,
            R.drawable.ic_main_golf,
            R.drawable.ic_main_diving,
            R.drawable.ic_main_cross,
            R.drawable.ic_main_sparing,
            R.drawable.ic_main_baseball
    };

    //  String id_layout_one,id_layout_two, id_layout_three, id_layout_four, id_layout_five, id_layout_six = "0";
    public HomeDemoFragment() {
    }

    public static HomeDemoFragment newInstance(String item) {
        HomeDemoFragment fragment = new HomeDemoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // item = getArguments().getString(ARG_ITEM);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDetails = this;

        if (view != null) {
            sport_one = (RecyclerView) view.findViewById(R.id.sport_one);
            imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
            imgLogo = (ImageView) view.findViewById(R.id.imgLogo);
            sport_one.setHasFixedSize(true);
//    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity() ,LinearLayoutManager.HORIZONTAL, false);
            sport_one.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            imgSearch.setOnClickListener(this);
            imgLogo.setOnClickListener(this);
            Map<String, String> paramss = new HashMap<String, String>();
            paramss.put("user_id", Pref.getUserID());
            paramss.put("time_zone", UtilityPro.getTimezone());
            getSportGame(paramss);
            // lnCountOne.setOnClickListener(this);
        }
    }

    public void getSportGame(Map<String, String> params) {
        String Url = Constants.HOME_API;
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET HOME DATA",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    Utility.writeSharedPreferencesBool(getActivity(), Constants.is_push_notification, UtilityPro.getDataFromJsonBoolean(responce_obj, "push_notification_switch"));
                                    JSONObject json2 = responce_obj.getJSONObject("results");
                                    //if(UtilityPro.getDataFromJsonBoolean(json2,"is_update") == false){
                                    //Pref.setUserID(json2.getString("user_id"));
                                    notificaiton_count = UtilityPro.getDataFromJsonInt(responce_obj, "notification_count");
                                    ((MainActivity) getActivity()).updateNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));

                                    homeEventModels.clear();
                                    JSONArray event_array = json2.getJSONArray("data");
                                    for (int event = 0; event < event_array.length(); event++) {
                                        JSONObject object = event_array.getJSONObject(event);
//                                        int game_id;
//                                        String game_name, total_events_count, image;
                                        HomeEventModel HomeEventModel = new HomeEventModel();
                                        ArrayList<EventModel> eventModels = new ArrayList<>();
                                        HomeEventModel.setGame_id(UtilityPro.getDataFromJsonInt(object, "game_id"));
                                        HomeEventModel.setGame_name(UtilityPro.getDataFromJson(object, "game_name"));
                                        HomeEventModel.setTotal_events_count(UtilityPro.getDataFromJson(object, "total_events_count"));
                                        HomeEventModel.setImage_background(drawable_background_new[event]);
                                        HomeEventModel.setImage_menu_background(drawable_menu_background_new[event]);
                                        // HomeEventModel.setImage(UtilityPro.getDataFromJson(object, "game_image"));

//                                        game_id = UtilityPro.getDataFromJsonInt(object, "game_id");
//                                        game_name = UtilityPro.getDataFromJson(object, "game_name");
//                                        total_events_count = UtilityPro.getDataFromJson(object, "total_events_count");
//                                        image = UtilityPro.getDataFromJson(object, "image");


                                        if (object.has("events")) {
                                            JSONArray jsonArray = object.getJSONArray("events");
                                            for (int event_count = 0; event_count < jsonArray.length(); event_count++) {
                                                JSONObject event_object = jsonArray.getJSONObject(event_count);
                                                EventModel sportEventModel = new EventModel();
                                                sportEventModel.setEvent_id(UtilityPro.getDataFromJsonInt(event_object, "event_id"));
                                                sportEventModel.setEvent_image(UtilityPro.getDataFromJson(event_object, "event_image"));
                                                sportEventModel.setEvent_date(UtilityPro.getDataFromJson(event_object, "event_date"));
                                                sportEventModel.setEvent_time(UtilityPro.getDataFromJson(event_object, "event_time"));
                                                sportEventModel.setEvent_vacancy_count(UtilityPro.getDataFromJson(event_object, "event_vacancy_count"));
                                                sportEventModel.setRaw_id(event);
                                                eventModels.add(sportEventModel);
                                            }
                                            HomeEventModel.setEventModels(eventModels);
                                        }
                                        homeEventModels.add(HomeEventModel);
                                    }
                                    homeEventModels.add(new HomeEventModel(-1, "OTHER SPORT", "", "", new ArrayList<EventModel>()));

//                                  homeBeachVollyBallAdapter = new HomeBeachVollyBallAdapter(sportGameModels.get(0).getSportEventModels());
                                    sport_one.setAdapter(new HomeDemoAdapter(getActivity(), getDetails, homeEventModels));

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


//    @Override
//    public void onDetailsListener(ArrayList<SportEventModel> mList, int pos) {
//        ((MainActivity) getActivity()).showFragment(EventMoreDetailsFragment.newInstance(String.valueOf(mList.get(pos).getId())));
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgSearch:
                ((MainActivity) getActivity()).showFragment(SearchFragment.newInstance(""));
                break;
            case R.id.imgLogo:
                ((MainActivity) getActivity()).showFragment(SuggestedSportFragment.newInstance(""));
                break;
        }
    }


    @Override
    public void onDetailsListener(ArrayList<HomeEventModel> mList, int pos) {
        System.out.println("=========val Eventcall======" + pos);
        if (homeEventModels.get(pos).getGame_id() == -1) {
            ((MainActivity) getActivity()).showFragment(SuggestedSportFragment.newInstance(""));
        } else {
            ((MainActivity) getActivity()).showFragment(EventMoreFragment.newInstance(String.valueOf(homeEventModels.get(pos).getGame_id()), homeEventModels.get(pos).getGame_name(), homeEventModels.get(pos).getGame_id(), homeEventModels.get(pos).getImage_menu_background()));
        }
    }

    @Override
    public void onDetailsListenerEvent(ArrayList<EventModel> mList, int pos) {
        System.out.println("=========val main row======" + mList.get(pos).getRaw_id());
        System.out.println("=========val main row======" + mList.get(pos).getEvent_id());
        ((MainActivity) getActivity()).showFragment(EventMoreDetailsFragment.newInstance(String.valueOf(mList.get(pos).getEvent_id())));

    }
}