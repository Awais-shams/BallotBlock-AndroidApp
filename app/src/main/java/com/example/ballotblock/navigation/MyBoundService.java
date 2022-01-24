package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.example.ballotblock.R;

import java.security.Provider;

public class MyBoundService extends Service {

    public Binder myBinder = new MyBinder();
    public MediaPlayer mediaPlayer;

    public MyBoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.mp3_file);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    public void Play() {
        mediaPlayer.start();
    }

    public void Pause() {
        mediaPlayer.pause();
    }

    public void Stop() {
        mediaPlayer.stop();
    }

    public boolean isPlay() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        MyBoundService getServiceMethod() {
            return MyBoundService.this;
        }
    }

}