package com.example.a194_lab_1.healthy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        TextView _diff = _sleepItem.findViewById(R.id.sleep_item_time);
        TextView _sleep = _sleepItem.findViewById(R.id.sleep_item_sleep_wake);

        Sleep _row = sleep.get(position);
        _date.setText(_row.getDate());
        _diff.setText(_row.getTime());
        _sleep.setText(_row.getTimeSleep()+" - "+_row.getTimeWake());

        return _sleepItem;
    }
}
