package com.example.macmini_desarrollo2.weatherandroidapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Oswaldo Morales on 07/06/16.
 *
 */
public class rowListAdapter extends ArrayAdapter {

    private Context _context;
    ArrayList<String> _title;
    ArrayList<String> _tempData;
    ArrayList <String> _iconData;

    public rowListAdapter(Context context, int resource,  ArrayList<String> title, ArrayList<String> tempData, ArrayList <String> iconData) {
        super(context, resource, title);
        this._title = title;
        this._context = context;
        this._tempData = tempData;
        this._iconData = iconData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.row_item, parent, false);

        //Layout Parameters
        TextView tittleText = (TextView) view.findViewById(R.id.text_1);
        TextView tempText = (TextView) view.findViewById(R.id.tempData);
        ImageView iconImage= (ImageView) view.findViewById(R.id.iconWeather);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_A);

        Calendar calendar = Calendar.getInstance();
        int HR24 = calendar.get(Calendar.HOUR_OF_DAY);
        int purple = ContextCompat.getColor(_context, R.color.colorPrimaryDark);
        int orange = ContextCompat.getColor(_context, R.color.hardOrange);

        // Check Hour to change colors if is night
        if (HR24 >= 20){
            tittleText.setBackgroundColor(purple);
            linearLayout.setBackgroundResource(R.drawable.purple_box);

        }else {
            tittleText.setBackgroundColor(orange);
            linearLayout.setBackgroundResource(R.drawable.orange_box);

        }


        //Set Icon  dependif List Poition
        switch (_iconData.get(position)){

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

        String tittleString = "  "+ _title.get(position);
        String tempString = "  "+ _tempData.get(position ) + "º";

        tempText.setText(tempString);
        tittleText.setText(tittleString);

        return view;


    }
}
