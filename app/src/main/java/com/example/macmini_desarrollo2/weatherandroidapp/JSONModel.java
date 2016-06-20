package com.example.macmini_desarrollo2.weatherandroidapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Oswaldo Morales on 07/06/16.
 *
 */

public class JSONModel {

    public ArrayList<String> clouds = new ArrayList<>();
    public ArrayList <String> humidity = new ArrayList<>();
    public ArrayList <String> pressure = new ArrayList<>();
    public ArrayList <String> speed = new ArrayList<>();

    public ArrayList <JSONObject> temp = new ArrayList<>();
    public ArrayList <String> tempMax = new ArrayList<>();
    public ArrayList <String> tempMin = new ArrayList<>();
    public ArrayList <String> tempDay = new ArrayList<>();
    public ArrayList <String> tempMorn = new ArrayList<>();
    public ArrayList <String> tempNight = new ArrayList<>();

    public ArrayList <String> descripWeather = new ArrayList<>();
    public ArrayList <String> iconWeather = new ArrayList<>();
    ArrayList <String> weekDay = new ArrayList<>();

    public  void  arrayList (JSONArray array) throws JSONException {

        // Clear All Variables in Arrays
        clouds.clear();
        humidity.clear();
        pressure.clear();
        speed.clear();
        tempMax.clear();
        tempMin.clear();
        tempDay.clear();
        tempMorn.clear();
        tempNight.clear();
        descripWeather.clear();
        iconWeather.clear();
        weekDay.clear();




        //Parse weather by Strings
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            String cloudsString = object.getString("clouds");
            String humidityString = object.getString("humidity");
            String pressureString = object.getString("pressure");
            String speedString = object.getString("speed");

            JSONObject tempObject = object.getJSONObject("temp");

            String tempMaxString = tempObject.getString("max");
            String tempMinString = tempObject.getString("min");
            String tempDayString = tempObject.getString("day");
            String tempMornSring = tempObject.getString("morn");
            String tempNigthString = tempObject.getString("night");

            JSONArray weatherArray = object.getJSONArray("weather");
            JSONObject object1 = weatherArray.getJSONObject(0);

            String descriptionWeather = object1.getString("description");
            String iconString = object1.getString("icon");



            // Add Variables to Arrays
            clouds.add(cloudsString );
            humidity.add(humidityString);
            pressure.add(pressureString);
            speed.add(speedString);
            temp.add(tempObject);
            tempMax.add(tempMaxString);
            tempMin.add(tempMinString);
            tempDay.add(tempDayString);
            tempMorn.add(tempMornSring);
            tempNight.add(tempNigthString);
            descripWeather.add(descriptionWeather);
            iconWeather.add(iconString);

            //Format Date to set in Calendar
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            String formattedDate = dayFormat.format(calendar.getTime());


            weekDay.add(formattedDate);


        }



    }

}
