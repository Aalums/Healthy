package com.example.a194_lab_1.healthy;

import android.content.ContentValues;

public class Sleep {

    ContentValues _row = new ContentValues();

    private String timeSleep;
    private String timeWake;
    private String date;

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

}
