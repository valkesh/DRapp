

package com.dr.app.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.dr.app.LoginActivity;
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

public class PrivacyPolicyFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_ITEM = "item";

    private String item;

    public PrivacyPolicyFragment() {
    }

    public static PrivacyPolicyFragment newInstance(String item) {
        PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  item = getArguments().getString(ARG_ITEM);
    }

    private ImageView imgBack;
    private TextView btnSignOut;
    TextView txtValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        txtValue = (TextView) view.findViewById(R.id.txtValue);
        imgBack.setOnClickListener(this);
        Map<String, String> paramss = new HashMap<String, String>();
        GetPrivacyPolicy(paramss);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                ((MainActivity) getActivity()).onBackPressed();
                break;
        }
    }


    public void GetPrivacyPolicy(Map<String, String> params) {
        String Url = Constants.PRIVACY_POLICY_SETTINGS;
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET PROFILE",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {

                                    JSONArray jsonArray = responce_obj.getJSONArray("results");

                                    for (int event = 0; event < jsonArray.length(); event++) {
                                        JSONObject object = jsonArray.getJSONObject(event);
                                        if (event == 0) {
                                            String value = UtilityPro.getDataFromJson(object, "description");
                                            System.out.println("=======description=======" + value);
                                            txtValue.setText(Html.fromHtml(value));
                                        }
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
}