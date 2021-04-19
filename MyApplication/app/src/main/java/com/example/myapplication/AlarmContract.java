package com.example.myapplication;

import android.provider.BaseColumns;

public class AlarmContract {

    private AlarmContract(){}

    public static final class AlarmEntry implements BaseColumns{
        public static final String TABLE_NAME = "AlarmTime";
        //public static String ID = "_id";
        public static final String DATE = "date";
        public static final String TIME = "Time";
        public static final String AmountTIME = "AmountTime";

    }
}
