package com.example.a194_lab_1.healthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    Bundle _bundle;
    int _bundleInt;
    EditText _date, _sleep, _wake;
    String _dateSql, _sleepSql, _wakeSql; //store data from db

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //open to use db
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        //create table if not exist
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS user (_id INTEGER PRIMARY KEY AUTOINCREMENT, sleep VARCHAR(5), wake VARCHAR(5), date VARCHAR(11))"
        );
        Log.d("SLEEP_FORM", "CREATE TABLE ALREADY");

        //get Bundle
        _bundle = getArguments();

        _date = getView().findViewById(R.id.sleep_fo_date);
        _sleep = getView().findViewById(R.id.sleep_fo_timeSleep);
        _wake = getView().findViewById(R.id.sleep_fo_timeWake);

        int count = 0;

        //แสดงข้อมูลเก่า เมื่อต้องการจะแก้ไข
        if(_bundle != null){
            _bundleInt = _bundle.getInt("_id"); //get Bundle to Int

            Cursor myCursor = myDB.rawQuery("SELECT * FROM user", null);
            while (myCursor.moveToNext()){
                if(count == _bundleInt){
                    _dateSql = myCursor.getString(3);
                    _sleepSql = myCursor.getString(1);
                    _wakeSql = myCursor.getString(2);
                    _bundleInt = myCursor.getInt(0); //เช็คเป็น _id เพราะจะเอาไปอัพเดตตาราง

                    _date.setText(_dateSql);
                    _sleep.setText(_sleepSql);
                    _wake.setText(_wakeSql);

                    Log.d("SLEEP_FORM", "Count = "+count+" _bundleInt = "+_bundleInt+" _id = "+_bundleInt);
                } else {
                    count += 1;
                }
            }
        }


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
                EditText _timeSleep = getView().findViewById(R.id.sleep_fo_timeSleep);
                EditText _timeWake= getView().findViewById(R.id.sleep_fo_timeWake);

                String _dateStr = _date.getText().toString();
                String _timeSleepStr = _timeSleep.getText().toString();
                String _timeWakeStr = _timeWake.getText().toString();

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
