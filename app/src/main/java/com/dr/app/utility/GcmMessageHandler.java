///**
// * @author saltinteractive
// */
//package com.dr.app.utility;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.dr.app.DashboardActivity;
//import com.dr.app.R;
//
//public class GcmMessageHandler extends GcmListenerService {
//    public static final int MESSAGE_NOTIFICATION_ID = 435345;
//
//    @Override
//    public void onMessageReceived(String from, Bundle data) {
//
//        if (data != null) {
////            createNotification(from, data);
//            System.out.println("===========data  notification=========" + data.toString());
////            String content_type = data.getString("content_type");
//            try {
//                prepareNotification(Golly.context, data.getString("content_id"), data.getString("title"), data.getString("text"), data.getString("content_type"), data);
//            } catch (Exception e) {
//
//            }
//        }
//    }
//
//    private void prepareNotification(Context context, String content_id, String title, String msg, String content_type, Bundle intent_bundle) {
//
//        NotificationManager manager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Long when = System.currentTimeMillis();
//        Notification notification;
//        /*notification = new Notification(R.drawable.ic_launcher, msg, when);*/
////        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        Intent local = new Intent();
//        Intent video_type = new Intent();
//        Intent resultIntent = new Intent(context, DashboardActivity.class);
//        switch (content_type) {
//            case "":
//                resultIntent.putExtra("msg", "" + title);
//                resultIntent.putExtra("text", "" + msg);
//                resultIntent.putExtra("content_type", content_type);
//                resultIntent.putExtra("content_id", "" + content_id);
//                PendingIntent pendingIntent1 = PendingIntent.getActivity(context, when.intValue(), resultIntent, 0);
//
//                Notification.Builder builder1 = new Notification.Builder(context);
//                builder1.setSmallIcon(UtilityPro.isMaterialAnimationSupported() ? R.drawable.ic_launcher : R.mipmap.ic_launcher)
//                        .setContentTitle(getString(R.string.app_name))
//                        .setContentText(title)
//                        .setSubText(msg)
////                        .setContentTitle(getString(R.string.app_name))
////                        .setContentText(title)
//                        .setContentIntent(pendingIntent1);
//                notification = builder1.getNotification();
//                notification.flags |= Notification.FLAG_AUTO_CANCEL;
//                try {
//                        manager.notify(when.intValue(), notification);
//                } catch (Exception e) {
//
//                }
//                break;
//            case "other":
//                local.putExtra("msg", "" + title);
//                local.putExtra("text", "" + msg);
//                local.putExtra("content_type", content_type);
//                local.setAction("com.parrisdance.action");
//
//                resultIntent.putExtra("content_type", content_type);
//                resultIntent.putExtra("msg", "" + title);
//                resultIntent.putExtra("text", "" + msg);
//                PendingIntent pendingIntent = PendingIntent.getActivity(context, when.intValue(), resultIntent, 0);
//
//                Notification.Builder builder = new Notification.Builder(context);
//                builder.setSmallIcon(UtilityPro.isMaterialAnimationSupported() ? R.drawable.ic_launcher : R.mipmap.ic_launcher)
//                        .setContentTitle(getString(R.string.app_name))
//                        .setContentText(title)
//                        .setSubText(msg)
//                        .setContentIntent(pendingIntent);
//                notification = builder.getNotification();
//                notification.flags |= Notification.FLAG_AUTO_CANCEL;
//                try {
//                    manager.notify(when.intValue(), notification);
//                } catch (Exception e) {
//                }
//                break;
////                }
//        }
//
//    }
//}
//
//
////    protected void displayNotification(Bundle data) {
////        Log.i("Start", "notification");
////        Intent resultIntent = new Intent(this, DashBoard.class);
////        String content_type = data.getString("content_type");
////        String content_title = data.getString("message");
////        String content_id = data.getString("content_id");
////
////        System.out.println("=============content_title==========" + content_title);
////
////   /* Invoking the default notification service */
////        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
////        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
////        mBuilder.setAutoCancel(true);
////        mBuilder.setContentText(content_title);
////
////
////   /* Increase notification number every time a new notification arrives */
////
////   /* Add Big View Specific Configuration */
////        // NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//
////        // Sets a title for the Inbox style big view
//////        inboxStyle.setBigContentTitle(content_title);
////        // inboxStyle.setSummaryText(content_title);
////        // Moves events into the big view
//////        for (int i=0; i < events.length; i++) {
//////            inboxStyle.addLine(events[i]);
//////        }
////
////        // mBuilder.setStyle(inboxStyle);
////
////   /* Creates an explicit intent for an Activity in your app */
////
////
////        resultIntent.putExtra("content_type", content_type);
////        resultIntent.putExtra("content_id", content_id);
////        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
////        stackBuilder.addParentStack(DashBoard.class);
////
////   /* Adds the Intent that starts the Activity to the top of the stack */
////        stackBuilder.addNextIntent(resultIntent);
////
////        int pos = 222;
////        try {
////            pos = Integer.parseInt(content_id);
////        } catch (Exception e) {
////            pos = 452;
////        }
////        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(pos, PendingIntent.FLAG_UPDATE_CURRENT);
////        mBuilder.setContentIntent(resultPendingIntent);
////        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////
////
////   /* notificationID allows you to update the notification later on. */
////        mNotificationManager.notify(Integer.parseInt(content_id), mBuilder.build());
////    }
//
//
//
//
//
//
//
//
