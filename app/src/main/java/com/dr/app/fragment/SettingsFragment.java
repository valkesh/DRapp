

package com.dr.app.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.dr.app.LoginActivity;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.Utility;
import com.dr.app.utility.UtilityPro;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_ITEM = "item";

    private String item;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String item) {
        SettingsFragment fragment = new SettingsFragment();
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
    private TextView btnSignOut, txtPrivacyPolicy, txtTermsConditions;
    private LinearLayout lnSwitchPushNotification, lnInviteFriends;
    JellyToggleButton switchPushNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        btnSignOut = (TextView) view.findViewById(R.id.btnSignOut);
        txtPrivacyPolicy = (TextView) view.findViewById(R.id.txtPrivacyPolicy);
        txtTermsConditions = (TextView) view.findViewById(R.id.txtTermsConditions);
        lnSwitchPushNotification = (LinearLayout) view.findViewById(R.id.lnSwitchPushNotification);
        lnInviteFriends = (LinearLayout) view.findViewById(R.id.lnInviteFriends);
        switchPushNotification = (JellyToggleButton) view.findViewById(R.id.switchPushNotification);

        lnSwitchPushNotification.setOnClickListener(this);
        lnInviteFriends.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtPrivacyPolicy.setOnClickListener(this);
        txtTermsConditions.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);

//        Utility.writeSharedPreferencesBool(getActivity(), Constants.is_push_notification, UtilityPro.getDataFromJsonBoolean(responce_obj,"push_notification_switch"));

        System.out.println("====Utility.getAppPrefBool(getActivity(), Constants.is_push_notification)=======" + Utility.getAppPrefBool(getActivity(), Constants.is_push_notification));

        if (Utility.getAppPrefBool(getActivity(), Constants.is_push_notification)) {
            switchPushNotification.setCheckedImmediately(true, false);
        } else {
            switchPushNotification.setCheckedImmediately(false, false);
        }

        switchPushNotification.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                // process - current process of JTB, between [0, 1]
                // state   - current state of JTB, it is one of State.LEFT, State.LEFT_TO_RIGHT, State.RIGHT and State.RIGHT_TO_LEFT
                // jtb     - the JTB

                if (state.equals(State.LEFT)) {
                    Map<String, String> paramss = new HashMap<String, String>();
                    paramss.put("user_id", Pref.getUserID());
                    paramss.put("push_notification", "0");
                    pushNotification(paramss);
                }
                if (state.equals(State.RIGHT)) {
                    Map<String, String> paramss = new HashMap<String, String>();
                    paramss.put("user_id", Pref.getUserID());
                    paramss.put("push_notification", "1");
                    pushNotification(paramss);
                }
            }
        });
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
            case R.id.txtPrivacyPolicy:
                ((MainActivity) getActivity()).showFragment(PrivacyPolicyFragment.newInstance(""));
                break;
            case R.id.txtTermsConditions:
                ((MainActivity) getActivity()).showFragment(TermConditonsFragment.newInstance(""));
                break;
            case R.id.btnSignOut:
//                Map<String, String> paramss = new HashMap<String, String>();
//                logout(paramss);
                //closed();
                showLogoutDialog();
                break;
            case R.id.switchPushNotification:
                break;
            case R.id.lnInviteFriends:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Pref.getUserName() + " invited you for Justsport application." +
                        "Click on the below link to download the application. " + Constants.APPLICATION_LINK);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Justsport app invitation");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Invite with"));
                break;
            case R.id.lnSwitchPushNotification:
                switchPushNotification.performClick();
                break;
        }
    }

    public static void logout(Map<String, String> params , final Activity act) {
        String Url = Constants.LOGOUT;
        Golly.showDarkProgressDialog(act);
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL LOGOUT",
                new Golly.GollyListner() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    //JSONObject json2 = responce_obj.getJSONObject("results");
                                    Pref.setIsLogin(false);
                                    Pref.setUserID("");
                                    if (Pref.getFaceBookLogin()) {
                                        Pref.setFaceBookLogin(false);
                                        FacebookSdk.sdkInitialize(act);
                                        LoginManager.getInstance().logOut();
                                        CookieSyncManager.createInstance(act);
                                        CookieManager cookieManager = CookieManager.getInstance();
                                        cookieManager.removeSessionCookie();
                                    }
                                    Intent login_intent = new Intent(act, LoginActivity.class);
                                    login_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    act.startActivity(login_intent);
                                    act.finish();
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

    public void pushNotification(Map<String, String> params) {
        String Url = Constants.PUSHNOTIFICATION_TOGGEL;
        // Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL PUSHNOTIFICATION_TOGGEL",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    //JSONObject json2 = responce_obj.getJSONObject("results");

//                                    push_notification_switch

                                    Utility.writeSharedPreferencesBool(getActivity(), Constants.is_push_notification, UtilityPro.getDataFromJsonBoolean(responce_obj, "push_notification_switch"));

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


    Dialog dialog;

    public void closed() {
        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialoglogout, null);
        dialog.setCancelable(true);
        dialog.setContentView(view);
        // Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.CENTER;
//        wlp.flags = WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//        window.setAttributes(wlp);
//        dialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();

        view.findViewById(R.id.txtNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.txtYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pref.setIsLogin(false);
                dialog.dismiss();
                Map<String, String> paramss = new HashMap<String, String>();
                //logout(paramss);
            }
        });
    }


    public static class ConfirmLogoutDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Bundle bundle = this.getArguments();

            String positive_text = bundle.getString("POSITIVE_TEXT");
            String negative_text = bundle.getString("NEGATIVE_TEXT");
            String title_text = bundle.getString("TITLE_TEXT");
            String message_text = bundle.getString("MESSAGE_TEXT");

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title_text);
            builder.setMessage(message_text)
                    .setPositiveButton(positive_text, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Map<String, String> paramss = new HashMap<String, String>();
                            logout(paramss , getActivity());
                        }
                    })
                    .setNegativeButton(negative_text, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private void showLogoutDialog() {
        ConfirmLogoutDialogFragment confirmDialogFragment = new ConfirmLogoutDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("POSITIVE_TEXT", getString(R.string.txt_logout));
        bundle.putString("NEGATIVE_TEXT", getString(R.string.txt_cancel));
        bundle.putString("TITLE_TEXT", "Logout");
        bundle.putString("MESSAGE_TEXT", getString(R.string.txt_logout_confirm));
        confirmDialogFragment.setArguments(bundle);
        confirmDialogFragment.show(getActivity().getSupportFragmentManager(), ConfirmLogoutDialogFragment.class.toString());
    }
}