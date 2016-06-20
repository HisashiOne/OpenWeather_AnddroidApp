package com.example.macmini_desarrollo2.weatherandroidapp;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
/**
 * Created by Oswaldo Morales on 07/06/16.
 *
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //  Update Widget in Alarm Manager
        Intent intent1 = new Intent(context, WeatherWidget.class);
        intent1.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids [] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,  WeatherWidget.class));
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent1);

    }
}
