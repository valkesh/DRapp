

package com.dr.app.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.model.EventProfileModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SuggestedSportFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_ITEM = "item";

    private String item;
    ImageView imgBack;
    Button btnSend;
    EditText edtSport;

    public SuggestedSportFragment() {
    }

    public static SuggestedSportFragment newInstance(String item) {
        SuggestedSportFragment fragment = new SuggestedSportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //item = getArguments().getString(ARG_ITEM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggested_sport, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnSend = (Button) view.findViewById(R.id.btnSend);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        edtSport = (EditText) view.findViewById(R.id.edtSport);
        btnSend.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                if (edtSport.getText().toString().trim().length() != 0) {
                    Map<String, String> paramss = new HashMap<String, String>();
                    paramss.put("user_id", Pref.getUserID());
                    paramss.put("request_title", edtSport.getText().toString());
                    paramss.put("time_zone", UtilityPro.getTimezone());
//                    paramss.put("request_images", Pref.getUserID());
//                    paramss.put("request_description", Pref.getUserID());
                    postSuggestedSport(paramss);
                } else {
                    UtilityPro.toast("Please enter your sport name.");
                }
                break;
            case R.id.imgBack:
                getActivity().onBackPressed();
                break;
        }
    }

    public void postSuggestedSport(Map<String, String> params) {
        String Url = Constants.SUGGESTED_SPORT;
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL SUGGESTED_SPORT",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    edtSport.setText("");
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

}