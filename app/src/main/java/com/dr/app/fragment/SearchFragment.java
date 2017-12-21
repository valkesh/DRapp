

package com.dr.app.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.SearchResultsActivity;
import com.dr.app.adapter.SearchEventListAdapter;
import com.dr.app.model.SearchEventModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.GPSTracker;
import com.dr.app.utility.Golly;
import com.dr.app.utility.PagingListView;
import com.dr.app.utility.Pref;
import com.dr.app.utility.Utility;
import com.dr.app.utility.UtilityPro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment implements View.OnClickListener, SearchEventListAdapter.OnDetailsItem {
    private static final String ARG_ITEM = "item";
    public PagingListView listSearchEvent;
    public SearchEventListAdapter searchEventAdapter;
    public ArrayList<SearchEventModel> searchEventModels = new ArrayList<SearchEventModel>();
    SearchEventListAdapter.OnDetailsItem getDetails;
    EditText edtSearchBar;
    ImageView imgBack;
    LinearLayout lnSearchFilter;
    int request_search = 123;
    TextView txtFilterBar;
    String latitude = "0.0";
    String longtitude = "0.0";

    boolean is_page = false;
    int pager = 0;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(String item) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    View views;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        views = view;
        listSearchEvent = (PagingListView) views.findViewById(R.id.listSearchEvent);
        lnSearchFilter = (LinearLayout) views.findViewById(R.id.lnSearchFilter);
        txtFilterBar = (TextView) views.findViewById(R.id.txtFilterBar);
        edtSearchBar = (EditText) views.findViewById(R.id.edtSearchBar);
        imgBack = (ImageView) views.findViewById(R.id.imgBack);
        getDetails = this;
//        listSearchEvent.setHasFixedSize(true);
//        listSearchEvent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        searchEventAdapter = new SearchEventListAdapter(getActivity(), getDetails, searchEventModels);
        imgBack.setOnClickListener(this);
        lnSearchFilter.setOnClickListener(this);

//        Utility.writeSharedPreferencesInt(getActivity() , Constants.FILTER_TOTAL, 0);

        if (Utility.getAppPrefInt(getActivity(), Constants.FILTER_TOTAL) != -1) {
            getValidateFilter();
        }


        edtSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    edtSearchBar.clearFocus();
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(edtSearchBar.getWindowToken(), 0);
                    pager = 0;
                    if (searchEventModels.size() != 0 && searchEventModels != null) {
                        searchEventModels.clear();
                    }
                    getValidateFilter();
                    return true;
                }
                return false;
            }
        });
    }

    boolean is_back_results = false;

    public void getValidateFilter() {

//        if (pager == 0 && searchEventModels.size() != 0) {
//            searchEventModels.clear();
//            listSearchEvent.deferNotifyDataSetChanged();
//        }

//        if (is_back_results == false) {
//            if (!days.equalsIgnoreCase("")) {
//                paramss.put("day", days);
//            }
//            if (!sport_id.equalsIgnoreCase("")) {
//                paramss.put("sport_id", sport_id);
//            }
//            if (!distance.equalsIgnoreCase("1")) {
//                System.out.println("===========valkesh ==========" + distance);
//                paramss.put("distance", distance);
//                paramss.put("Lat", latitude);
//                paramss.put("Long", longtitude);
//            }
//        }


        Map<String, String> paramss = new HashMap<String, String>();
        int filter_count = 0;
        if (!Utility.getAppPrefString(getActivity(), Constants.FILTER_DAYS).equalsIgnoreCase("")) {
            filter_count = filter_count + 1;
            paramss.put("day", Utility.getAppPrefString(getActivity(), Constants.FILTER_DAYS));
        }
        if (Utility.getAppPrefInt(getActivity(), Constants.FILTER_DISTANCE) != -1) {
            int dis = Utility.getAppPrefInt(getActivity(), Constants.FILTER_DISTANCE);
            System.out.println("===========dis============" + dis);
            if (dis > 1) {
                filter_count = filter_count + 1;
                paramss.put("distance", String.valueOf(dis));
                paramss.put("Lat", latitude);
                paramss.put("Long", longtitude);
                if (!Utility.getAppPrefString(getActivity(), Constants.FILTER_LOCATION).equalsIgnoreCase("")) {
                    paramss.put("Lat", Utility.getAppPrefString(getActivity(), Constants.FILTER_LOCATION_LATITUDE));
                    paramss.put("Long", Utility.getAppPrefString(getActivity(), Constants.FILTER_LOCATION_LONGTITUDE));
                }
            }
        }
        if (!Utility.getAppPrefString(getActivity(), Constants.FILTER_LOCATION).equalsIgnoreCase("")) {
            filter_count = filter_count + 1;
        }

        if (!Utility.getAppPrefString(getActivity(), Constants.FILTER_START_DATES).equalsIgnoreCase("") && !Utility.getAppPrefString(getActivity(), Constants.FILTER_END_DATES).equalsIgnoreCase("")) {
            filter_count = filter_count + 1;
            paramss.put("start_date", Utility.getAppPrefString(getActivity(), Constants.FILTER_START_DATES));
            paramss.put("end_date", Utility.getAppPrefString(getActivity(), Constants.FILTER_END_DATES));
        }

        if (!Utility.getAppPrefString(getActivity(), Constants.FILTER_SPORT_ID).equalsIgnoreCase("")) {
            filter_count = filter_count + 1;
            paramss.put("sport_id", Utility.getAppPrefString(getActivity(), Constants.FILTER_SPORT_ID));
        }

        if (filter_count == 0) {
            txtFilterBar.setText("SHOW FILTER");
        } else {
            txtFilterBar.setText("SHOW FILTER(" + String.valueOf(filter_count) + ")");
        }

        paramss.put("event_name", edtSearchBar.getText().toString().trim());
        paramss.put("user_id", Pref.getUserID());
        paramss.put("page", String.valueOf(pager));
        paramss.put("time_zone", UtilityPro.getTimezone());
//        if (searchEventModels != null && searchEventModels.size() > 0) {
//            searchEventModels.clear();
//        }
        // if (edtSearchBar.getText().toString().trim().length() != 0) {

        //System.out.println("===paramss==="+ paramss.);

        getSearchResults(paramss);
        // }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                getActivity().onBackPressed();

                //((MainActivity) getActivity()).showFragment(EditProfileFragment.newInstance(getString(R.string.editprofile)));
                break;
            case R.id.lnSearchFilter:
                openAddressMap();
                break;
        }
    }

    String days = "";
    String distance = "1";
    String sport_id = "";
    String sport_name = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == request_search) {
            if (resultCode == getActivity().RESULT_OK) {
                int filter_count = 0;
                if (!Utility.getAppPrefString(getActivity(), Constants.FILTER_DAYS).equalsIgnoreCase("")) {
                    filter_count = filter_count + 1;
                }
                if (Utility.getAppPrefInt(getActivity(), Constants.FILTER_DISTANCE) != 1) {
                    int dis = Utility.getAppPrefInt(getActivity(), Constants.FILTER_DISTANCE);
                    if (dis > 1) {
                        filter_count = filter_count + 1;
                    }
                }
                if (!Utility.getAppPrefString(getActivity(), Constants.FILTER_SPORT_ID).equalsIgnoreCase("")) {
                    filter_count = filter_count + 1;
                }
                Utility.writeSharedPreferencesInt(getActivity(), Constants.FILTER_TOTAL, filter_count);

                if (filter_count == 0) {
                    txtFilterBar.setText("SHOW FILTER");
                } else {
                    txtFilterBar.setText("SHOW FILTER(" + String.valueOf(filter_count) + ")");
                }
                pager = 0;


                //            if (!days.equalsIgnoreCase("")) {
//                paramss.put("day", days);
//            }
//            if (!sport_id.equalsIgnoreCase("")) {
//                paramss.put("sport_id", sport_id);
//            }
//            if (!distance.equalsIgnoreCase("1")) {
//                System.out.println("===========valkesh ==========" + distance);
//                paramss.put("distance", distance);
//                paramss.put("Lat", latitude);
//                paramss.put("Long", longtitude);
//            }
                getValidateFilter();


//
//                int is_filter = data.getIntExtra("is_filter", 0);
//                boolean is_clear = data.getBooleanExtra("is_clear", false);
//
//                    if (is_clear) {
//                        days = "";
//                        distance = "1";
//                        sport_id = "";
//                        sport_name = "";
//                        int filter_count = 0;
//                        txtFilterBar.setText("SHOW FILTER(" + String.valueOf(filter_count) + ")");
//                        pager = 0;
//                        getValidateFilter();
//                }
//                if (is_filter == 2) {
//                    days = data.getStringExtra("date_no");
//                    distance = data.getStringExtra("seek_values");
//                    sport_id = data.getStringExtra("sport_id");
//                    sport_name = data.getStringExtra("sport_name");
//                    int filter_count = 0;
//                    if (!Utility.getAppPrefString(getActivity(), Constants.FILTER_DAYS).equalsIgnoreCase("")) {
//                        filter_count = filter_count + 1;
//                    }
//                    if (Utility.getAppPrefInt(getActivity(), Constants.FILTER_DISTANCE) != 1 && Utility.getAppPrefInt(getActivity(), Constants.FILTER_DISTANCE) != 0) {
//                        filter_count = filter_count + 1;
//                    }
//                    if (!Utility.getAppPrefString(getActivity(), Constants.FILTER_SPORT).equalsIgnoreCase("")) {
//                        filter_count = filter_count + 1;
//                    }
//                    txtFilterBar.setText("SHOW FILTER(" + String.valueOf(filter_count) + ")");
//                    pager = 0;
//                    getValidateFilter();
//                }
            }
        }
    }


    public void openAddressMap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            } else {
                checkGps();
            }
        } else {
            checkGps();
        }
    }

    public void checkGps() {
        GPSTracker gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) { // gps enabled} // return boolean true/false

            double latitude_l = gps.getLatitude(); // returns latitude
            double lontitude_l = gps.getLongitude(); // returns longitude

            latitude = String.valueOf(latitude_l);
            longtitude = String.valueOf(lontitude_l);

            Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
            startActivityForResult(intent, request_search);

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    public void getSearchResults(Map<String, String> params) {
        String Url = Constants.SEARCH_EVENT;
        //   Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET SEARCH_EVENT",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    ((MainActivity) getActivity()).updateNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));
                                    JSONArray main_array = responce_obj.getJSONArray("results");
                                    if (pager == 0) {
                                        searchEventModels.clear();
                                    }
                                    for (int event_count = 0; event_count < main_array.length(); event_count++) {
                                        JSONObject event_object = main_array.getJSONObject(event_count);
                                        SearchEventModel sportEventModel = new SearchEventModel();
                                        sportEventModel.setEvent_id(UtilityPro.getDataFromJsonInt(event_object, "event_id"));
                                        sportEventModel.setGame_id(UtilityPro.getDataFromJsonInt(event_object, "game_id"));
                                        sportEventModel.setEvent_image(UtilityPro.getDataFromJson(event_object, "event_image"));
                                        sportEventModel.setEvent_title(UtilityPro.getDataFromJson(event_object, "event_name"));
                                        sportEventModel.setEvent_date(UtilityPro.getDataFromJson(event_object, "event_date"));
                                        sportEventModel.setEvent_time(UtilityPro.getDataFromJson(event_object, "event_time"));
                                        sportEventModel.setEvent_sport_name(UtilityPro.getDataFromJson(event_object, "game_name"));
                                        sportEventModel.setEvent_vacancy_count(UtilityPro.getDataFromJsonInt(event_object, "event_vacancy_count"));
                                        sportEventModel.setEvent_status(UtilityPro.getDataFromJsonInt(event_object, "event_status"));
                                        searchEventModels.add(sportEventModel);
                                    }

                                    if (listSearchEvent.getAdapter() == null) {
                                        listSearchEvent.setAdapter(searchEventAdapter);
                                        listSearchEvent.setHasMoreItems(true);
                                        listSearchEvent.setPagingableListener(pm);
                                    }
                                    if (responce_obj.getBoolean("next") == true) {
                                        is_page = true;
                                        pager = pager + 1;
                                        listSearchEvent.onFinishLoading(true, searchEventModels);

                                    } else {
                                        is_page = false;
                                        listSearchEvent.setHasMoreItems(false);
                                        listSearchEvent.onFinishLoading(false, searchEventModels);
                                    }
                                    searchEventAdapter.notifyDataSetChanged();

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
    public void onDetailsListener(ArrayList<SearchEventModel> searchEvent, int pos) {

        ((MainActivity) getActivity()).showFragment(EventMoreDetailsFragment.newInstance(String.valueOf(searchEvent.get(pos).getEvent_id())));
    }

    PagingListView.Pagingable pm = new PagingListView.Pagingable() {
        @Override
        public void onLoadMoreItems() {
            if (pager != 0) {
                getValidateFilter();
            }
        }
    };
}




