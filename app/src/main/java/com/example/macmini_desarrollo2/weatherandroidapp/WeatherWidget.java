package com.example.macmini_desarrollo2.weatherandroidapp;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.widget.RemoteViews;

import com.example.macmini_desarrollo2.weatherandroidapp.Client.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;


/**
 * Created by Oswaldo Morales on 07/06/16.
 *
 */


public class WeatherWidget extends AppWidgetProvider implements LocationListener {

    Boolean startLocation;
    Context context;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //update Widget
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        // Create a Alarm Manager every 12 Hours

        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,  234324243, intent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 6 * 60 * 60 * 1000, 6 * 60 * 60 * 1000, pendingIntent);

        reloadWidget(context);
    }


    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        //Start Alarm Repeating on create widget
        Intent intent_2 = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent_2 = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent_2, 0);
        AlarmManager alarmManager_2 = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager_2.cancel(pendingIntent_2);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

        // Stop Alar Repeating
        Intent intent_2 = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent_2 = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent_2, 0);
        AlarmManager alarmManager_2 = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager_2.cancel(pendingIntent_2);
    }


    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        this.context = context;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);


        reloadWidget(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        //Start Alarm Manager if Widget is Activate
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,  234324243, intent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 6 * 60 * 60 * 1000, 6 * 60 * 60 * 1000, pendingIntent);


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }

    @Override
    public void onLocationChanged(Location location) {

        //Load Location from Network
        if (startLocation){
            RestClient client = new RestClient();
            client.getWeather(String.valueOf( location.getLatitude()), String.valueOf(location.getLongitude()), null, handler);
            startLocation = false;
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    //Main Handler
    private JsonHttpResponseHandler handler = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            try {


                //Get City and Name and send to List
                JSONObject city = response.getJSONObject("city");
                String name = city.getString("name");

                JSONArray list = response.getJSONArray("list");
                JSONModel model = new JSONModel();
                model.arrayList(list);

                String dayTmpString = model.tempDay.get(0) + "º";
                String iconWeather = model.iconWeather.get(0);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
                ComponentName thisWidget = new ComponentName(context, WeatherWidget.class);
                remoteViews.setTextViewText(R.id.cityWidget, name);
                remoteViews.setTextViewText(R.id.tempWidget, dayTmpString);
                remoteViews.setTextViewText(R.id.DescripWidget, model.descripWeather.get(0));


                Calendar calendar = Calendar.getInstance();
                int HR24 = calendar.get(Calendar.HOUR_OF_DAY);

                //Check Hour of the day if is night change the colors
                if (HR24 >= 20){



                    remoteViews.setInt(R.id.mainContainerWidget, "setBackgroundResource", R.drawable.purple_cicle_gradient);
                    remoteViews.setInt(R.id.cicle_2, "setBackgroundResource", R.drawable.purple_cicle_gradient);


                }else {


                    remoteViews.setInt(R.id.mainContainerWidget, "setBackgroundResource", R.drawable.orange_circle_gradient);
                    remoteViews.setInt(R.id.cicle_2, "setBackgroundResource", R.drawable.orange_circle_gradient);
                }

                //Set the icon depending object List
                switch (iconWeather){
                    case "01d":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d01);
                        break;
                    case "01n":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d01);
                        break;
                    case "02d":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d02);
                        break;
                    case "02n":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d02);
                        break;
                    case "03d":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d03);
                        break;
                    case "03n":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d03);
                        break;
                    case "04d":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d03);
                        break;
                    case "04n":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d03);
                        break;
                    case "09d":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d09);
                        break;
                    case "09n":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d09);
                        break;
                    case "10d":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d10);
                        break;
                    case "10n":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d10);
                        break;
                    case "11d":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d11);
                        break;
                    case "11n":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d11);
                        break;
                    case "13d":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d13);
                        break;
                    case "13n":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d13);
                        break;
                    case "50d":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d50);
                        break;
                    case "50n":remoteViews.setImageViewResource(R.id.IconWidget, R.drawable.d50);
                        break;
                }


                //Update Widget Layout
                appWidgetManager.updateAppWidget(thisWidget, remoteViews);



            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);

        }


    };
    public void reloadWidget(Context context){

        //Reload Widget Location Manager
        LocationManager locationManager;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        startLocation = true;



    }
}

