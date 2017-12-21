

package com.dr.app.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.adapter.YouApplyNotificationAdapter;
import com.dr.app.model.YouApplyNotificationModel;
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

public class FragmentYouNotification extends Fragment implements YouApplyNotificationAdapter.OnDetailsItem {
    private static final String ARG_ITEM = "item";
    public static OnYourNotificationCount onYourNotificationCount;

    private String item;
    public ArrayList<YouApplyNotificationModel> youApplyNotificationModels = new ArrayList<YouApplyNotificationModel>();
    public YouApplyNotificationAdapter youApplyNotificationAdapter;
    public YouApplyNotificationAdapter.OnDetailsItem onDetailsItem;
    boolean is_page = false;
    int pager = 0;

    public FragmentYouNotification() {
    }

    public static FragmentYouNotification newInstance(String item, boolean type, String event_id, OnYourNotificationCount onYourNotificationCount) {
        FragmentYouNotification fragment = new FragmentYouNotification();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        FragmentYouNotification.onYourNotificationCount = onYourNotificationCount;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //item = getArguments().getString(ARG_ITEM);

    }

    // View view;
    PagingListView listYourNoitification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onDetailsItem = this;
        View view = inflater.inflate(R.layout.fragment_you_notification, null, false);
        listYourNoitification = (PagingListView) view.findViewById(R.id.listYourNoitification);
        youApplyNotificationAdapter = new YouApplyNotificationAdapter(getActivity(), onDetailsItem, youApplyNotificationModels);
        NotificationManager mNotificationManager = (NotificationManager) getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        return view;
        // return inflater.inflate(R.layout.fragment_you_notification, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            pager = 0;
            Map<String, String> paramss = new HashMap<String, String>();
            paramss.put("user_id", Pref.getUserID());
            paramss.put("type", "0");
            paramss.put("time_zone", UtilityPro.getTimezone());
            paramss.put("page", String.valueOf(pager));
            getYourEventNotification(paramss);
            System.out.println("=========valkesh patel view pager setUserVisibleHint ========" + isVisibleToUser);
        }
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
        // Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET NOTIFICATION",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    ((MainActivity) getActivity()).updateNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));
                                    FragmentYouNotification.onYourNotificationCount.onYourNotificationCount(UtilityPro.getDataFromJsonInt(responce_obj, "yourevent_count"));
                                    notificaiton_count = UtilityPro.getDataFromJsonInt(responce_obj, "notification_count");
                                    if (youApplyNotificationModels != null && youApplyNotificationModels.size() != 0) {
                                        youApplyNotificationModels.clear();
                                    }
                                    JSONArray json2 = responce_obj.getJSONArray("results");

                                    for (int event = 0; event < json2.length(); event++) {
                                        JSONObject object = json2.getJSONObject(event);
                                        YouApplyNotificationModel youApplyNotificationModel = new YouApplyNotificationModel();
                                        youApplyNotificationModel.setAction_id(UtilityPro.getDataFromJsonInt(object, "action_id"));
                                        youApplyNotificationModel.setEvent_id(UtilityPro.getDataFromJsonInt(object, "event_id"));
                                        youApplyNotificationModel.setEvent_name(UtilityPro.getDataFromJson(object, "event_name"));
                                        youApplyNotificationModel.setUser_image(UtilityPro.getDataFromJson(object, "user_image"));
                                        youApplyNotificationModel.setUser_id(UtilityPro.getDataFromJsonInt(object, "user_id"));
                                        youApplyNotificationModel.setUser_name(UtilityPro.getDataFromJson(object, "user_name"));
                                        youApplyNotificationModel.setType(UtilityPro.getDataFromJson(object, "type"));
                                        youApplyNotificationModel.setEvent_image(UtilityPro.getDataFromJson(object, "event_image"));
                                        youApplyNotificationModel.setIs_online(UtilityPro.getDataFromJsonBoolean(object, "is_online"));
                                        youApplyNotificationModel.setMessages(UtilityPro.getDataFromJson(object, "and_msg"));
                                        youApplyNotificationModels.add(youApplyNotificationModel);
                                    }

                                    if (listYourNoitification.getAdapter() == null) {
                                        listYourNoitification.setAdapter(youApplyNotificationAdapter);
                                        listYourNoitification.setHasMoreItems(true);
                                        listYourNoitification.setPagingableListener(pm);
                                    }
                                    if (responce_obj.getBoolean("next") == true) {
                                        is_page = true;
                                        pager = pager + 1;
                                        listYourNoitification.onFinishLoading(true, youApplyNotificationModels);

                                    } else {
                                        is_page = false;
                                        listYourNoitification.setHasMoreItems(false);
                                        listYourNoitification.onFinishLoading(false, youApplyNotificationModels);
                                    }
                                    youApplyNotificationAdapter.notifyDataSetChanged();


//                                    try {
//                                        NotificationFragment nn = new NotificationFragment();
//                                        nn.setTextNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));
//
//                                    } catch (Exception e) {
//
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

    PagingListView.Pagingable pm = new PagingListView.Pagingable() {
        @Override
        public void onLoadMoreItems() {
            if (pager != 0) {
                Map<String, String> paramss = new HashMap<String, String>();
                paramss.put("user_id", Pref.getUserID());
                paramss.put("type", "0");
                paramss.put("time_zone", UtilityPro.getTimezone());
                paramss.put("next", String.valueOf(pager));
                getYourEventNotification(paramss);
            }
        }
    };

    @Override
    public void onDetailsListener(ArrayList<YouApplyNotificationModel> mList, int pos) {
        ((MainActivity) getActivity()).showFragment(EventMoreDetailsFragment.newInstance(String.valueOf(mList.get(pos).getEvent_id())));
    }

    public interface OnYourNotificationCount {
        public void onYourNotificationCount(int count);
    }
}