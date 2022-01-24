package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.ballotblock.R;

public class AirplaneModeMainActivity extends AppCompatActivity {
    //    jisko bhejna uska object - referencing down
    AirplaneModeReceiver obj = new AirplaneModeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplane_mode_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        yahan pe uska naam dena jisko bhejna
//        intent filter ke andar pata chalge ke konsa kaam hy
        registerReceiver(obj, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(obj);
    }
}