package com.example.prasannakumar.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import database.DatabaseHelper;
import database.Note;

/**
 * Created by prasannakumar on 27/09/18.
 */

public class TrackerService extends Service {

    private MyService gpsTracker;
    private DatabaseHelper db;
    private List<Note> notesList;
   // private final static int INTERVAL = 1000 * 60 * 5; //5 minutes
    private final static int INTERVAL = 5000;//5 seconds
    Handler mHandler = new Handler();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone
        Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show();
        startRepeatingTask();
        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
        //stopping the player when service is destroyed

    }
    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
            doSomething();
            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };

    private void doSomething() {

        db = new DatabaseHelper(this);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        gpsTracker = new MyService(this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
           Log.e("TAG1","latitude::"+String.valueOf(latitude));
            Log.e("TAG1","longitude::"+String.valueOf(longitude));
            Log.e("TAG1","Date::"+date);
            createNote(""+String.valueOf(latitude),""+String.valueOf(longitude),""+date);

        }else{
            gpsTracker.showSettingsAlert();
        }
    }



    void startRepeatingTask()
    {
        mHandlerTask.run();
    }
    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mHandlerTask);
        Toast.makeText(this,"Service Stopped",Toast.LENGTH_LONG).show();
    }
    private void createNote(String lat,String log,String time) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertNote(lat,log,time);

        // get the newly inserted note from db
       // Note n = db.getNote(id);


    }

}
