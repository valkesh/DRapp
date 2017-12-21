/**
 * @author Valkesh patel
 */
package com.dr.app;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify of changes
        Intent intent = new Intent(this, com.dr.app.RegistrationIntentService.class);
        startService(intent);
    }
}