package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.SQLException;


public class AlarmActivity extends AppCompatActivity {


    EditText minutesEditText;
    EditText hoursEditText;
    TextView hourtext;
    TextView mintext;
    Button Setbtn;
    private AlarmTimeHelper db; //need to get this from a file
    private Cursor cursor;
    private SQLiteDatabase mDatabase;
    private AlarmListAdapter mAdapter;
    private static final String SAMPLE_DB_NAME = "time.db";
    private static final String SAMPLE_TABLE_NAME = "time";

    private AlarmTimeHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_main);

        //for menu
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        myToolbar.setTitle("My Meditation App | Alarm");
        setSupportActionBar(myToolbar);

        //initialize all the fields and text
        minutesEditText = (EditText) findViewById(R.id.minutes_Numbers);
        hoursEditText = (EditText) findViewById(R.id.hours_Number);
        hourtext = (TextView) findViewById(R.id.tv1);
        mintext = (TextView) findViewById(R.id.tv2);

        Setbtn = (Button) findViewById(R.id.Setbutton);

        dbhelper = new AlarmTimeHelper(this);

        //try2.1
        //AlarmTimeHelper dbhelper = new AlarmTimeHelper(this);
        //mDatabase = dbhelper.getWritableDatabase();

        //mydatabase.sendlistAlarms(mDatabase, newAlarm2);
        //File sdcardPath= databaseContext.getDatabasePath(SAMPLE_DB_NAME);
        //String sdcardstring ="data/data/MyApplication/";
        //mDatabase = createDbFileOnSDCard(sdcardstring, SAMPLE_DB_NAME);

        //atempts at file based stuff above

        mDatabase = dbhelper.getSqlDB();
        //if nothing returns it may create something
        if(mDatabase == null){

            try {
                dbhelper.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //incase for testing
            dbhelper.onUpgrade(dbhelper.sqlDB, 0, 1);
            dbhelper.createAlarmTime(new AlarmTime("2020.06.04", "2:2:20", "30"));
            //dbhelper.close();

        }

            Setbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //get info for current date and time
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                    String currentDate = sdf.format(new Date());
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                    String currentTime = sdf2.format(new Date());

                    //get current hour and min
                    int hour = (new Date()).getHours();
                    int min = (new Date()).getMinutes();

                    if (hoursEditText.getText().toString().trim().length() == 0 || minutesEditText.getText().toString().trim().length() == 0) {
                        return;
                        //need error message
                    }
                    int hours = Integer.parseInt(hoursEditText.getText().toString());
                    int mins = Integer.parseInt(minutesEditText.getText().toString());
                    //error checking below
                    if (hours > 24 || hours < 0) {
                        return;
                        //need error message
                        //Toast toast = Toast. makeText(getApplicationContext(), "This has invalid hours", Toast. LENGTH_SHORT);
                    }
                    if (mins > 59 || mins < 0) {
                        return;
                        //need error message but no work
                        //Toast toast = Toast. makeText(getApplicationContext(), "This has invalid minutes", Toast. LENGTH_SHORT);
                    }

                    int hours2 = hour + hours;
                    int mins2 = min + mins;
                    //incase min between current and set min is over an hour
                    if(mins2 > 59 ){
                        int hours3 = mins2%60;
                        double mins3 = ((double)mins2/60)-1;
                        double mins4 = mins2* mins3;
                        mins2 = (int) mins4;
                        hours2 = hours2 + hours3;
                    }



                    //stuff for setting up the clock
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_HOUR, hours2);
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, mins2);
                    intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);

                    //record amount of time meditating
                    String amounttime = String.valueOf(((hours * 60) + mins));

                    //try2
//                    ContentValues cv2 = new ContentValues();
//                    cv2.put(AlarmContract.AlarmEntry.DATE, currentDate);
//                    cv2.put(AlarmContract.AlarmEntry.TIME, currentTime);
//                    cv2.put(AlarmContract.AlarmEntry.AmountTIME, amounttime);
//                    mDatabase.insert(AlarmContract.AlarmEntry.TABLE_NAME, null, cv2);
//
//                    //mAdapter.sawpCursor(getAllAlarmItems());
//                    hoursEditText.getText().clear();
//                    minutesEditText.getText().clear();

                    //try4
                    //SQLiteDatabase sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
                    //dbhelper.addContacts(newAlarm2);

                    //setup new data and closing
                    AlarmTime newAlarm2 = new AlarmTime(currentDate, currentTime, amounttime);
                    dbhelper.createAlarmTime(newAlarm2);
                    dbhelper.close();

                    //would go to alarmclock
                    startActivity(intent);


                }
            });

        }

//for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }
//for menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuHome:
                //something
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.mnuAlarm:
                //something
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }
    private Cursor getAllAlarmItems(){
        return mDatabase.query( AlarmContract.AlarmEntry.TABLE_NAME, null, null, null, null, null, AlarmContract.AlarmEntry._ID
        );
    }


    private SQLiteDatabase createDbFileOnSDCard(String sdcardPath, String dbfilename) {
        //File folder = getExternalFilesDir("dbAndroid");// Folder Name
        //File myFile = new File(folder, SAMPLE_DB_NAME);// Filename
        File dbf = new File(sdcardPath, dbfilename);
        //LogUtils.info("dbf.getAbsolutePath():" + dbf.getAbsolutePath());
        if (!dbf.exists()) {
            try {
                if (dbf.createNewFile()) {
                    return SQLiteDatabase.openOrCreateDatabase(dbf, null);
                }
            } catch (IOException e) {

            }
        } else {
            return SQLiteDatabase.openOrCreateDatabase(dbf, null);
        }

        return null;
    }
}
