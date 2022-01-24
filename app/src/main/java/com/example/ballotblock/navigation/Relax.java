package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.ballotblock.R;

public class Relax extends AppCompatActivity {
    Toolbar toolbar;

    MyBoundService myBoundMediaPlayerService;
    public boolean myBoundService = false;
    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBoundService.MyBinder myBinder = (MyBoundService.MyBinder) service;
            myBoundMediaPlayerService = myBinder.getServiceMethod();
            myBoundService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
//        for back button, we can also customize back btn - to customize see link bookmarked
        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyBoundService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myBoundService == true) {
            unbindService(serviceConnection);
        }
    }

    public void Play(View view) {
//        check connection
        if (myBoundService == true) {
//            check if playing
            if (myBoundMediaPlayerService.isPlay()) {
                myBoundMediaPlayerService.Pause();
            }
            else {
                myBoundMediaPlayerService.Play();
            }
        }
    }

    public void Stop(View view) {
//        check connection
        if (myBoundService == true) {
            myBoundMediaPlayerService.Stop();
        }
    }
}