package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

//cst143 Lucas Tom

public class MainActivity extends AppCompatActivity {
    RecyclerView itemList;
    private AlarmTimeHelper db; //need to get this from a file
    private Cursor cursor;
    private SQLiteDatabase mDatabase;
    private AlarmListAdapter mAdapter;
    private ArrayList<AlarmTime> allContacts=new ArrayList<>();

    private static final String SAMPLE_DB_NAME = "time.db";
    private static final String SAMPLE_TABLE_NAME = "time";
    TextView tvInfo;
    private AlarmTimeHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("My Meditation App");
        setSupportActionBar(myToolbar);

        tvInfo = (TextView)findViewById(R.id.tvinfo);
        tvInfo.setText("Pervious Meditation Sessions Below");

        //setting up items
        itemList = (RecyclerView) findViewById(R.id.itemView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        itemList.setLayoutManager(linearLayoutManager);
        //itemList.setHasFixedSize(true);
        dbhelper = new AlarmTimeHelper(this);
        //SQLiteDatabase sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
        //File sdcardPath= databaseContext.getDatabasePath(SAMPLE_DB_NAME);
        //String sdcardstring ="data/data/MyApplication/";
        //getExternalFilesDir("dbAndroid");
        //getExternalFilesDir("dbAndroid").getAbsolutePath()
        //mDatabase = createDbFileOnSDCard(sdcardstring, SAMPLE_DB_NAME);

        //attempts at doing file based stuff above
        mDatabase = dbhelper.getSqlDB();
        if(mDatabase == null){
            //incase nothing shows up
            //mostly for testing

        }else {
            dbhelper.onCreate(mDatabase);
            allContacts = dbhelper.listContacts();
            if (allContacts.size() > 0) {
                itemList.setVisibility(View.VISIBLE);

                LinearLayoutManager manager = new LinearLayoutManager(this);
                itemList.setLayoutManager(manager);
                mAdapter = new AlarmListAdapter(this, allContacts);

                itemList.setAdapter(mAdapter);
                dbhelper.close();
            } else {
                itemList.setVisibility(View.GONE);
                dbhelper.close();
            }
        }

    }
    @Override
//for menu
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
                return true;

            case R.id.mnuAlarm:
                //something
                Intent intent = new Intent(this, AlarmActivity.class);
                this.startActivity(intent);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    //try2
    private Cursor getAllAlarmItems(){
        return mDatabase.query( AlarmContract.AlarmEntry.TABLE_NAME, null, null, null, null, null, AlarmContract.AlarmEntry._ID
        );
    }

//try4
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

    private SQLiteDatabase createDbFileOnSDCard2(File sdcardPath, String dbfilename) {
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
