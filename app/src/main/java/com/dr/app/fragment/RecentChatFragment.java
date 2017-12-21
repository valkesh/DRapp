

package com.dr.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.dr.app.R;
import com.dr.app.adapter.RecentChatAdapter;
import com.dr.app.model.ChatMessageNotification;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RecentChatFragment extends Fragment implements View.OnClickListener, RecentChatAdapter.OnDetailsItem {
    private static final String ARG_ITEM = "item";
    public ListView listRecentChat;
    public RecentChatAdapter recentChatAdapter;
    public ArrayList<ChatMessageNotification> chatMessageNotifications = new ArrayList<ChatMessageNotification>();
    RecentChatAdapter.OnDetailsItem getDetails;
//    ChatDatabase db;

    boolean isRunning = false;

    public RecentChatFragment() {
    }

    public static RecentChatFragment newInstance(String item) {
        RecentChatFragment fragment = new RecentChatFragment();
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
//        db = new ChatDatabase(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent_chat, container, false);
    }

    View views;
    EditText edtSearchBar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDetails = this;
        views = view;
        listRecentChat = (ListView) views.findViewById(R.id.listRecentChat);

        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(getActivity());
        asyncTaskRunner.execute("");

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                //getActivity().onBackPressed();
                //((MainActivity) getActivity()).showFragment(EditProfileFragment.newInstance(getString(R.string.editprofile)));
                break;
            case R.id.lnSearchFilter:
//                AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(String.valueOf(latitude), String.valueOf(longitude), MapResultsActivity.this);
//                asyncTaskRunner.execute("");
                break;
        }
    }

    @Override
    public void onDetailsListener(ArrayList<ChatMessageNotification> mList, int pos) {
        Pref.setOtherUserImage(mList.get(pos).getChatMessages().getOtherUserImage());
//        startActivity(new Intent(getActivity(), FinalChatActivity.class).putExtra("FROM_MESSGE", mList.get(pos).getChatMessages().getRecv_id())
//                .putExtra("titlename", mList.get(pos).getChatMessages().getName())
//                .putExtra("type", "0")
//                .putExtra("other_image", mList.get(pos).getChatMessages().getOtherUserImage()));
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        Context con;
//        ChatDatabase db;

        public AsyncTaskRunner(Activity con) {
            this.con = con;
//            db = new ChatDatabase(con);
        }

        @Override
        protected String doInBackground(String... params) {
//            if (chatMessageNotifications.size() != 0) {
//                chatMessageNotifications.clear();
//            }

//            chatMessageNotifications = db.GetConversationMessage(Constants.App_Name + Pref.getUserID(), "");
//            Collections.reverse(chatMessageNotifications);
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            // Golly.hideDarkProgressDialog();
//            recentChatAdapter = new RecentChatAdapter(getActivity(), getDetails, chatMessageNotifications);
//            listRecentChat.setAdapter(recentChatAdapter);
          //  recentChatAdapter.notifyDataSetChanged();

            edtSearchBar = (EditText) views.findViewById(R.id.edtSearchBar);

//            for(int i=0; i<chatMessageNotifications.size(); i++){
//
//                try {
//                    System.out.println("====Status: "+XMPPService.getStatus(chatMessageNotifications.get(i).getChatMessages().getReceiverName()));
//                } catch (SmackException.NotConnectedException e) {
//                    e.printStackTrace();
//                }
//            }

            edtSearchBar.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    System.out.println("Text [" + s + "]");
                    recentChatAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            String chat_user_id = "";
            for (int i = 0; i < chatMessageNotifications.size(); i++) {
                chat_user_id = chatMessageNotifications.get(i).getChatMessages().getRecv_id() + "," + chat_user_id;
            }
            if (chat_user_id.length() != 0) {
                chat_user_id.split(",", chat_user_id.length() - 1);
            }
            if (chat_user_id.length() != 0) {
//                Call webservice
                Map<String, String> paramss = new HashMap<String, String>();
                paramss.put("user_id", Pref.getUserID());
                paramss.put("chat_user_id", chat_user_id);
                setUserState(paramss, chatMessageNotifications);
            }
        }

        @Override
        protected void onPreExecute() {
            isRunning = true;
            // Golly.showDarkProgressDialogAct(con);
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }


    public void setUserState(Map<String, String> params, final ArrayList<ChatMessageNotification> chatMessageNotifications) {
        String Url = Constants.USER_STATUS;
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET STATUS FOR CHAT USER_STATUS",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    System.out.println("====responce_obj======" + responce_obj);
                                    JSONArray event_array = responce_obj.getJSONArray("results");
                                    for (int event = 0; event < event_array.length(); event++) {
                                        JSONObject object = event_array.getJSONObject(event);
                                        boolean status = object.getBoolean("isonline");
                                        System.out.println("=====object.getString(status_image)====="+ object.getString("status_image"));
                                        chatMessageNotifications.get(event).getChatMessages().setUser_status(status);
                                    }
                                    recentChatAdapter = new RecentChatAdapter(getActivity(), getDetails, chatMessageNotifications);
                                    listRecentChat.setAdapter(recentChatAdapter);
//                                    recentChatAdapter.notifyDataSetChanged();
                                } else {
                                    if (!UtilityPro.getDataFromJson(responce_obj, "message").equalsIgnoreCase("")) {
                                        //    UtilityPro.toast(UtilityPro.getDataFromJson(responce_obj, "message"));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isRunning = false;
                    }

                    @Override
                    public void errorResponce() {
                        isRunning = false;
                    }
                });
    }

    public void updateMessages(final Activity act) {
        if (!isRunning) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(act);
                    asyncTaskRunner.execute("");

                }
            }, 500);
        }


    }


}




