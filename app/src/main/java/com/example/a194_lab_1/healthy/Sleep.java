package com.example.a194_lab_1.healthy;

import android.content.ContentValues;

public class Sleep {

    ContentValues _row = new ContentValues();

    private String timeSleep;
    private String timeWake;
    private String date;
    String time;

    public Sleep() {}

    public Sleep(String timeSleep, String timeWake, String date) {
        this.setTimeSleep(timeSleep);
        this.setTimeWake(timeWake);
        this.setDate(date);
    }

    public void setContent(String timeSleep, String timeWake, String date) {
        this._row.put("sleep", timeSleep);
        this._row.put("wake", timeWake);
        this._row.put("date", date);
        setTime(timeSleep, timeWake);
    }

    public ContentValues getContent() {
        return _row;
    }

    public String getTimeSleep() {
        return timeSleep;
    }

    public void setTimeSleep(String timeSleep) {
        this.timeSleep = timeSleep;
    }

    public String getTimeWake() {
        return timeWake;
    }

    public void setTimeWake(String timeWake) {
        this.timeWake = timeWake;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String timeSleep, String timeWake) {
        int _hour;
        int _min;

        String[] _timeSleep = timeSleep.split(":");
        int _timeSleep_hour = Integer.parseInt(_timeSleep[0])%12;
        int _timeSleep_min = Integer.parseInt(_timeSleep[1]);

        String[] _timeWake = timeWake.split(":");
        int _timeWake_hour = Integer.parseInt(_timeWake[0])%12;
        int _timeWake_min = Integer.parseInt(_timeWake[1]);

        _hour = 12 - Math.abs(_timeSleep_hour - _timeWake_hour);
        _min = 60 - Math.abs(_timeSleep_min - _timeWake_min);

        if(_timeWake_min >= _timeSleep_min){
            this.time = String.valueOf(_hour)+":"+String.valueOf(_min);
        } else {
            this.time = String.valueOf(_hour-1)+":"+String.valueOf(_min);
        }
    }
}
