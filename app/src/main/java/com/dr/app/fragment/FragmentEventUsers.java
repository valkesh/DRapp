

package com.dr.app.fragment;

import android.app.NotificationManager;
import android.content.Context;
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
import com.dr.app.adapter.EventUserAdapter;
import com.dr.app.model.EventUserModel;
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

public class FragmentEventUsers extends Fragment implements EventUserAdapter.OnDetailsItem {
    private static final String ARG_ITEM = "item";

    private String item;
    boolean is_page = false;
    int pager = 0;
    LinearLayout lnParent;
    static String event_id = "";

    public ArrayList<EventUserModel> eventUserModels = new ArrayList<EventUserModel>();
    public EventUserAdapter eventUserAdapter;
    public EventUserAdapter.OnDetailsItem onDetailsItem;
    // public View view;
    public PagingListView listEvenUsers;

    public FragmentEventUsers() {
    }

    public static FragmentEventUsers newInstance(String item, String event_id_) {
        FragmentEventUsers fragment = new FragmentEventUsers();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        event_id = event_id_;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //item = getArguments().getString(ARG_ITEM);
    }


    TextView txtToast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onDetailsItem = this;
        //  if (view == null) {
        View view = inflater.inflate(R.layout.fragment_event_users, null, false);
        listEvenUsers = (PagingListView) view.findViewById(R.id.listEvenUsers);
        txtToast = (TextView) view.findViewById(R.id.txtToast);
        lnParent = (LinearLayout) view.findViewById(R.id.lnParent);
        eventUserAdapter = new EventUserAdapter(getActivity(), onDetailsItem, eventUserModels);

        Map<String, String> paramss = new HashMap<String, String>();
        if (Pref.getIsLogin()) {
            paramss.put("user_id", Pref.getUserID());
            paramss.put("event_id", event_id);
            getEventUsersList(paramss);
        }
        return view;
//        return inflater.inflate(R.layout.fragment_your_event_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
    public void getEventUsersList(Map<String, String> params) {
        String Url = Constants.EVENT_USERS;
        //  Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL EVENT USER.",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    JSONArray json2 = responce_obj.getJSONArray("results");
                                    for (int event = 0; event < json2.length(); event++) {
                                        JSONObject object = json2.getJSONObject(event);
                                        EventUserModel eventUserModel = new EventUserModel();
                                        eventUserModel.setAction_id(UtilityPro.getDataFromJsonInt(object, "action_id"));
                                        eventUserModel.setEvent_id(UtilityPro.getDataFromJsonInt(object, "event_id"));
                                        eventUserModel.setEvent_name(UtilityPro.getDataFromJson(object, "event_name"));
                                        eventUserModel.setUser_image(UtilityPro.getDataFromJson(object, "user_image"));
                                        eventUserModel.setUser_id(UtilityPro.getDataFromJsonInt(object, "user_id"));
                                        eventUserModel.setUser_chat_id(UtilityPro.getDataFromJson(object, "user_chat_id"));
                                        eventUserModel.setUser_name(UtilityPro.getDataFromJson(object, "user_name"));
                                        eventUserModel.setType(UtilityPro.getDataFromJsonInt(object, "noti_type"));
                                        eventUserModel.setMessages(UtilityPro.getDataFromJson(object, "and_msg"));
                                        eventUserModel.setIs_online(UtilityPro.getDataFromJsonBoolean(object, "is_online"));
                                        eventUserModels.add(eventUserModel);
                                    }

                                    if (listEvenUsers.getAdapter() == null) {
                                        listEvenUsers.setAdapter(eventUserAdapter);
                                        listEvenUsers.setHasMoreItems(true);
                                        listEvenUsers.setPagingableListener(pm);
                                    }
                                    if (responce_obj.getBoolean("next") == true) {
                                        is_page = true;
                                        pager = pager + 1;
                                        listEvenUsers.onFinishLoading(true, eventUserModels);

                                    } else {
                                        is_page = false;
                                        listEvenUsers.setHasMoreItems(false);
                                        listEvenUsers.onFinishLoading(false, eventUserModels);
                                    }
                                    eventUserAdapter.notifyDataSetChanged();

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


    PagingListView.Pagingable pm = new PagingListView.Pagingable() {
        @Override
        public void onLoadMoreItems() {
            if (pager != 0) {
                Map<String, String> paramss = new HashMap<String, String>();
                paramss.put("user_id", Pref.getUserID());
                paramss.put("event_id", event_id);
                paramss.put("next", String.valueOf(pager));
                getEventUsersList(paramss);
            }
        }
    };

    @Override
    public void onProfile(ArrayList<EventUserModel> mList, int pos) {
        ((MainActivity) getActivity()).showFragment(ProfileFragment.newInstance(String.valueOf(mList.get(pos).getUser_id())));
    }
}