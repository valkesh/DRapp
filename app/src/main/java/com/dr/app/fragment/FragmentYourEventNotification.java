

package com.dr.app.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.adapter.YouEventNotificationAdapter;
import com.dr.app.model.YouEventNotificationModel;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.PagingListView;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dr.app.utility.Golly.notificaiton_count;

public class FragmentYourEventNotification extends Fragment implements YouEventNotificationAdapter.OnDetailsItem {
    private static final String ARG_ITEM = "item";

    private String item;
    boolean is_page = false;
    int pager = 0;
    LinearLayout lnParent;

    public static OnnotificationCount onnotificationCount;
    public ArrayList<YouEventNotificationModel> youEventNotificationModels = new ArrayList<YouEventNotificationModel>();
    public YouEventNotificationAdapter youEventNotificationAdapter;
    public YouEventNotificationAdapter.OnDetailsItem onDetailsItem;
    // public View view;
    public PagingListView listYourEventNoitification;

    public FragmentYourEventNotification() {
    }

    public static FragmentYourEventNotification newInstance(String item, boolean type, String event_id, OnnotificationCount onnotificationCount) {
        FragmentYourEventNotification fragment = new FragmentYourEventNotification();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        FragmentYourEventNotification.onnotificationCount = onnotificationCount;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //item = getArguments().getString(ARG_ITEM);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        /*Snackbar snack = Snackbar.make(lnParent, "valkesh patel", Snackbar.LENGTH_LONG);
        snack.show();*/

        if (isVisibleToUser) {
            pager = 0;
            Map<String, String> paramss = new HashMap<String, String>();
            paramss.put("user_id", Pref.getUserID());
            paramss.put("type", "1");
            paramss.put("time_zone", UtilityPro.getTimezone());
            paramss.put("page", String.valueOf(pager));
            getYourEventNotification(paramss);
        }
        System.out.println("=========valkesh patel view pager setUserVisibleHint  FragmentYourEventNotification========" + isVisibleToUser);
    }

    TextView txtToast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onDetailsItem = this;
        //  if (view == null) {
        View view = inflater.inflate(R.layout.fragment_your_event_notification, null, false);
        listYourEventNoitification = (PagingListView) view.findViewById(R.id.listYourEventNoitification);
        txtToast = (TextView) view.findViewById(R.id.txtToast);
        lnParent = (LinearLayout) view.findViewById(R.id.lnParent);
        youEventNotificationAdapter = new YouEventNotificationAdapter(getActivity(), onDetailsItem, youEventNotificationModels);
        // listYourEventNoitification.setAdapter(youEventNotificationAdapter);
        //  }
        NotificationManager mNotificationManager = (NotificationManager) getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        return view;
//        return inflater.inflate(R.layout.fragment_your_event_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

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

    public void getYourEventNotification(Map<String, String> params) {
        String Url = Constants.NOTIFICATION;
        //  Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET NOTIFICATION",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    //if (UtilityPro.getDataFromJsonInt(responce_obj, "this_noti_count") != 0) {
                                    ((MainActivity) getActivity()).updateNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));
                                    FragmentYourEventNotification.onnotificationCount.onNotificationCount(UtilityPro.getDataFromJsonInt(responce_obj, "youinevent_count"));
                                    notificaiton_count = UtilityPro.getDataFromJsonInt(responce_obj, "notification_count");

                                    if (youEventNotificationModels != null && youEventNotificationModels.size() != 0) {
                                        youEventNotificationModels.clear();
                                    }
//                                    ShortcutBadger.removeCount(context);
                                    //  }
                                    JSONArray json2 = responce_obj.getJSONArray("results");

                                    for (int event = 0; event < json2.length(); event++) {
                                        JSONObject object = json2.getJSONObject(event);
                                        YouEventNotificationModel youEventNotificationModel = new YouEventNotificationModel();
                                        youEventNotificationModel.setAction_id(UtilityPro.getDataFromJsonInt(object, "action_id"));
                                        youEventNotificationModel.setEvent_id(UtilityPro.getDataFromJsonInt(object, "event_id"));
                                        youEventNotificationModel.setEvent_name(UtilityPro.getDataFromJson(object, "event_name"));
                                        youEventNotificationModel.setUser_image(UtilityPro.getDataFromJson(object, "user_image"));
                                        youEventNotificationModel.setUser_id(UtilityPro.getDataFromJsonInt(object, "user_id"));
                                        youEventNotificationModel.setUser_chat_id(UtilityPro.getDataFromJson(object, "user_chat_id"));
                                        youEventNotificationModel.setUser_name(UtilityPro.getDataFromJson(object, "user_name"));
                                        youEventNotificationModel.setType(UtilityPro.getDataFromJsonInt(object, "noti_type"));
                                        youEventNotificationModel.setMessages(UtilityPro.getDataFromJson(object, "and_msg"));
                                        youEventNotificationModel.setIs_online(UtilityPro.getDataFromJsonBoolean(object, "is_online"));
                                        youEventNotificationModels.add(youEventNotificationModel);
                                    }

                                    if (listYourEventNoitification.getAdapter() == null) {
                                        listYourEventNoitification.setAdapter(youEventNotificationAdapter);
                                        listYourEventNoitification.setHasMoreItems(true);
                                        listYourEventNoitification.setPagingableListener(pm);
                                    }
                                    if (responce_obj.getBoolean("next") == true) {
                                        is_page = true;
                                        pager = pager + 1;
                                        listYourEventNoitification.onFinishLoading(true, youEventNotificationModels);

                                    } else {
                                        is_page = false;
                                        listYourEventNoitification.setHasMoreItems(false);
                                        listYourEventNoitification.onFinishLoading(false, youEventNotificationModels);
                                    }
                                    youEventNotificationAdapter.notifyDataSetChanged();

//                                    try {
//                                        NotificationFragment nn = new NotificationFragment();
//                                        nn.setTextNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));
//
//                                    } catch (Exception e) {
//
//                                    }
                                    // youEventNotificationAdapter.notifyDataSetChanged();
//                                    JSONArray event_array = json2.getJSONArray("data");
//                                    for (int event = 0; event < event_array.length(); event++) {
//                                    }

                                } else {
                                    if (!UtilityPro.getDataFromJson(responce_obj, "message").equalsIgnoreCase("")) {
                                        UtilityPro.toast(UtilityPro.getDataFromJson(responce_obj, "message"));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (
                                Exception e
                                )

                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void errorResponce() {

                    }
                }

        );
    }

    @Override
    public void onDetailsListener(ArrayList<YouEventNotificationModel> mList, int pos) {
        ((MainActivity) getActivity()).showFragment(EventMoreDetailsFragment.newInstance(String.valueOf(mList.get(pos).getEvent_id())));
    }

    @Override
    public void onAccept(ArrayList<YouEventNotificationModel> mList, int pos) {
        Map<String, String> paramss = new HashMap<String, String>();
        paramss.put("user_id", Pref.getUserID());
        paramss.put("action_id", String.valueOf(mList.get(pos).getAction_id()));
        paramss.put("action", "1");
        paramss.put("time_zone", UtilityPro.getTimezone());
        acceptRejectRequest(paramss, pos);
    }

    @Override
    public void onReject(ArrayList<YouEventNotificationModel> mList, int pos) {
        Map<String, String> paramss = new HashMap<String, String>();
        paramss.put("user_id", Pref.getUserID());
        paramss.put("action_id", String.valueOf(mList.get(pos).getAction_id()));
        paramss.put("action", "3");
        paramss.put("time_zone", UtilityPro.getTimezone());
        acceptRejectRequest(paramss, pos);
    }


    public void setToastMessagesLikeSnackbar(final String messages) {
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long m) {
                long sec = m / 1000 + 1;
                txtToast.setVisibility(View.VISIBLE);
                txtToast.setText(messages);
//                tv.append(sec+" seconds remain\n");
            }

            public void onFinish() {
                txtToast.setVisibility(View.GONE);
//                tv.append("Done!");
            }

        }.start();
    }

    @Override
    public void onMessage(ArrayList<YouEventNotificationModel> mList, int pos) {

//        startActivity(new Intent(getActivity(), FinalChatActivity.class).putExtra("FROM_MESSGE", Constants.App_Name + mList.get(pos).getUser_id())
//                .putExtra("titlename", mList.get(pos).getUser_name()).putExtra("type", "0"));

    }

    @Override
    public void onProfile(ArrayList<YouEventNotificationModel> mList, int pos) {
        ((MainActivity) getActivity()).showFragment(ProfileFragment.newInstance(String.valueOf(mList.get(pos).getUser_id())));

    }

    PagingListView.Pagingable pm = new PagingListView.Pagingable() {
        @Override
        public void onLoadMoreItems() {
            if (pager != 0) {
                Map<String, String> paramss = new HashMap<String, String>();
                paramss.put("user_id", Pref.getUserID());
                paramss.put("type", "1");
                paramss.put("time_zone", UtilityPro.getTimezone());
                paramss.put("next", String.valueOf(pager));
                getYourEventNotification(paramss);
            }
        }
    };


    public void acceptRejectRequest(Map<String, String> params, final int pos) {
        String Url = Constants.NOTIFICATION_ACCEPT_REJECT;
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET NOTIFICATION_ACCEPT_REJECT",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    System.out.println("=response=====" + response);
                                    setToastMessagesLikeSnackbar(UtilityPro.getDataFromJson(responce_obj, "message"));
                                    JSONArray json2 = responce_obj.getJSONArray("results");
                                    for (int event = 0; event < json2.length(); event++) {
                                        JSONObject object = json2.getJSONObject(event);
                                        youEventNotificationModels.get(pos).setType(UtilityPro.getDataFromJsonInt(object, "noti_type"));
                                        youEventNotificationModels.get(pos).setMessages(UtilityPro.getDataFromJson(object, "and_msg"));
                                    }
                                    youEventNotificationAdapter.notifyDataSetChanged();

                                } else {
                                    if (!UtilityPro.getDataFromJson(responce_obj, "message").equalsIgnoreCase("")) {
                                        UtilityPro.toast(UtilityPro.getDataFromJson(responce_obj, "message"));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (
                                Exception e
                                )

                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void errorResponce() {

                    }
                }

        );
    }


    public interface OnnotificationCount {
        public void onNotificationCount(int count);
    }
}