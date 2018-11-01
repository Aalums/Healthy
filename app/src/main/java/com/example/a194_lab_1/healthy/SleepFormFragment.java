package com.example.a194_lab_1.healthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SleepFormFragment extends Fragment {

    SQLiteDatabase myDB;
    ContentValues _row;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSaveBtn();
        initBackBtn();

    }

    void initSaveBtn () {
        Button _saveBtn = getView().findViewById(R.id.sleep_fo_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

                myDB.execSQL(
                        "CREATE TABLE IF NOT EXISTS user (_id INTEGER PRIMARY KEY AUTOINCREMENT, sleep VARCHAR(5), wake VARCHAR(5), date VARCHAR(11))"
                );

                Log.d("SLEEP FORM", "CREATE TABLE");

                EditText _date = getView().findViewById(R.id.sleep_fo_date);
                EditText _timeSleep_hour = getView().findViewById(R.id.sleep_fo_timeSleep_hour);
                EditText _timeSleep_min = getView().findViewById(R.id.sleep_fo_timeSleep_min);
                EditText _timeWake_hour = getView().findViewById(R.id.sleep_fo_timeWake_hour);
                EditText _timeWake_min = getView().findViewById(R.id.sleep_fo_timeWake_min);

                String _dateStr = _date.getText().toString();
                String _timeSleepStr = _timeSleep_hour.getText().toString() + ":" + _timeSleep_min.getText().toString();
                String _timeWakeStr = _timeWake_hour.getText().toString() + ":" + _timeWake_min.getText().toString();

                Sleep _itemSleep = new Sleep();
                _itemSleep.setContent(_timeSleepStr, _timeWakeStr, _dateStr);

                _row = _itemSleep.getContent();

                myDB.insert("user", null, _row);

                Log.d("SLEEP FORM", "INSERT DATA");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(
                        getActivity(), "SAVE SUCCESS", Toast.LENGTH_SHORT
                ).show();
                Log.d("SLEEP FORM", "GOTO SLEEP");

            }
        });
    }

    void initBackBtn () {
        Button _backBtn = getView().findViewById(R.id.sleep_fo_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("SLEEP FORM", "GOTO SLEEP");
            }
        });
    }

}
