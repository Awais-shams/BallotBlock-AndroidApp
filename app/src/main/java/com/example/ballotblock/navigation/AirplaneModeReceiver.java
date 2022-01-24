package com.example.ballotblock.navigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class AirplaneModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isAirplaneModeOn(context.getApplicationContext())) {
            Toast.makeText(context, "Airplane Mode is ON", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Airplane Mode is OFF", Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isAirplaneModeOn(Context context) {
//        ye method return krega ke mode change hua ke nai - by looking into system settings
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

}
