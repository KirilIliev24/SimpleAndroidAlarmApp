package com.example.myalarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("in the receiver", "yes");

        String getCommand = intent.getExtras().getString("command");
        Log.e("command", getCommand);

        Intent ringtoneIntent = new Intent(context, RingtoneService.class);
        ringtoneIntent.putExtra("command", getCommand);

        context.startService(ringtoneIntent);
    }
}
