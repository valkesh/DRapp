

package com.dr.app.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.adapter.EventProfileAdapter;
import com.dr.app.model.EventProfileModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;
import com.dr.app.widgets.CButtonViewOpenSans;
import com.dr.app.widgets.CTextViewjustsport;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dr.app.utility.Golly.notificaiton_count;

public class ProfileFragment extends Fragment implements View.OnClickListener, EventProfileAdapter.OnDetailsItem {
    private static final String ARG_ITEM = "item";

    private String item;
    private TextView txtUsername, txtbio;
    private ImageView imgProfile;
    private CTextViewjustsport btnFemale, btnMale;
    private CButtonViewOpenSans btnCreateAccount;
    String isFemale = "1";
    TextView txtLocation, txtNoEvent;
    String camera_image_path;
    boolean is_camera = false;
    Uri imagePath = null;
    public HashMap<Integer, File> files;
    Button btnEditProfile;
    //    public View stickyViewSpacer;
    public ListView listEvent;
    ImageView btnBack, btnSetting;


    EventProfileAdapter eventProfileAdapter;
    ArrayList<EventProfileModel> eventProfileModels = new ArrayList<EventProfileModel>();
    EventProfileAdapter.OnDetailsItem getDetails;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String item) {
        ProfileFragment fragment = new ProfileFragment();
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
        item = getArguments().getString(ARG_ITEM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.fragment_profile, null);
        listEvent = (ListView) view.findViewById(R.id.listEvent);
        getDetails = this;
        eventProfileAdapter = new EventProfileAdapter(getActivity(), getDetails, eventProfileModels);
        listEvent.setAdapter(eventProfileAdapter);
        txtNoEvent = (Button) view.findViewById(R.id.txtNoEvent);
        txtUsername = (TextView) view.findViewById(R.id.txtUsername);
        txtbio = (TextView) view.findViewById(R.id.txtbio);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        btnEditProfile = (Button) view.findViewById(R.id.btnEditProfile);
        txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        btnBack = (ImageView) view.findViewById(R.id.btnBack);
        btnSetting = (ImageView) view.findViewById(R.id.btnSetting);
       // listEvent.addHeaderView(listHeader);

        btnEditProfile.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSetting.setOnClickListener(this);

        if (Pref.getUserID().equalsIgnoreCase(item)) {
            btnBack.setVisibility(View.INVISIBLE);
            btnSetting.setVisibility(View.VISIBLE);
        } else {
            if (Pref.getIsLogin()) {
                btnEditProfile.setText("MESSAGE ME");
                btnBack.setVisibility(View.VISIBLE);
                btnSetting.setVisibility(View.INVISIBLE);
            } else {
                btnEditProfile.setText("MESSAGE ME");
                btnEditProfile.setVisibility(View.GONE);
                btnBack.setVisibility(View.VISIBLE);
                btnSetting.setVisibility(View.INVISIBLE);
            }
        }

        Map<String, String> paramss = new HashMap<String, String>();
        paramss.put("user_id", item);
        paramss.put("time_zone", UtilityPro.getTimezone());
        getProfileDetails(paramss);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnEditProfile) {
            if (Pref.getUserID().equalsIgnoreCase(item)) {
                ((MainActivity) getActivity()).showFragment(EditProfileFragment.newInstance(getString(R.string.editprofile)));
            } else {
                if (Pref.getIsLogin()) {
                    if (txtUsername.getText().toString().trim().length() == 0) {

                    } else {
//                        startActivity(new Intent(getActivity(), FinalChatActivity.class).putExtra("FROM_MESSGE", item).putExtra("titlename", txtUsername.getText().toString()).putExtra("type", "0").putExtra("other_image", other_user));
                    }
                } else {
                    ((MainActivity) getActivity()).showFragment(IsNotLoginProfileFragment.newInstance(getString(R.string.isnotuserprofile)));
                }
            }
        }
        if (view.getId() == R.id.btnBack) {
            if (!Pref.getUserID().equalsIgnoreCase(item)) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        }
        if (view.getId() == R.id.btnSetting) {
            if (Pref.getUserID().equalsIgnoreCase(item)) {
                ((MainActivity) getActivity()).showFragment(SettingsFragment.newInstance(""));
            }
        }
    }

    String other_user = "";
    int user_id = 0;

    public void getProfileDetails(Map<String, String> params) {
        String Url = Constants.GET_PROFILE;
//      Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET PROFILE",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    if (Pref.getIsLogin()) {
                                        ((MainActivity) getActivity()).updateNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));
                                        notificaiton_count = UtilityPro.getDataFromJsonInt(responce_obj, "notification_count");
                                    }
                                    JSONObject json2 = responce_obj.getJSONObject("results");
//                                    if(UtilityPro.getDataFromJsonBoolean(json2,"is_update") == false){
                                    //Pref.setUserID(json2.getString("user_id"));

                                    txtUsername.setText(json2.getString("user_name"));
                                    txtbio.setText(json2.getString("user_bio"));
                                    // txtLocation.setText(json2.getString(""));
                                    other_user = json2.getString("user_image");
                                    int user_id = json2.getInt("user_id");
                                    Golly.imageLoader.displayImage(json2.getString("user_image"),
                                            imgProfile, Golly.options_profile, new SimpleImageLoadingListener() {
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

                                    JSONArray event_array = json2.getJSONArray("user_event");
                                    for (int event = 0; event < event_array.length(); event++) {
                                        JSONObject object = event_array.getJSONObject(event);
                                        EventProfileModel eventProfileModel = new EventProfileModel();
                                        eventProfileModel.setEvent_id(UtilityPro.getDataFromJsonInt(object, "event_id"));
                                        eventProfileModel.setEvent_image(UtilityPro.getDataFromJson(object, "event_image"));
                                        eventProfileModel.setEvent_title(UtilityPro.getDataFromJson(object, "event_name"));
                                        eventProfileModel.setEvent_sport_name(UtilityPro.getDataFromJson(object, "game_name"));
                                        eventProfileModel.setEvent_date_time(UtilityPro.getDataFromJson(object, "event_full_date_time"));
                                        eventProfileModel.setEvent_status(UtilityPro.getDataFromJsonBoolean(object, "event_status"));
                                        eventProfileModel.setUser_id(user_id);
                                        eventProfileModels.add(eventProfileModel);
                                    }
                                    eventProfileAdapter.notifyDataSetChanged();
                                    if (eventProfileModels.size() != 0) {
                                        txtNoEvent.setVisibility(View.GONE);
                                    } else {
                                        txtNoEvent.setVisibility(View.VISIBLE);
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

    @Override
    public void onDetailsListener(ArrayList<EventProfileModel> mList, int pos) {
        ((MainActivity) getActivity()).showFragment(EventMoreDetailsFragment.newInstance(String.valueOf(mList.get(pos).getEvent_id())));
    }

    @Override
    public void onEditListener(ArrayList<EventProfileModel> mList, int pos, boolean isEdit) {
        if (isEdit) {
            ((MainActivity) getActivity()).showFragment(EditEventFragment.newInstance(String.valueOf(mList.get(pos).getEvent_id()), isEdit));
        } else {
            ((MainActivity) getActivity()).showFragment(ReEventFragment.newInstance(String.valueOf(mList.get(pos).getEvent_id()), isEdit));
        }
    }
}