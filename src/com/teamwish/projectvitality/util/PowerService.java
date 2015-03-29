package com.teamwish.projectvitality.util;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.*;
import android.util.Log;

/**
 * Created by kuzalj on 3/28/2015.
 */
public class PowerService extends Service {

    @Override
    public void onCreate() {
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        registerReceiver(powerReciever, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(powerReciever, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 1;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(batteryReceiver);
    }

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive( Context context, Intent intent )
        {
            int level = intent.getIntExtra("level", 0);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;


            if(level >= Constants.MAX_CHARGE_LEVEL && isCharging){

               new NetworkUtil().setCustomState("off");
                new NetworkUtil().setLightState("gr");
            }

        }
    };

    private BroadcastReceiver powerReciever = new BroadcastReceiver() {
        @Override
        public void onReceive( Context context, Intent intent )
        {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            if(isCharging){
                //new NetworkUtil().setLightState("red");
                //new NetworkUtil().setCustomState("on");
            }

        }
    };
}
