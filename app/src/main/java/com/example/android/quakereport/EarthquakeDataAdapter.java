package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 9/25/2017.
 */

class ViewHolder {
    TextView myMagnitude, myLocationOffset, myPrimaryLocation, myDate, myTime;


    ViewHolder(View view) {
        myMagnitude = (TextView) view.findViewById(R.id.mag_textView);
        myPrimaryLocation = (TextView) view.findViewById(R.id.primary_location);
        myLocationOffset = (TextView) view.findViewById(R.id.location_offset);
        myDate = (TextView) view.findViewById(R.id.date_textView);
        myTime = (TextView) view.findViewById(R.id.time_textView);
    }
}

public class EarthquakeDataAdapter extends ArrayAdapter<Earthquake> {

    ArrayList<Earthquake> list;
    Context context;

    public EarthquakeDataAdapter(@NonNull Context context, @NonNull List<Earthquake> list) {
        super(context, 0, list);

        this.context = context;
        this.list = (ArrayList<Earthquake>) list;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, viewGroup, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        // Can use either one, Good practice to use getItem
        // Earthquake temp = list.get(i);
        Earthquake temp = (Earthquake) getItem(i);

        String originalLocation = temp.getLocation();


        //Set magnitude
        String formattedMagnitude = formatMagnitude(temp.getMagnitude());
        holder.myMagnitude.setText(formattedMagnitude);
        TextView magnitudeView = holder.myMagnitude;
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(temp.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);



        //Set primary and offset Location
        String primaryLocation;
        String locationOffset;
        final String LOCATION_SEPARATOR = " of ";

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = "Near the";
            primaryLocation = originalLocation;
        }
        holder.myLocationOffset.setText(locationOffset);
        holder.myPrimaryLocation.setText(primaryLocation);


        //Set date and time separately
        Long dateLong = temp.getTimeInMilliseconds();
        Date dateObject = new Date(dateLong);
        String formattedDate = formatDate(dateObject);
        String formattedTime = formatTime(dateObject);
        holder.myDate.setText(formattedDate);
        holder.myTime.setText(formattedTime);

        return row;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }
}
