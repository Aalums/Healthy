package com.example.a194_lab_1.healthy;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SleepFragment extends Fragment {

    SQLiteDatabase myDB;
    ArrayList<Sleep> sleeps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ListView _sleepList = getView().findViewById(R.id.sleep_list);
        final SleepAdapter _sleepAdapter = new SleepAdapter(getActivity(), R.layout.fragment_sleep_item, sleeps);

        //เริ่มการใช้ DB
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        //สร้างตาราง
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS user (_id INTEGER PRIMARY KEY AUTOINCREMENT, sleep VARCHAR(5), wake VARCHAR(5), date VARCHAR(11))"
        );

        //Query
        Cursor myCursor = myDB.rawQuery("SELECT * FROM user", null);

        _sleepAdapter.clear();

        while (myCursor.moveToNext()) {
            String _timeSleep = myCursor.getString(1);
            String _timeWake = myCursor.getString(2);
            String _date = myCursor.getString(3);

            Log.d("SLEEP", "_id : "+myCursor.getInt(0)
                    +" sleep : "+_timeSleep+" wake : "+_timeWake+" date : "+_date);

            sleeps.add(new Sleep(_timeSleep, _timeWake, _date));
        }

        _sleepList.setAdapter(_sleepAdapter);

        //Click Item List
        _sleepList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id = _sleepList.getItemIdAtPosition(position);
                Log.d("SLEEP", "Position = " + id + "_id = " + (id+1));


                Bundle bundle = new Bundle();
                bundle.putInt("id", position);

                SleepFormFragment fragment = new SleepFormFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, fragment)
                        .addToBackStack(null)
                        .commit();
                Log.d("SLEEP", "GOTO SLEEP_FORM");
            }
        });

        myCursor.close();

        initAddBtn();
        initBackBtn();

    }

    void initAddBtn () {
        Button _addBtn = getView().findViewById(R.id.sleep_add_btn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFormFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("SLEEP", "GOTO SLEEP FORM");
            }
        });
    }

    void initBackBtn () {
        Button _backBtn = getView().findViewById(R.id.sleep_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("SLEEP", "GOTO MENU");
            }
        });
    }

}
