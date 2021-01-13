package com.example.myalarmapp;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

public class RingtoneService extends Service {

    static MediaPlayer mediaPlayer;
    private boolean isRunning;

    private int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String command = intent.getExtras().getString("command");
        Log.e("command in service", command);


        assert command != null;
        switch (command) {
            case "turnOffAlarm":
                startId = 0;
                break;
            case "setAlarm":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");
            mediaPlayer = MediaPlayer.create(this,
                    Settings.System.DEFAULT_RINGTONE_URI);
            mediaPlayer.start();
            this.isRunning = true;
            this.startId = 0;
        }
        else if (!this.isRunning && startId == 0){
            Log.e("if there was not sound ", " and you want end");
            this.isRunning = false;
            this.startId = 0;
        }
        else if (this.isRunning && startId == 1){
            Log.e("if there is sound ", " and you want start");
            this.isRunning = true;
            this.startId = 0;
        }
        else {
            Log.e("if there is sound ", " and you want end");
            mediaPlayer.stop();
            mediaPlayer.reset();
            this.isRunning = false;
            this.startId = 0;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.isRunning = false;

        mediaPlayer.stop();
        mediaPlayer.reset();
    }
}
