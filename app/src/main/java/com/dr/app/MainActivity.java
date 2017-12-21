/*
 * Copyright (C) 2016 JetRadar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dr.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dr.app.backstackinsta.BackStackActivity;
import com.dr.app.fragment.CreateEventFragment;
import com.dr.app.fragment.HomeDemoFragment;
import com.dr.app.fragment.IsNotLoginProfileFragment;
import com.dr.app.fragment.NotificationFragment;
import com.dr.app.fragment.ProfileFragment;
import com.dr.app.fragment.RecentChatFragment;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;


import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

import static com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener;

public class MainActivity extends BackStackActivity implements OnTabSelectedListener {
    private static final String STATE_CURRENT_TAB_ID = "current_tab_id";
    private static final int MAIN_TAB_ID = 0;
    public static MainActivity mMainActivity;
    private BottomNavigationBar bottomNavBar;
    private Fragment curFragment;
    private int curTabId;
    int lastSelectedPosition = 0;
    Golly.MyNotificationListener myNotificationListener;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        setUpBottomNavBar();
        mMainActivity = this;
        // myNotificationListener = new MainActivity();
        if (state == null) {
            bottomNavBar.selectTab(MAIN_TAB_ID, false);
            showFragment(rootTabFragment(MAIN_TAB_ID));
        }
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("event_id") != null) {
                onNewIntent(getIntent());
            }
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));


        //  setUnreadChatCount();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            System.out.println("===extras.getString(event_id)==" + extras.getString("event_id"));
            if (extras.getString("event_id") != null) {
                extras.putString("event_id", null);
                showFragment(NotificationFragment.newInstance(getString(R.string.notification), true, extras.getString("event_id"), extras.getString("type")), false);
//            NotificationFragment.newInstance(getString(R.string.notification), true, extras.getString("event_id"));
            }
        }
    }


    BadgeItem badgeItem = new BadgeItem();
    BadgeItem badgeItemChat = new BadgeItem();
    BottomNavigationItem bottomNavigationItem;
    BottomNavigationItem bottomNavigationItemChat;

    private void setUpBottomNavBar() {
        String userprofile_option = getString(R.string.userprofile);
        if (Pref.getIsLogin()) {
            userprofile_option = getString(R.string.userprofile);
        } else {
            userprofile_option = getString(R.string.isnotuserprofile);
        }

        bottomNavigationItem = new BottomNavigationItem(R.drawable.ic_notification_on, R.string.notification).setInactiveIconResource(R.drawable.ic_notification_off);
        bottomNavigationItemChat = new BottomNavigationItem(R.drawable.ic_message_on, R.string.chat).setInactiveIconResource(R.drawable.ic_message_off);

        bottomNavBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation);
        bottomNavBar
                .setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavBar.setBackgroundResource(R.drawable.bg_bottom);
        bottomNavBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_on, R.string.home).setInactiveIconResource(R.drawable.ic_home_off))
                .addItem(bottomNavigationItemChat.setBadgeItem(badgeItemChat.hide()))
                .addItem(new BottomNavigationItem(R.drawable.add, R.string.post).setInactiveIconResource(R.drawable.add))
                .addItem(bottomNavigationItem.setBadgeItem(badgeItem.hide()))
                .addItem(new BottomNavigationItem(R.drawable.ic_user_on, userprofile_option).setInactiveIconResource(R.drawable.ic_user_off))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        bottomNavBar.setTabSelectedListener(this);
    }

    @NonNull
    private Fragment rootTabFragment(int tabId) {
        switch (tabId) {
            case 0:
                return HomeDemoFragment.newInstance(getString(R.string.home));
            case 1:
                if (Pref.getIsLogin()) {
                    return RecentChatFragment.newInstance(getString(R.string.recent_chat));
                } else {
                    return IsNotLoginProfileFragment.newInstance(getString(R.string.isnotuserprofile));
                }
            case 2:
                if (Pref.getIsLogin()) {
                    return CreateEventFragment.newInstance(getString(R.string.post));
                } else {
                    return IsNotLoginProfileFragment.newInstance(getString(R.string.isnotuserprofile));
                }
            case 3:

                if (Pref.getIsLogin()) {
                    return NotificationFragment.newInstance(getString(R.string.notification), false, "", "other");
                } else {
                    return IsNotLoginProfileFragment.newInstance(getString(R.string.isnotuserprofile));
                }

            case 4:
                //return ProfileFragment.newInstance(getString(R.string.userprofile));
                if (Pref.getIsLogin()) {
                    return ProfileFragment.newInstance(Pref.getUserID());
                } else {
                    return IsNotLoginProfileFragment.newInstance(getString(R.string.isnotuserprofile));
                }

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        curFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        curTabId = savedInstanceState.getInt(STATE_CURRENT_TAB_ID);
        bottomNavBar.selectTab(curTabId, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CURRENT_TAB_ID, curTabId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        Pair<Integer, Fragment> pair = popFragmentFromBackStack();
        if (pair != null) {
            backTo(pair.first, pair.second);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        if (curFragment != null) {
            pushFragmentToBackStack(curTabId, curFragment);
        }
        curTabId = position;
        Fragment fragment = popFragmentFromBackStack(curTabId);
        if (fragment == null) {
            fragment = rootTabFragment(curTabId);
        }
        replaceFragment(fragment);
    }

    @Override
    public void onTabReselected(int position) {
        System.out.println("========curTabId========" + position);
        if (position == 0) {
            bottomNavBar.selectTab(MAIN_TAB_ID, false);
            showFragment(rootTabFragment(MAIN_TAB_ID));
        } else {
            backToRoot();
        }
    }

    @Override
    public void onTabUnselected(int position) {
    }

    public void showFragment(@NonNull Fragment fragment) {
        showFragment(fragment, true);
    }

    public void showFragment(@NonNull Fragment fragment, boolean addToBackStack) {
        if (curFragment != null && addToBackStack) {
            pushFragmentToBackStack(curTabId, curFragment);
        }
        replaceFragment(fragment);
    }


    private void backTo(int tabId, @NonNull Fragment fragment) {
        if (tabId != curTabId) {
            curTabId = tabId;
            bottomNavBar.selectTab(curTabId, false);
        }
        replaceFragment(fragment);
        getSupportFragmentManager().executePendingTransactions();
    }

    private void backToRoot() {
        if (isRootTabFragment(curFragment, curTabId)) {
            return;
        }
        resetBackStackToRoot(curTabId);
        Fragment rootFragment = popFragmentFromBackStack(curTabId);
        assert rootFragment != null;
        backTo(curTabId, rootFragment);
    }

    public void updateNotification(int value) {
        if (value == 0) {
            bottomNavigationItem.setBadgeItem(badgeItem.hide());
        }
        if (value == -1) {
            bottomNavigationItem.setBadgeItem(badgeItem.hide());
        } else {
            bottomNavigationItem.setBadgeItem(badgeItem.show().setText(String.valueOf(value)));
        }
    }

    private boolean isRootTabFragment(@NonNull Fragment fragment, int tabId) {
        return fragment.getClass() == rootTabFragment(tabId).getClass();
    }

    private void replaceFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.content, fragment);
        tr.commitAllowingStateLoss();
        curFragment = fragment;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        curFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        curFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            updateNotification(Integer.parseInt(message));
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}