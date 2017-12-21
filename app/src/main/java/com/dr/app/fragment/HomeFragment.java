

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
import android.widget.TextView;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.adapter.HomeBeachVollyBallAdapter;
import com.dr.app.model.SportEventModel;
import com.dr.app.model.SportGameModel;
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

public class HomeFragment extends Fragment implements HomeBeachVollyBallAdapter.OnDetailsItem, View.OnClickListener {
    private static final String ARG_ITEM = "item";

    private String item;
    private RecyclerView sport_one, sport_two, sport_three, sport_four, sport_five, sport_six;
    private TextView txtGameName, txtGameNameTwo, txtGameNameThree, txtGameNameFour, txtGameNameSix,
            txtGameNameFive, txtGameEventNo, txtGameEventNoTwo, txtGameEventNoThree, txtGameEventNoFour, txtGameEventNoFive, txtGameEventNoSix;

    ImageView imgSearch, imgLogo;
    ArrayList<SportEventModel> sportEventModels = new ArrayList<SportEventModel>();
    ArrayList<SportGameModel> sportGameModels = new ArrayList<SportGameModel>();
    HomeBeachVollyBallAdapter.OnDetailsItem getDetails;
    LinearLayout lnCountOne, lnCountTwo, lnCountThree, lnCountFour, lnCountFive, lnCountSix, lnOtherSport;

    //  String id_layout_one,id_layout_two, id_layout_three, id_layout_four, id_layout_five, id_layout_six = "0";
    public HomeFragment() {


    }

    public static HomeFragment newInstance(String item) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDetails = this;

        if (view != null) {
            sport_one = (RecyclerView) view.findViewById(R.id.sport_one);
            sport_two = (RecyclerView) view.findViewById(R.id.sport_two);
            sport_three = (RecyclerView) view.findViewById(R.id.sport_three);
            sport_four = (RecyclerView) view.findViewById(R.id.sport_four);
            sport_five = (RecyclerView) view.findViewById(R.id.sport_five);
            sport_six = (RecyclerView) view.findViewById(R.id.sport_six);
            imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
            imgLogo = (ImageView) view.findViewById(R.id.imgLogo);


            // Gmae name label
            txtGameName = (TextView) view.findViewById(R.id.txtGameName);
            txtGameNameTwo = (TextView) view.findViewById(R.id.txtGameNameTwo);
            txtGameNameThree = (TextView) view.findViewById(R.id.txtGameNameThree);
            txtGameNameFour = (TextView) view.findViewById(R.id.txtGameNameFour);
            txtGameNameFive = (TextView) view.findViewById(R.id.txtGameNameFive);
            txtGameNameSix = (TextView) view.findViewById(R.id.txtGameNameSix);


            lnCountOne = (LinearLayout) view.findViewById(R.id.lnCountOne);
            lnCountTwo = (LinearLayout) view.findViewById(R.id.lnCountTwo);
            lnCountThree = (LinearLayout) view.findViewById(R.id.lnCountThree);
            lnCountFour = (LinearLayout) view.findViewById(R.id.lnCountFour);
            lnCountFive = (LinearLayout) view.findViewById(R.id.lnCountFive);
            lnCountSix = (LinearLayout) view.findViewById(R.id.lnCountSix);
            lnOtherSport = (LinearLayout) view.findViewById(R.id.lnOtherSport);


            // No of total vacacany count label
            txtGameEventNo = (TextView) view.findViewById(R.id.txtGameEventNo);
            txtGameEventNoTwo = (TextView) view.findViewById(R.id.txtGameEventNoTwo);
            txtGameEventNoThree = (TextView) view.findViewById(R.id.txtGameEventNoThree);
            txtGameEventNoFour = (TextView) view.findViewById(R.id.txtGameEventNoFour);
            txtGameEventNoFive = (TextView) view.findViewById(R.id.txtGameEventNoFive);
            txtGameEventNoSix = (TextView) view.findViewById(R.id.txtGameEventNoSix);

            sport_one.setHasFixedSize(true);
            sport_two.setHasFixedSize(true);
            sport_three.setHasFixedSize(true);
            sport_four.setHasFixedSize(true);
            sport_five.setHasFixedSize(true);
            sport_six.setHasFixedSize(true);
//    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity() ,LinearLayoutManager.HORIZONTAL, false);
            sport_one.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            sport_two.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            sport_three.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            sport_four.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            sport_five.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            sport_six.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            Map<String, String> paramss = new HashMap<String, String>();
            paramss.put("user_id", Pref.getUserID());
            paramss.put("time_zone", UtilityPro.getTimezone());
            getSportGame(paramss);

            lnCountOne.setOnClickListener(this);
            lnCountTwo.setOnClickListener(this);
            lnCountThree.setOnClickListener(this);
            lnCountFour.setOnClickListener(this);
            lnCountFive.setOnClickListener(this);
            lnCountSix.setOnClickListener(this);
            imgSearch.setOnClickListener(this);
            imgLogo.setOnClickListener(this);
            lnOtherSport.setOnClickListener(this);

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

                                    ((MainActivity) getActivity()).updateNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));

                                    sportEventModels.clear();
                                    sportGameModels.clear();
                                    JSONArray event_array = json2.getJSONArray("data");
                                    for (int event = 0; event < event_array.length(); event++) {

                                        JSONObject object = event_array.getJSONObject(event);

                                        SportGameModel sportGameModel = new SportGameModel();
                                        sportGameModel.setTotal_event_count(UtilityPro.getDataFromJson(object, "total_events_count"));
                                        sportGameModel.setGame_id(UtilityPro.getDataFromJsonInt(object, "game_id"));
                                        sportGameModel.setGame_name(UtilityPro.getDataFromJson(object, "game_name"));
                                        JSONArray event_data = object.getJSONArray("events");
                                        ArrayList<SportEventModel> sportEventModelslist = new ArrayList<SportEventModel>();

                                        for (int event_count = 0; event_count < event_data.length(); event_count++) {
                                            JSONObject event_object = event_data.getJSONObject(event_count);
                                            SportEventModel sportEventModel = new SportEventModel();
                                            sportEventModel.setId(UtilityPro.getDataFromJsonInt(event_object, "event_id"));
                                            sportEventModel.setSport_event_image(UtilityPro.getDataFromJson(event_object, "event_image"));
                                            sportEventModel.setSport_event_date(UtilityPro.getDataFromJson(event_object, "event_date"));
                                            sportEventModel.setSport_event_time(UtilityPro.getDataFromJson(event_object, "event_time"));
                                            sportEventModel.setSport_event_no_vacancy(UtilityPro.getDataFromJson(event_object, "event_vacancy_count"));
                                            sportEventModels.add(sportEventModel);
                                            sportEventModelslist.add(sportEventModel);
                                        }
                                        sportGameModel.setSportEventModels(sportEventModelslist);
                                        sportGameModels.add(sportGameModel);
                                    }

//                      homeBeachVollyBallAdapter = new HomeBeachVollyBallAdapter(sportGameModels.get(0).getSportEventModels());
                                    sport_one.setAdapter(new HomeBeachVollyBallAdapter(getActivity(), getDetails, sportGameModels.get(0).getSportEventModels()));
                                    sport_two.setAdapter(new HomeBeachVollyBallAdapter(getActivity(), getDetails, sportGameModels.get(1).getSportEventModels()));
                                    sport_three.setAdapter(new HomeBeachVollyBallAdapter(getActivity(), getDetails, sportGameModels.get(2).getSportEventModels()));
                                    sport_four.setAdapter(new HomeBeachVollyBallAdapter(getActivity(), getDetails, sportGameModels.get(3).getSportEventModels()));
                                    sport_five.setAdapter(new HomeBeachVollyBallAdapter(getActivity(), getDetails, sportGameModels.get(4).getSportEventModels()));
                                    sport_six.setAdapter(new HomeBeachVollyBallAdapter(getActivity(), getDetails, sportGameModels.get(5).getSportEventModels()));

                                    // Gmae name label
                                    txtGameName.setText(sportGameModels.get(0).getGame_name());
                                    txtGameNameTwo.setText(sportGameModels.get(1).getGame_name());
                                    txtGameNameThree.setText(sportGameModels.get(2).getGame_name());
                                    txtGameNameFour.setText(sportGameModels.get(3).getGame_name());
                                    txtGameNameFive.setText(sportGameModels.get(4).getGame_name());
                                    txtGameNameSix.setText(sportGameModels.get(5).getGame_name());


                                    // No of total vacacany count label
                                    txtGameEventNo.setText(sportGameModels.get(0).getTotal_event_count());
                                    txtGameEventNoTwo.setText(sportGameModels.get(1).getTotal_event_count());
                                    txtGameEventNoThree.setText(sportGameModels.get(2).getTotal_event_count());
                                    txtGameEventNoFour.setText(sportGameModels.get(3).getTotal_event_count());
                                    txtGameEventNoFive.setText(sportGameModels.get(4).getTotal_event_count());
                                    txtGameEventNoSix.setText(sportGameModels.get(5).getTotal_event_count());

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
    public void onDetailsListener(ArrayList<SportEventModel> mList, int pos) {
        ((MainActivity) getActivity()).showFragment(EventMoreDetailsFragment.newInstance(String.valueOf(mList.get(pos).getId())));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnCountOne:
                mainLayerClicked(0, "ice_hockey");
                break;
            case R.id.lnCountTwo:
                mainLayerClicked(1, "basket_ball");
                break;
            case R.id.lnCountThree:
                mainLayerClicked(2, "beach_volly_ball");
                break;
            case R.id.lnCountFour:
                mainLayerClicked(3, "foot_ball");
                break;
            case R.id.lnCountFive:
                mainLayerClicked(4, "base_ball");
                break;
            case R.id.lnCountSix:
                mainLayerClicked(5, "soccer");
                break;
            case R.id.imgSearch:
                ((MainActivity) getActivity()).showFragment(SearchFragment.newInstance(""));
                break;
            case R.id.imgLogo:
                ((MainActivity) getActivity()).showFragment(SuggestedSportFragment.newInstance(""));
                break;
            case R.id.lnOtherSport:
                ((MainActivity) getActivity()).showFragment(SuggestedSportFragment.newInstance(""));
                break;
        }
    }

    public void mainLayerClicked(int game_id, String game_name) {
        if (sportGameModels.size() == 0) {
        } else {
            ((MainActivity) getActivity()).showFragment(EventMoreFragment.newInstance(String.valueOf(sportGameModels.get(game_id).getGame_id()), game_name, game_id, game_id));
        }
    }
}