/**
 * @author saltinteractive
 */
package com.dr.app;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


import com.dr.app.utility.Constants;
import com.dr.app.utility.Pref;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;


import java.io.IOException;

public class RegistrationIntentService extends IntentService {

    // abbreviated tag name
    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Make a call to Instance API
        InstanceID instanceID = InstanceID.getInstance(this);
        String senderId = getResources().getString(R.string.gcm_defaultSenderId);

        try {
            // request token that will be used by the server to send push notifications
            String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.d(TAG, "GCM Registration Token: " + token);
            if (token != null) {
//                String sub[] = token.split(":");
                sendRegistrationToServer(token);
//         sendRegistrationToServer(sub[1]);
            }
            // pass along this data

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        if (Pref.getDeviceToken(Constants.DEVICE_TOKEN_GOOGLE).toString().length() == 0) {
            Pref.setDeviceToken(Constants.DEVICE_TOKEN_GOOGLE, token);
        }
    }

//    private void subscribeTopics(String token) throws IOException {
//        GcmPubSub pubSub = GcmPubSub.getInstance(this);
//        for (String topic : TOPICS) {
//            pubSub.subscribe(token, "/topics/" + topic, null);
//        }
//    }
}