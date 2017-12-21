package com.dr.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;

import com.dr.app.adapter.CategoriesAdapter;
import com.dr.app.adapter.CountryCodeAdapter;
import com.dr.app.model.ContryCodeModel;
import com.dr.app.pinview.PinView;
import com.dr.app.pinview.PinViewSettings;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;
import com.dr.app.widgets.CTextViewjustsport;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity  implements CountryCodeAdapter.OnDetailsItem {
    EditText edtPhoneNumber;
    TextView txtPreCode;
    Button btnSignup;
    List<String> filterlist;
    LinearLayout spinnerFilter;
    PopupWindow dropdownGender;
    PinView pinView;
    CTextViewjustsport txtCountryName;
    ImageView btnBack;
    CountryCodeAdapter.OnDetailsItem getDetails;
    ArrayList<ContryCodeModel> mlistcategoriesModels  = new ArrayList<ContryCodeModel>();

    boolean is_country_selected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.sign_up_bg, getBaseContext().getTheme()));
        } else {
            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.sign_up_bg));
        }
        setContentView(R.layout.activity_registration);
        getDetails = RegistrationActivity.this;
        saveCountryCode();
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        txtPreCode = (TextView) findViewById(R.id.txtPreCode);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        spinnerFilter = (LinearLayout) findViewById(R.id.spinnerFilter);
        txtCountryName = (CTextViewjustsport) findViewById(R.id.txtCountryName);


      //  pinView.setTitles(title);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg_intent = new Intent(RegistrationActivity.this ,LoginActivity.class);
                startActivity(reg_intent);
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityPro.isNetworkAvaliable(RegistrationActivity.this)) {

                   if(is_country_selected){
                   if(!edtPhoneNumber.getText().toString().equalsIgnoreCase("") || edtPhoneNumber.getText().toString().length() < 0) {
                       Map<String, String> params = new HashMap<String, String>();
                       params.put("type", "1");
                       params.put("device_token", Pref.getDeviceToken(Constants.DEVICE_TOKEN_GOOGLE));
                       params.put("mobile_no", txtPreCode.getText().toString() + edtPhoneNumber.getText().toString());
                       params.put("device_type", Constants.DEVICE_TYPE);
                       params.put("time_zone", UtilityPro.getTimezone());
                       logInWebCall(params);
                   }else{
                       UtilityPro.toast(RegistrationActivity.this.getString(R.string.please_enter_your_mobile_no));
                   }
                    }else{
                        UtilityPro.toast(RegistrationActivity.this.getString(R.string.please_enter_your_country_name));
                    }
                } else {
                    UtilityPro.toast(RegistrationActivity.this.getString(R.string.please_check_your_internet_connection));
                }
            }
        });
        spinnerFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropdownGender =  UtilityPro.popupWindowDogsCustom(new CountryCodeAdapter(RegistrationActivity.this, getDetails, mlistcategoriesModels));
                dropdownGender.setWidth(view.getWidth() + UtilityPro.dptopx(4));
                UtilityPro.log(" Registration : width =  " + view.getWidth());
                dropdownGender.showAsDropDown(view, -UtilityPro.dptopx(2), -view.getHeight());
            }
        });


//        spinnerFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dropdownGender =  UtilityPro.popupWindowDogs(filterlist, new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Animation fadeInAnimation = AnimationUtils
//                                .loadAnimation(view.getContext(),
//                                        android.R.anim.fade_in);
//                        fadeInAnimation.setDuration(1000);
//                        view.startAnimation(fadeInAnimation);
//                        // dismiss the pop up
//                        dropdownGender.dismiss();
//                        // get the text and set it as the button text
//                        String selectedItemText = ((TextView) view).getText().toString();
//                        txtPreCode.setText("+"+"120");
//                    }
//                });
//                dropdownGender.setWidth(view.getWidth() + UtilityPro.dptopx(4));
//                UtilityPro.log(" Registration : width =  " + view.getWidth());
//                dropdownGender.showAsDropDown(view, -UtilityPro.dptopx(2), -view.getHeight());
//            }
//        });
    }




    // App Login
    public void logInWebCall(Map<String, String> params) {
        String Url = Constants.HOME_LOGIN;
        Golly.showDarkProgressDialog(RegistrationActivity.this);
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL_LOGIN/SIGNUP",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    JSONObject json2 = responce_obj.getJSONObject("results");
//                                    if(UtilityPro.getDataFromJsonBoolean(json2,"is_update") == false){
                                        Pref.setUserID(json2.getString("user_id"));
                                        Intent reg_intent = new Intent(RegistrationActivity.this ,RegistrationConfirmActivity.class);
                                        reg_intent.putExtra("response_conf", response);
                                        reg_intent.putExtra("response_number", response);
                                        startActivity(reg_intent);
                                        finish();
//                                    }

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
    public void onDetailsListener(String country_name, String country_code, ArrayList<ContryCodeModel> mList, int pos) {
        txtPreCode.setText(country_code);
        txtCountryName.setText(country_name);
        is_country_selected = true;
        dropdownGender.dismiss();
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("country/countrycodes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void saveCountryCode(){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("formules");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                ContryCodeModel videoModel = new ContryCodeModel();
                videoModel.setCountry_name(UtilityPro.getDataFromJson(jo_inside, "name"));
                videoModel.setCountry_code(UtilityPro.getDataFromJson(jo_inside, "dial_code"));
                mlistcategoriesModels.add(videoModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent reg_intent = new Intent(RegistrationActivity.this ,LoginActivity.class);
            startActivity(reg_intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
