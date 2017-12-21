/**
 * @author saltinteractive
 */
package com.dr.app;

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

import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;


import me.leolin.shortcutbadger.ShortcutBadger;

import static com.dr.app.utility.Golly.context;


public class Splacescreen extends Activity {

    private ImageView img_spinner;
    IntentFilter filter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.spalce_bg, getBaseContext().getTheme()));
//        } else {
//            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.spalce_bg));
//        }
        setContentView(R.layout.activity_splacescreen);
        ShortcutBadger.removeCount(context);
        initView();

    }


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        final Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            System.out.println("====extras==" + extras.getString("event_id"));
//            if (Pref.getIsLogin()) {
//                Intent serviceIntent = new Intent(getBaseContext(),
//                        XMPPService.class);
//                if (ChatComman.isMyServiceRunning(XMPPService.class, this))
//                    stopService(serviceIntent);
//                startService(serviceIntent);
//                Intent intent1 = new Intent(this, MainActivity.class);
//                intent1.putExtra("type", extras.getString("type"));
//                intent1.putExtra("event_id", extras.getString("event_id"));
//                startActivity(intent1);
//                finish();
//            }
//        }
//    }

    public void initView() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
        handler.sendMessageDelayed(handler.obtainMessage(), 3000);
    }


    private void prepareNotification(Context context, String content_id, String title, String msg, String content_type) {

        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Long when = System.currentTimeMillis();
        Notification notification;
        /*notification = new Notification(R.drawable.ic_launcher, msg, when);*/
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Intent resultIntent = new Intent(context, DashboardActivity.class);
        switch (content_type) {
            case "video":
                resultIntent.putExtra("content_type", content_type);
                resultIntent.putExtra("content_id", "" + content_id);
                break;
            case "image":
                break;
        }
        //intent.setData(Uri.parse(msg));
        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
//        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, when.intValue(), resultIntent, 0);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(UtilityPro.isMaterialAnimationSupported() ? R.drawable.ic_launcher : R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(title)
                .setSubText(msg)
                .setContentIntent(pendingIntent);
        notification = builder.getNotification();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        manager.notify(Calendar.getInstance().get(Calendar.MILLISECOND), notification);
        try {
            manager.notify(Integer.parseInt(content_id), notification);
        } catch (Exception e) {

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (Pref.getIsLogin()) {

                startActivity(new Intent(Splacescreen.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(Splacescreen.this, LoginActivity.class));
                finish();
            }

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
