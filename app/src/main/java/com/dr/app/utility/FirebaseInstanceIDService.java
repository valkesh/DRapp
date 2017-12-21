package com.dr.app.utility;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by valkeshpatel on 13/10/16.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Pref.setDeviceToken(Constants.DEVICE_TOKEN_GOOGLE, token);
    }
}