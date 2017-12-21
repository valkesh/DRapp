package com.dr.app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.facebook.CallbackManager;

import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;
import com.dr.app.widgets.CEditTextViewjustsport;

import com.google.android.gms.common.api.GoogleApiClient;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RegistrationConfirmActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnFacebook, btnSignup;
    private CallbackManager callbackManager;
    private GoogleApiClient client;
    private String responce_step = "";
    private String responce_mobile_no = "";

    private CEditTextViewjustsport edtconform;
    private Button btnConfirm;
    private LinearLayout lnResend;
    private String[] title = {
            "0",
            "0",
            "0",
            "0",
            "0",
            "0"
    };
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.sign_up_bg, getBaseContext().getTheme()));
        } else {
            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.sign_up_bg));
        }

        setContentView(R.layout.activity_registration_confirm);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            responce_step = extras.getString("response_conf");
            responce_mobile_no = extras.getString("response_number");
        }
        edtconform = (CEditTextViewjustsport) findViewById(R.id.edtconform);
        lnResend = (LinearLayout) findViewById(R.id.lnResend);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        lnResend.setOnClickListener(this);

//        pinView.setTitles(title);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnConfirm) {

            if (UtilityPro.isNetworkAvaliable(RegistrationConfirmActivity.this)) {
                try {
                    if (!responce_step.equalsIgnoreCase("")) {
                        JSONObject responce_obj = new JSONObject(responce_step);
                        if (responce_obj.getBoolean("status")) {
                            JSONObject json2 = responce_obj.getJSONObject("results");
                            if (edtconform.getText().toString().equalsIgnoreCase(UtilityPro.getDataFromJson(json2, "otp"))) {
                                if (UtilityPro.getDataFromJsonBoolean(json2, "is_update") == false) {
                                    // Pref.setIsLogin(true);

                                    Intent reg_intent = new Intent(RegistrationConfirmActivity.this, RegistrationProfileActivity.class);
                                    startActivity(reg_intent);
                                    finish();
                                } else {
                                    Pref.setIsLogin(true);
                                    Pref.setFaceBookLogin(false);
                                    Pref.setUserName(json2.getString("user_name"));
                                    Pref.setUserImage(json2.getString("user_image"));

                                    Pref.setUserID(json2.getString("user_id"));
//                                    Intent serviceIntent = new Intent(getBaseContext(),
//                                            XMPPService.class);
//                                    if (ChatComman.isMyServiceRunning(XMPPService.class, RegistrationConfirmActivity.this))
//                                        stopService(serviceIntent);
//                                    startService(serviceIntent);

                                    Intent reg_intent = new Intent(RegistrationConfirmActivity.this, MainActivity.class);
                                    startActivity(reg_intent);
                                    finish();
                                }
                            } else {
                                UtilityPro.toast("Please try again.");
                            }
                        } else {
                            if (!UtilityPro.getDataFromJson(responce_obj, "message").equalsIgnoreCase("")) {
                                UtilityPro.toast(UtilityPro.getDataFromJson(responce_obj, "message"));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                UtilityPro.toast(RegistrationConfirmActivity.this.getString(R.string.please_check_your_internet_connection));
            }
        } else if (view.getId() == R.id.btnBack) {
            Intent reg_intent = new Intent(RegistrationConfirmActivity.this, RegistrationActivity.class);
            startActivity(reg_intent);
            finish();
        } else if (view.getId() == R.id.lnResend) {

            if (UtilityPro.isNetworkAvaliable(RegistrationConfirmActivity.this)) {
                edtconform.setText("");
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "1");
                params.put("device_token", Pref.getDeviceToken(Constants.DEVICE_TOKEN_GOOGLE));
                params.put("mobile_no", responce_mobile_no);
                params.put("device_type", Constants.DEVICE_TYPE);
                params.put("time_zone", UtilityPro.getTimezone());
                logWebResendCall(params);

            } else {
                UtilityPro.toast(RegistrationConfirmActivity.this.getString(R.string.please_check_your_internet_connection));
            }
        }
    }

    // App Login
    public void logWebResendCall(Map<String, String> params) {
        String Url = Constants.HOME_LOGIN;
        Golly.showDarkProgressDialog(RegistrationConfirmActivity.this);
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
                                    responce_step = response;
//                                }

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent reg_intent = new Intent(RegistrationConfirmActivity.this, RegistrationActivity.class);
            startActivity(reg_intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
