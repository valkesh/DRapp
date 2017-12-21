

package com.dr.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.adapter.ImageMoreAdapter;
import com.dr.app.model.ImageMoreModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.GPSTracker;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EventMoreDetailsFragment extends Fragment implements ImageMoreAdapter.OnDetailsItem, View.OnClickListener {
    private static final String ARG_ITEM = "item";

    public String item;
    public GoogleMap mMap;
    GPSTracker gps;
    SupportMapFragment map;
    public ArrayList<ImageMoreModel> mList = new ArrayList<ImageMoreModel>();
    public ImageMoreAdapter imageMoreAdapter;
    public GridView GridUserPics;
    public ImageMoreAdapter.OnDetailsItem getDetails;
    public FloatingActionButton fab;

    ImageView imgBanner, imgCreaterPics;
    TextView txtCreaterName, txtSportName, txtSportEventName, txtVacancyCount, txtTotalVacancy, txtAddress, txtDisc,
            txtDate, txtTime, txtMonth;
    ListView listUserPics;
    ImageLoader imageLoader = null;
    DisplayImageOptions options;
    Button btnWillJoin;

    String event_lat = "0.0";
    String event_long = "0.0";
    int creator_id = 0;
    boolean is_active = false;
    FragmentManager fm;

    boolean is_event_apply = false;
    boolean is_event_join = false;
    boolean is_full = false;
    int event_id = 0;
    int game_id = 0;
    private LinearLayout lnShare;

    public EventMoreDetailsFragment() {
    }

    public static EventMoreDetailsFragment newInstance(String item) {
        EventMoreDetailsFragment fragment = new EventMoreDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            getActivity().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.spalce_bg, getActivity().getBaseContext().getTheme()));
//            getActivity().getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
        Bundle arguments = getArguments();
        if (arguments == null) {
            item = Constants.event_more;
        } else {
            item = getArguments().getString(ARG_ITEM);
            Constants.event_more = item;
        }

        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) { // gps enabled} // return boolean true/false
            gps.getLatitude(); // returns latitude
            gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }
        getDetails = this;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_section)
                .showImageForEmptyUri(R.drawable.img_section)
                .showImageOnFail(R.drawable.img_section).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


// create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_NoActionBar_Fragment);

// clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_event_more_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        final Map<String, String> params = new HashMap<String, String>();
        if (Pref.getIsLogin()) {
            params.put("user_id", Pref.getUserID());
            params.put("time_zone", UtilityPro.getTimezone());
        }
        params.put("event_id", item);
        if (view != null) {
            imgBanner = (ImageView) view.findViewById(R.id.imgBanner);
            imgCreaterPics = (ImageView) view.findViewById(R.id.imgCreaterPics);
            txtCreaterName = (TextView) view.findViewById(R.id.txtCreaterName);
            txtSportName = (TextView) view.findViewById(R.id.txtSportName);
            txtSportEventName = (TextView) view.findViewById(R.id.txtSportEventName);
            txtVacancyCount = (TextView) view.findViewById(R.id.txtVacancyCount);
            txtTotalVacancy = (TextView) view.findViewById(R.id.txtTotalVacancy);
            txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            txtDisc = (TextView) view.findViewById(R.id.txtDisc);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            txtMonth = (TextView) view.findViewById(R.id.txtMonth);
            GridUserPics = (GridView) view.findViewById(R.id.GridUserPics);
            btnWillJoin = (Button) view.findViewById(R.id.btnWillJoin);
            lnShare = (LinearLayout) view.findViewById(R.id.lnShare);
            lnShare.setOnClickListener(this);
            //fab = (FloatingActionButton) view.findViewById(R.id.fab);
            imageMoreAdapter = new ImageMoreAdapter(getActivity(), getDetails, mList);
            GridUserPics.setAdapter(imageMoreAdapter);
            GridUserPics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mList.size() != 0) {
                        if (Pref.getIsLogin()) {
                            ((MainActivity) getActivity()).showFragment(FragmentEventUsers.newInstance("Joinuser", String.valueOf(event_id)));
                        }
                    }
                }
            });
            fm = getChildFragmentManager();
            map = (SupportMapFragment) fm.findFragmentById(R.id.map);
//          map = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, map).commit();

            btnWillJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is_event_apply) {
                        Map<String, String> w_params = new HashMap<String, String>();
                        w_params.put("user_id", Pref.getUserID());
                        w_params.put("game_id", String.valueOf(game_id));
                        w_params.put("event_id", String.valueOf(event_id));
                        withrowEvent(w_params);
                    } else if (is_event_apply == false) {
                        Map<String, String> apply_params = new HashMap<String, String>();
                        apply_params.put("user_id", Pref.getUserID());
                        apply_params.put("game_id", String.valueOf(game_id));
                        apply_params.put("event_id", String.valueOf(event_id));
                        applyEvent(apply_params);
                    }
                }
            });

            imgCreaterPics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).showFragment(ProfileFragment.newInstance(String.valueOf(creator_id)));
                }
            });

//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
            if (!Pref.getIsLogin()) {
                btnWillJoin.setVisibility(View.GONE);
            }
            getEventDetails(params);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnShare:
                if (event_name_share.toString().length() != 0) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Come join my event " + event_name_share + ".\nSearch it on Justsport at " + Constants.APPLICATION_LINK);
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Justsport Join Event");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Joint with Justsport"));
                } else {

                }
                break;
        }
    }

    String event_name_share = "";

    public void getEventDetails(Map<String, String> params) {
        String Url = Constants.EVENT_DETAILS;
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET EVENT_DETAILS",
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
                                    JSONArray image_url = json2.getJSONArray("event_join_user_image");
                                    mList.clear();

                                    if (image_url.length() != 0) {


                                    }
                                    for (int i = 0; i < image_url.length(); i++) {
                                        ImageMoreModel imageMoreModel = new ImageMoreModel();
                                        imageMoreModel.setImage_url(image_url.getString(i));
                                        mList.add(imageMoreModel);
                                    }
                                    imageMoreAdapter.notifyDataSetChanged();
                                    game_id = UtilityPro.getDataFromJsonInt(json2, "game_id");
                                    event_id = UtilityPro.getDataFromJsonInt(json2, "event_id");
                                    txtCreaterName.setText(UtilityPro.getDataFromJson(json2, "creator_name"));
                                    txtSportName.setText(UtilityPro.getDataFromJson(json2, "game_name"));
                                    txtSportEventName.setText(UtilityPro.getDataFromJson(json2, "event_name"));
                                    event_name_share = UtilityPro.getDataFromJson(json2, "event_name");
                                    txtVacancyCount.setText(UtilityPro.getDataFromJson(json2, "event_join_count"));
                                    txtTotalVacancy.setText("/" + UtilityPro.getDataFromJson(json2, "event_vacancy_count"));
                                    txtAddress.setText(UtilityPro.getDataFromJson(json2, "event_address"));
                                    txtDisc.setText(UtilityPro.getDataFromJson(json2, "event_description"));
                                    txtDate.setText(UtilityPro.getDataFromJson(json2, "event_date"));
                                    txtTime.setText(UtilityPro.getDataFromJson(json2, "event_time"));
                                    txtMonth.setText(UtilityPro.getDataFromJson(json2, "event_month"));
                                    creator_id = UtilityPro.getDataFromJsonInt(json2, "creator_id");
                                    is_active = UtilityPro.getDataFromJsonBoolean(json2, "event_status");

                                    event_lat = UtilityPro.getDataFromJson(json2, "event_lat");
                                    event_long = UtilityPro.getDataFromJson(json2, "event_long");
                                    final LatLng sydney = new LatLng(Double.parseDouble(event_lat), Double.parseDouble(event_long));
                                    map.getMapAsync(new OnMapReadyCallback() {
                                        @Override
                                        public void onMapReady(GoogleMap googleMap) {
                                            mMap = googleMap;
                                            mMap.addMarker(new MarkerOptions().position(sydney).title("Event Address").draggable(false));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                            mMap.getMaxZoomLevel();
                                            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(sydney, 14);
                                            mMap.animateCamera(yourLocation);
                                            mMap.getUiSettings().setAllGesturesEnabled(true);
                                            mMap.getUiSettings().setZoomControlsEnabled(true);
                                        }
                                    });

                                    is_event_apply = UtilityPro.getDataFromJsonBoolean(json2, "is_event_apply");
                                    is_event_join = UtilityPro.getDataFromJsonBoolean(json2, "is_event_join");
                                    is_full = UtilityPro.getDataFromJsonBoolean(json2, "is_full");

                                    if (is_active) {
                                        if (!Pref.getUserID().equalsIgnoreCase(UtilityPro.getDataFromJson(json2, "creator_id"))) {
                                            btnWillJoin.setVisibility(View.VISIBLE);
                                            if (is_event_apply) {
                                                btnWillJoin.setBackgroundColor(Color.parseColor("#D9DADC"));
                                                btnWillJoin.setText("WITHDRAW YOUR APPLICATION");
                                            } else {
                                                if (is_full == false) {
                                                    btnWillJoin.setVisibility(View.VISIBLE);
                                                } else {
                                                    btnWillJoin.setVisibility(View.GONE);
                                                }
                                            }
                                        } else {
                                            btnWillJoin.setVisibility(View.GONE);
                                        }
                                    } else {
                                        btnWillJoin.setVisibility(View.GONE);
                                    }

//                                    if (UtilityPro.getDataFromJson(json2, "event_vacancy_count").equalsIgnoreCase(UtilityPro.getDataFromJson(json2, "event_join_count")) && is_event_apply == false && is_event_join == false) {
//                                        btnWillJoin.setVisibility(View.GONE);
//                                    } else {
//                                        btnWillJoin.setVisibility(View.VISIBLE);
//                                        btnWillJoin.setBackgroundColor(Color.parseColor("#D9DADC"));
//                                        btnWillJoin.setText("WITHDRAW YOUR APPLICATION");
//                                    }


                                    if (!Pref.getIsLogin()) {
                                        btnWillJoin.setVisibility(View.GONE);
                                    }
                                    if (UtilityPro.getDataFromJson(json2, "event_image") == null && UtilityPro.getDataFromJson(json2, "event_image").equals("") &&
                                            UtilityPro.getDataFromJson(json2, "event_image").length() > 6) {
                                    } else {
                                        imageLoader.displayImage(UtilityPro.getDataFromJson(json2, "event_image"),
                                                imgBanner, options, new SimpleImageLoadingListener() {
                                                    @Override
                                                    public void onLoadingStarted(String imageUri, View view) {
                                                        // holder.progressBar.setProgress(0);
                                                        // holder.progressBar.setVisibility(View.VISIBLE);
                                                    }

                                                    @Override
                                                    public void onLoadingFailed(String imageUri, View view,
                                                                                FailReason failReason) {
                                                        // holder.progressBar.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onLoadingComplete(String imageUri, View view,
                                                                                  Bitmap loadedImage) {
                                                        // holder.progressBar.setVisibility(View.GONE);
                                                    }
                                                }, new ImageLoadingProgressListener() {
                                                    @Override
                                                    public void onProgressUpdate(String imageUri, View view,
                                                                                 int current, int total) {
                                                        // holder.progressBar.setProgress(Math.round(100.0f
                                                        // * current / total));
                                                    }
                                                });
                                    }


                                    if (UtilityPro.getDataFromJson(json2, "creator_image") == null && UtilityPro.getDataFromJson(json2, "creator_image").equals("") &&
                                            UtilityPro.getDataFromJson(json2, "creator_image").length() > 6) {
                                    } else {
                                        imageLoader.displayImage(UtilityPro.getDataFromJson(json2, "creator_image"),
                                                imgCreaterPics, options, new SimpleImageLoadingListener() {
                                                    @Override
                                                    public void onLoadingStarted(String imageUri, View view) {
                                                        // holder.progressBar.setProgress(0);
                                                        // holder.progressBar.setVisibility(View.VISIBLE);
                                                    }

                                                    @Override
                                                    public void onLoadingFailed(String imageUri, View view,
                                                                                FailReason failReason) {
                                                        // holder.progressBar.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onLoadingComplete(String imageUri, View view,
                                                                                  Bitmap loadedImage) {
                                                        // holder.progressBar.setVisibility(View.GONE);
                                                    }
                                                }, new ImageLoadingProgressListener() {
                                                    @Override
                                                    public void onProgressUpdate(String imageUri, View view,
                                                                                 int current, int total) {
                                                        // holder.progressBar.setProgress(Math.round(100.0f
                                                        // * current / total));
                                                    }
                                                });
                                    }

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


    public void applyEvent(Map<String, String> params) {
        String Url = Constants.APPLY_EVENT;
        params.put("time_zone", UtilityPro.getTimezone());
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET APPLY_EVENT",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    is_event_apply = true;
                                    mList.clear();
                                    btnWillJoin.setBackgroundColor(Color.parseColor("#D9DADC"));
                                    btnWillJoin.setText("WITHDRAW YOUR APPLICATION");
                                    JSONObject json2 = responce_obj.getJSONObject("results");
                                    txtVacancyCount.setText(UtilityPro.getDataFromJson(json2, "event_join_count"));

                                    JSONArray image_url = json2.getJSONArray("event_join_user_image");

                                    for (int i = 0; i < image_url.length(); i++) {
                                        ImageMoreModel imageMoreModel = new ImageMoreModel();
                                        imageMoreModel.setImage_url(image_url.getString(i));
                                        mList.add(imageMoreModel);
                                    }
                                    imageMoreAdapter.notifyDataSetChanged();
                                    UtilityPro.toast(UtilityPro.getDataFromJson(responce_obj, "message"));
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


    public void withrowEvent(Map<String, String> params) {
        params.put("time_zone", UtilityPro.getTimezone());
        String Url = Constants.WITHDRAW_EVENT;
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET WITHDRAW_EVENT",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    is_event_apply = false;
                                    btnWillJoin.setBackgroundResource(R.drawable.notification_accepted);
                                    btnWillJoin.setText("I WILL JOIN");
                                    JSONObject json2 = responce_obj.getJSONObject("results");
                                    txtVacancyCount.setText(UtilityPro.getDataFromJson(json2, "event_join_count"));
                                    JSONArray image_url = json2.getJSONArray("event_join_user_image");
                                    mList.clear();
                                    System.out.println("========valkesh===withrowEvent======" + mList.size());
                                    for (int i = 0; i < image_url.length(); i++) {
                                        ImageMoreModel imageMoreModel = new ImageMoreModel();
                                        imageMoreModel.setImage_url(image_url.getString(i));
                                        mList.add(imageMoreModel);
                                    }
                                    imageMoreAdapter.notifyDataSetChanged();
                                    UtilityPro.toast(UtilityPro.getDataFromJson(responce_obj, "message"));
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
    public void onDetailsListener(String index, int type, String image_url, ArrayList<ImageMoreModel> mList, int pos) {

    }


}