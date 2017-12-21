package com.dr.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.dr.app.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;
import com.dr.app.widgets.CTextViewjustsport;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnFacebook, btnSignup;
    private CallbackManager callbackManager;
    private CTextViewjustsport txtSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.spalce_bg, getBaseContext().getTheme()));
        } else {
            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.spalce_bg));
        }
        generateKey(LoginActivity.this);
        setContentView(R.layout.activity_login);

        btnFacebook = (Button) findViewById(R.id.btnFacebook);
        txtSkip = (CTextViewjustsport) findViewById(R.id.txtSkip);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnFacebook.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        txtSkip.setOnClickListener(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
//        FirebaseMessaging.getInstance().subscribeToTopic("justsport");
//        Pref.setDeviceToken(Constants.DEVICE_TOKEN_GOOGLE, FirebaseInstanceId.getInstance().getToken());

        //  System.out.println("=====login====="+ FirebaseInstanceId.getInstance().getToken().trim());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnFacebook) {
            if (UtilityPro.isNetworkAvaliable(LoginActivity.this)) {
                LoginManager.getInstance().logOut();
                CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeSessionCookie();
                onFacebooklogin();
            } else {
                UtilityPro.toast(LoginActivity.this.getString(R.string.please_check_your_internet_connection));
            }

        } else if (view.getId() == R.id.btnSignup) {
            Intent reg_intent = new Intent(this, RegistrationActivity.class);
            startActivity(reg_intent);
            finish();
        } else if (view.getId() == R.id.txtSkip) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void onFacebooklogin() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        Log.e("Success", "Success");
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            Log.e("ERROR", "");
                                        } else {
                                            try {
                                                AccessToken token = AccessToken.getCurrentAccessToken();

                                                String jsonresult = String.valueOf(json);
                                                System.out.println("====JSON Result json ===" + json);
                                                System.out.println("====JSON Result json ===" + token.getToken());
                                                System.out.println("====JSON Result id ===" + json.getString("id"));
                                                String url_image = "";
//                                                JSONObject jj =  json.getJSONObject("picture");
//                                                System.out.println("===oop====="+jj.getJSONObject("data"));
//                                                System.out.println("===oop====="+jj.getString("url"));


                                                JSONObject uniObject = json.getJSONObject("picture");

                                                JSONObject data = uniObject.getJSONObject("data");
                                                String isFemale = "1";
                                                if (json.getString("gender").equalsIgnoreCase("male")) {
                                                    isFemale = "0";
                                                } else {
                                                    isFemale = "1";
                                                }
                                                String st_id = json.getString("id");
                                                String st_name = json.getString("name");

                                                Map<String, String> params = new HashMap<String, String>();

                                                params.put("type", "0");
                                                params.put("access_token", token.getToken());
                                                params.put("facebook_id", json.getString("id"));
                                                params.put("user_gender", isFemale);
                                                params.put("user_bio", "");
                                                params.put("user_image", data.getString("url"));
                                                params.put("user_name", json.getString("name"));

                                                params.put("device_token", Pref.getDeviceToken(Constants.DEVICE_TOKEN_GOOGLE));

                                                //  params.put("mobile no", "");
                                                params.put("device_type", Constants.DEVICE_TYPE);
                                                params.put("time_zone", UtilityPro.getTimezone());
                                                Pref.setFaceBookLogin(true);
                                                logInWebCall(params);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,picture,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login cancel", Toast.LENGTH_LONG).show();
                        Log.d("TAG_CANCEL", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(LoginActivity.this, "Login unsuccessful", Toast.LENGTH_LONG).show();
                        Log.d("TAG_ERROR", error.toString());
                        try {
                            LoginManager.getInstance().logOut();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    // Facebook Login
    public void logInWebCall(Map<String, String> params) {
        String Url = Constants.HOME_LOGIN;
        Golly.showDarkProgressDialog(LoginActivity.this);
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
                                    Pref.setUserName(json2.getString("user_name"));
                                    Pref.setUserImage(json2.getString("user_image"));
                                    Pref.setIsLogin(true);
//                                    Intent serviceIntent = new Intent(getBaseContext(),
//                                            XMPPService.class);
//                                    if (ChatComman.isMyServiceRunning(XMPPService.class, LoginActivity.this))
//                                        stopService(serviceIntent);
//                                    startService(serviceIntent);
                                    Intent reg_intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(reg_intent);
                                    finish();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);

        } catch (Exception e) {

        }
    }

    public static void generateKey(Context act) {
        try {
            PackageInfo info = act.getPackageManager().getPackageInfo(
                    "com.dr.app", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("===hash_key=======",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
                // txtText.setText(Base64.encodeToString(md.digest(),
                // Base64.DEFAULT));
//                Toast.makeText(act,Base64.encodeToString(md.digest(), Base64.DEFAULT).toString() , Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Error", e.getMessage());

        } catch (NoSuchAlgorithmException e) {
            Log.e("Error", e.getMessage());
        }
    }


}
