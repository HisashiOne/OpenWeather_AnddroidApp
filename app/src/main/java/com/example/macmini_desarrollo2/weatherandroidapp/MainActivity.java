package com.example.macmini_desarrollo2.weatherandroidapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macmini_desarrollo2.weatherandroidapp.Client.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.util.Calendar;


/**
 * Created by Oswaldo Morales on 07/06/16.
 *
 */

public class MainActivity extends AppCompatActivity implements LocationListener {


    private boolean network_enabled = false;

    Location l;

    Boolean startLocation;
    TextView cityTxt, cloudsTxt, humidityTxt, pressureTxt, speedTxt, tempMaxTxt, tempMinTxt, mornTxt, dayTxt, nightTxt;
    TextView weatherTxt, dateText, tempText;
    ImageView iconImage, lineImage1, lineImage2;
    TwoWayView twoWayView;
    ImageButton showTemp;
    LinearLayout mainContainer, loadingContainer, circleContainer, circleContainer_2, LineContainer_1;
    RelativeLayout mainView;


    rowListAdapter adapter;

    Boolean containerFlag;

    JSONModel model = new JSONModel();
    //private boolean gps_enabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
       // CurrentLocationListener currentLocationListener = new CurrentLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        //Asign Layout Objects
        cityTxt = (TextView) findViewById(R.id.city);
        cloudsTxt = (TextView) findViewById(R.id.clouds);
        humidityTxt = (TextView) findViewById(R.id.humidity);
        pressureTxt = (TextView) findViewById(R.id.pressure);
        speedTxt = (TextView) findViewById(R.id.speed);
        tempMaxTxt = (TextView) findViewById(R.id.tempMax);
        tempMinTxt = (TextView) findViewById(R.id.tempMin);
        mornTxt = (TextView) findViewById(R.id.tempMorn);
        dayTxt = (TextView) findViewById(R.id.tempDay);
        nightTxt = (TextView) findViewById(R.id.tempNight);
        weatherTxt = (TextView) findViewById(R.id.weatherDescrip);

        iconImage = (ImageView) findViewById(R.id.weatherIcon);
        twoWayView = (TwoWayView) findViewById(R.id.lvItems);
        dateText = (TextView) findViewById(R.id.dateText);
        tempText = (TextView) findViewById(R.id.temp_A);

        showTemp = (ImageButton) findViewById(R.id.expandView);
        mainContainer = (LinearLayout) findViewById(R.id.mainContainer);
        loadingContainer = (LinearLayout) findViewById(R.id.LoadingView);
        mainView = (RelativeLayout) findViewById(R.id.mainView);
        circleContainer = (LinearLayout) findViewById(R.id.circle);
        circleContainer_2 = (LinearLayout) findViewById(R.id.cicle_2);
        LineContainer_1 = (LinearLayout) findViewById(R.id.line_1);
        lineImage1 = (ImageView) findViewById(R.id.Line_IMG1);
        lineImage2 = (ImageView) findViewById(R.id.Line_IMG2);

        containerFlag = true;





        //Set Colors Background

        Calendar calendar = Calendar.getInstance();
        int HR24 = calendar.get(Calendar.HOUR_OF_DAY);


        int purple = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
        int orange = ContextCompat.getColor(getApplicationContext(), R.color.primaryOrange);
        int hard_orange = ContextCompat.getColor(getApplicationContext(), R.color.hardOrange);



        //Change Colors if is Night
        if (HR24 >= 20){
            mainView.setBackgroundResource(R.drawable.purple_gradient);
            circleContainer.setBackgroundResource(R.drawable.purple_cicle_gradient);
            circleContainer_2.setBackgroundResource(R.drawable.purple_cicle_gradient);
            LineContainer_1.setBackgroundColor(purple);
            lineImage1.setBackgroundColor(purple);
            lineImage2.setBackgroundColor(purple);

        }
        else {

            mainView.setBackgroundResource(R.drawable.orange_gradient);
            circleContainer.setBackgroundResource(R.drawable.orange_circle_gradient);
            circleContainer_2.setBackgroundResource(R.drawable.orange_circle_gradient);
            LineContainer_1.setBackgroundColor(orange);
            lineImage1.setBackgroundColor(hard_orange);
            lineImage2.setBackgroundColor(hard_orange);
        }

        showTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resizeView();
            }
        });



        // Find Location by Network Conection
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (network_enabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }

        startLocation = true;



     }


    //Main Responce  Handler
    private JsonHttpResponseHandler handler = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);


            loadingContainer.setVisibility(View.GONE);



            try {


                // Get City And Name
                JSONObject city = response.getJSONObject("city");
                String name = city.getString("name");
                cityTxt.setText(name);

                JSONArray list = response.getJSONArray("list");
                model.arrayList(list);

                setData(0);


                adapter = new rowListAdapter(getApplicationContext(), R.layout.row_item, model.weekDay, model.tempDay, model.iconWeather);
                twoWayView.setAdapter(adapter);
                twoWayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        setData(position);
                    }
                });



            }catch (Exception e){
                e.printStackTrace();
                loadingContainer.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error Conection", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            loadingContainer.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Not Data Conection", Toast.LENGTH_SHORT).show();
        }
    };


    public void setData(Integer position){

        //Set Data to Model
        cloudsTxt.setText(model.clouds.get(position));
        humidityTxt.setText(model.humidity.get(position));
        pressureTxt.setText(model.pressure.get(position));
        speedTxt.setText(model.speed.get(position));
        weatherTxt.setText(model.descripWeather.get(position));
        dateText.setText(model.weekDay.get(position));

        String maxTmpString = model.tempMax.get(position) + "º";
        String minTmpString = model.tempMin.get(position) + "º";
        String mornTmpStrimg = model.tempMorn.get(position) + "º";
        String dayTmpString = model.tempDay.get(position) + "º";
        String nightTmpString = model.tempNight.get(position) + "º";

        tempMaxTxt.setText( maxTmpString);
        tempMinTxt.setText(minTmpString);
        mornTxt.setText(mornTmpStrimg);
        dayTxt.setText(dayTmpString);
        nightTxt.setText(nightTmpString);
        tempText.setText(dayTmpString);

        //Check Icon in Model

        switch (model.iconWeather.get(position)){

            case "01d":
                //Clear Sky
                iconImage.setImageResource(R.drawable.d01);
                break;
            case "01n":
                //clear Sky Night
                iconImage.setImageResource(R.drawable.d01);
                break;
            case "02d":
                iconImage.setImageResource(R.drawable.d02);
                break;
            case "02n":
                iconImage.setImageResource(R.drawable.d02);
                break;
            case "03d":
                iconImage.setImageResource(R.drawable.d03);
                break;
            case "03n":
                iconImage.setImageResource(R.drawable.d03);
                break;
            case "04d":
                iconImage.setImageResource(R.drawable.d03);
                break;
            case "04n":
                iconImage.setImageResource(R.drawable.d03);
                break;
            case "09d":
                iconImage.setImageResource(R.drawable.d09);
                break;
            case "09n":
                iconImage.setImageResource(R.drawable.d09);
                break;
            case "10d":
                //rain
                iconImage.setImageResource(R.drawable.d10);
                break;
            case "10n":
                //Night Rain
                iconImage.setImageResource(R.drawable.d10);
                break;
            case "11d":
                iconImage.setImageResource(R.drawable.d11);
                break;
            case "11n":
                iconImage.setImageResource(R.drawable.d11);
                break;
            case "13d":
                iconImage.setImageResource(R.drawable.d13);
                break;
            case "13n":
                iconImage.setImageResource(R.drawable.d13);
                break;
            case "50d":
                iconImage.setImageResource(R.drawable.d50);
                break;
            case "50n":
                iconImage.setImageResource(R.drawable.d50);
                break;


        }

    }
    public  void resizeView (){


        //Resize Container

        ViewGroup.LayoutParams params = mainContainer.getLayoutParams();

        if (containerFlag){

            params.height = 400;
            mainContainer.setLayoutParams(params);
            containerFlag = false;

        }else {

            params.height = 200;
            mainContainer.setLayoutParams(params);
            containerFlag = true;

        }


    }
    @Override
    public void onLocationChanged(Location location) {

        l = location;

        // Get Location from netework and send to rest Service
        if (startLocation ){

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
}
