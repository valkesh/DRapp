

package com.dr.app.fragment;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dr.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.dr.app.utility.Golly.notificaiton_count;

public class NotificationFragment extends Fragment implements View.OnClickListener, TabLayout.OnTabSelectedListener, FragmentYourEventNotification.OnnotificationCount , FragmentYouNotification.OnYourNotificationCount {
    private static final String ARG_ITEM = "item";
    public static String event_id = "";
    public static boolean type = false;
    ViewPager viewPager;
    TabLayout tabLayout;
    public static int openTab = 0;
    public static String typeOne = "";
    public static FragmentYourEventNotification.OnnotificationCount onNotificationValue;
    public static FragmentYouNotification.OnYourNotificationCount onYourNotificationCount;

    public NotificationFragment() {
    }


    public static NotificationFragment newInstance(String item, boolean typeof, String event_id_, String typeOnen) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        event_id = event_id_;
        type = typeof;
        typeOne = typeOnen;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        item = getArguments().getString(ARG_ITEM);
        if (typeOne.equals("apply") || typeOne.equals("withdraw")) {
            openTab = 1;
        } else {
            openTab = 0;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onNotificationValue = this;
        onYourNotificationCount = this;
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        createTabIcons();
        if (openTab == 0) {
            setTextNotification(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgEventImage:
                break;
        }
    }

    FrameLayout frameBadge, frameBadgetwo;
    public static TextView badge,badgetwo;

    private void createTabIcons() {
        RelativeLayout ll1 = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tablayout_customview, null);
        TextView tabOne1 = (TextView) ll1.findViewById(R.id.tab);
        badge = (TextView) ll1.findViewById(R.id.imgTab);
        frameBadge = (FrameLayout) ll1.findViewById(R.id.frameBadge);
        tabOne1.setText("YOU   ");
        tabOne1.setTextColor(Color.parseColor("#000000"));
        tabLayout.getTabAt(0).setCustomView(ll1);

        RelativeLayout ll2 = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tablayout_customview_two, null);
        TextView tabOne2 = (TextView) ll2.findViewById(R.id.tab);
        badgetwo = (TextView) ll2.findViewById(R.id.imgTabtwo);
        frameBadgetwo = (FrameLayout) ll2.findViewById(R.id.frameBadgetwo);

        tabOne2.setText("YOUR EVENT  ");
        tabOne2.setTextColor(Color.parseColor("#32000000"));
        tabLayout.getTabAt(1).setCustomView(ll2);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentYouNotification().newInstance("", type, event_id, onYourNotificationCount), "You");
        adapter.addFragment(new FragmentYourEventNotification().newInstance("", type, event_id, onNotificationValue), "Your Event");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(openTab);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            setTextNotification(0);
        }
        if (tab.getPosition() == 1) {
            setTextNotificationYou(0);
        }

        TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab);
        RelativeLayout rlView = (RelativeLayout) tab.getCustomView().findViewById(R.id.tablayout);
//      rlView.setBackgroundResource(R.drawable.tab_selected);
        textView.setTextColor(Color.parseColor("#000000"));
        System.out.println("tab........" + tab);
        Map<String, String> paramss = new HashMap<String, String>();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab);
        RelativeLayout rlView = (RelativeLayout) tab.getCustomView().findViewById(R.id.tablayout);
//        rlView.setBackgroundResource(R.drawable.status_bar);
        textView.setTextColor(Color.parseColor("#32000000"));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onNotificationCount(int count) {
        setTextNotification(count);
    }

    @Override
    public void onYourNotificationCount(int count) {
        setTextNotificationYou(count);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void setTextNotification(int notification_count) {
        if (notification_count == 0 || notification_count == -1) {
            frameBadge.setVisibility(View.GONE);
        } else {
            badge.setText(String.valueOf(notification_count));
            frameBadge.setVisibility(View.VISIBLE);
        }
    }

    public void setTextNotificationYou(int notification_count) {
        if (notification_count == 0 || notification_count == -1) {
            frameBadgetwo.setVisibility(View.GONE);
        } else {
            badgetwo.setText(String.valueOf(notification_count));
            frameBadgetwo.setVisibility(View.VISIBLE);
        }
    }

    public void snackbarToast() {
//        Snackbar snack = Snackbar.make(lnSnackbar_action, "valkesh patel", Snackbar.LENGTH_LONG);
//
//        View view = snack.getView();
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params);
//        snack.show();
    }



}