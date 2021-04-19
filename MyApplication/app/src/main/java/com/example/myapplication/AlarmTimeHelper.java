package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myapplication.AlarmContract.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class AlarmTimeHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "time.db";
    public static final String TABLE_NAME = "time";
    public static final int DB_VERSION = 1;
    public static String ID = "_id";
   public static final String DATE = "date";
   public static final String TIME = "Time";
   public static final String AmountTIME = "AmountTime";
    private static AlarmTimeHelper mDbHelper;
    public SQLiteDatabase sqlDB;

    //public SQLiteDatabase sqlDB; //need to grab the database

    @Override
    public void onCreate(SQLiteDatabase db) { //need it for doing local

        final String sCreate = "create table if not exists " +
                TABLE_NAME + " (" +
                ID + " integer primary key autoincrement, " +
                DATE + " text not null, " +
                TIME + " text not null, " +
                AmountTIME + " text not null);";



        //Execute the create table sql statement
        db.execSQL(sCreate);
        //sqlDB.execSQL(sCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //implement local storage

        //try1
        //Drop the existing table if it exists
        //db.execSQL("DROP TABLE IF EXISTS " + AlarmEntry.TABLE_NAME);
        //Use the existing onCreate code to recreate the table
        //onCreate(db);

        //try2
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }


    }



    public static synchronized AlarmTimeHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (mDbHelper == null) {
            mDbHelper = new AlarmTimeHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }

    public AlarmTimeHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);}

    public ArrayList<AlarmTime> listContacts(){
        String sql = "select * from " + TABLE_NAME;
        //SQLiteDatabase  = this.getReadableDatabase();
        sqlDB = this.getReadableDatabase();
        ArrayList<AlarmTime> storeContacts = new ArrayList<>();
        Cursor cursor = sqlDB.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                String amounttime = cursor.getString(3);
                storeContacts.add(new AlarmTime(id,date, time, amounttime));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    public void addContacts(AlarmTime contacts){
        ContentValues values = new ContentValues();
        values.put(DATE, contacts.getDate());
        values.put(TIME, contacts.getTime());
        values.put(AmountTIME, contacts.getAmountTime());
        //SQLiteDatabase db = this.getWritableDatabase();
        sqlDB = this.getWritableDatabase();
        sqlDB.insert(TABLE_NAME, null, values);
    }

    public void updateContacts(AlarmTime contacts){
        ContentValues values = new ContentValues();
        values.put(DATE, contacts.getDate());
        values.put(TIME, contacts.getTime());
        values.put(AmountTIME, contacts.getAmountTime());
        //SQLiteDatabase db = this.getWritableDatabase();
        sqlDB = this.getWritableDatabase();
        sqlDB.update(TABLE_NAME, values, ID + "	= ?", new String[] { String.valueOf(contacts.getId())});
    }



        //help all is now broken

    public void open() throws SQLException{    sqlDB = this.getWritableDatabase();}
    public void close(){
        sqlDB.close();}

    //Create a new row in the table
    public long createAlarmTime(AlarmTime alarmtime)
    {
        //A container storing each column value for a row
        ContentValues cvs = new ContentValues();

        //Add values for each column of the row
        cvs.put(DATE, alarmtime.date);
        cvs.put(TIME, alarmtime.time);
        cvs.put(AmountTIME, alarmtime.AmountTime);

        //Execute the insert statement, which returns the autoincrement value
        long autoId = sqlDB.insert(TABLE_NAME, null, cvs);

        //Update the purchase with the assigned id value
        alarmtime.id = Long.parseLong(String.valueOf(autoId));
        return autoId;
    }

    //Get all rows in the table
    public Cursor getAllAlarmTime()
    {
        String [] sFields = new String [] {ID, DATE, TIME, AmountTIME};
        return sqlDB.query(TABLE_NAME, sFields, null, null, null, null, null );
    }

    //Get a single row in the table
    public Cursor getAlarmTime(long id) throws SQLException
    {
        String [] sFields = new String [] {ID, DATE, TIME, AmountTIME};
        Cursor fpCursor = sqlDB.query(true, TABLE_NAME, sFields, ID + " = "
                + id, null, null, null, null, null);
        if(fpCursor != null)
        {
            fpCursor.moveToFirst();
        }
        return fpCursor;
    }

    public void sendSqlDB(SQLiteDatabase db) {
        this.sqlDB = db;
    }
    public SQLiteDatabase getSqlDB(){
        sqlDB = this.getWritableDatabase();
    return sqlDB;
    }
}
