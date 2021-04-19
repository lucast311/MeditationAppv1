package com.example.myapplication;

public class AlarmTime {
    public String date = "";
    public String time = "";
    public String AmountTime = "";
    public long id;

    public AlarmTime(String date, String time, String AmountTime){
        this.id = -1;
        this.date = date;
        this.time = time;
        this.AmountTime = AmountTime;
    }

    public AlarmTime(long id ,String date, String time, String AmountTime){
        this(date, time, AmountTime);
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAmountTime() {
        return AmountTime;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString(){
        return date + " at " + time + ": " +AmountTime;
    }

}
