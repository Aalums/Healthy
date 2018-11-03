package com.example.a194_lab_1.healthy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SleepAdapter extends ArrayAdapter<Sleep> {

    List<Sleep> sleep = new ArrayList<>();
    Context context;


    public SleepAdapter(@NonNull Context context, int resource, List<Sleep> objects) {
        super(context, resource, objects);
        this.sleep = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View _sleepItem = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_item, parent, false);
        TextView _date = _sleepItem.findViewById(R.id.sleep_item_date);
        TextView _time = _sleepItem.findViewById(R.id.sleep_item_time);
        TextView _sleep = _sleepItem.findViewById(R.id.sleep_item_sleep_wake);

        Sleep _row = sleep.get(position);
        _date.setText(_row.getDate());
        _time.setText(Calculate (_row.getTimeSleep(), _row.getTimeWake()));
        _sleep.setText(_row.getTimeSleep()+" - "+_row.getTimeWake());

        return _sleepItem;
    }

    public String Calculate (String sleep, String wake) {
        int hour;
        int min;

        String[] t_sleep = sleep.split(":");
        int sleep_hour = Integer.parseInt(t_sleep[0]);
        int sleep_min = Integer.parseInt(t_sleep[1]);

        String[] t_wake = wake.split(":");
        int wake_hour = Integer.parseInt(t_wake[0]);
        int wake_min = Integer.parseInt(t_wake[1]);

        //hour
        if (sleep_hour <= wake_hour){
            hour = wake_hour-sleep_hour;
        }else {
            hour = (24-sleep_hour)+wake_hour;
        }

        //min
        if (sleep_min >= wake_min) {
            min = (sleep_min - wake_min) + wake_min;
            hour -= 1;
        }else {
            min = wake_min - sleep_min;
        }

        return String.valueOf(hour)+":"+String.valueOf(min);
    }
}
