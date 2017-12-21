/**
 * @author saltinteractive
 */
package com.dr.app.appointment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.dr.app.DashboardActivity;
import com.dr.app.LoginActivity;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.RegistrationIntentService;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;



import me.leolin.shortcutbadger.ShortcutBadger;

import static com.dr.app.utility.Golly.context;


public class WelcomeScreen extends Activity {



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.spalce_bg, getBaseContext().getTheme()));
//        } else {
//            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.spalce_bg));
//        }
        setContentView(R.layout.activity_welcomescreen);
        ShortcutBadger.removeCount(context);
        initView();

    }

    public void initView() {
//        Intent intent = new Intent(this, RegistrationIntentService.class);
//        startService(intent);
        handler.sendMessageDelayed(handler.obtainMessage(), 3000);
    }




    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                startActivity(new Intent(WelcomeScreen.this, MainActivityDR.class));
                finish();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
