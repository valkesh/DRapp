package com.dr.app.utility;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.fragment.FragmentYouNotification;
import com.dr.app.fragment.FragmentYourEventNotification;
import com.dr.app.model.YouEventNotificationModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.dr.app.utility.Golly.context;
import static com.dr.app.utility.Golly.notificaiton_count;

/**
 * Created by valkeshpatel on 13/10/16.
 */

public class MyFireBaseNotificationService extends FirebaseMessagingService {





    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        System.out.println("======get notifiaction=== youinevent_count"+ remoteMessage.getData().get("youinevent_count"));
        System.out.println("======get notifiaction youinevent_count==="+ remoteMessage.getData().get("youinevent_count"));
        System.out.println("======get notifiactio==="+ remoteMessage.getData().toString());

        int badgeCount = Integer.parseInt(remoteMessage.getData().get("total_count"));

        int badgeCountChat = 0;
        if(badgeCountChat == -1){
        }else{
            badgeCount = badgeCountChat + badgeCount;
        }
        notificaiton_count = badgeCount;
        ShortcutBadger.applyCount(context, badgeCount);
        sendNotification(getApplicationContext().getString(R.string.app_name_label), remoteMessage);

        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", ""+ badgeCount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendNotification(String messageTitle, RemoteMessage remoteMessage) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("event_id", remoteMessage.getData().get("event_id").toString());
        intent.putExtra("type", remoteMessage.getData().get("type").toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500, 500, 500, 500, 500};

//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        //  {total_count=1, game_id=388, user_id=83, type=apply, event_id=540, event_image=http://opensource.zealousys.com/player_vacancy/wp-content/uploads/2016/10/Anti-virus-Android-Gratis-Terbaik-12-150x150.jpg, event_title=Diwali Mela, message=Hardik Kansara applied for join Diwali Mela, noti_type=0}


        // System.out.println("===remoteMessage.getData().get(\"event_title\").toString()======="+ remoteMessage.getData().get("event_title").toString());
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(Utility.isMaterialAnimationSupported() ? R.drawable.ic_notification : R.drawable.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(remoteMessage.getData().get("message").toString())
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setAutoCancel(true)
                .setLights(Color.BLUE, 1, 1)
                .setContentIntent(pendingIntent);
        Long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(when.intValue() /* ID of notification */, notificationBuilder.build());
    }

}

