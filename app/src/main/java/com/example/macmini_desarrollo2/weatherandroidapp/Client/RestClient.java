package com.example.macmini_desarrollo2.weatherandroidapp.Client;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Oswaldo Morales on 07/06/16.
 *
 */
public class RestClient {


    private static final String Base_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String Language = "&units=metric&lang=es&appid=";
    private static final String client_ID = "a9ba67d0a9a33a56f6d485c0a03c58f1";
    static String _extURL;

    public  static void getWeather (String Lat, String Long, RequestParams params, AsyncHttpResponseHandler handler){

        _extURL = Base_URL +"lat=" + Lat + "&lon=" + Long + Language + client_ID;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(_extURL, params, handler);


    }


}
