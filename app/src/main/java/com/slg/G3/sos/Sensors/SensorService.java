package com.slg.G3.sos.Sensors;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.slg.G3.sos.DbHelper;
import com.slg.G3.sos.Utils.TinyDB;


import java.util.ArrayList;
import java.util.List;

public class SensorService extends Service {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    public static String sosPredefinedNoLocation, sosPredefinedLocation;



    public SensorService(){
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sosPredefinedNoLocation = "Mwen genyen ijans. Tanpri kontakte m rapid konnya menm. VIT! VIT!";
        sosPredefinedLocation = "Mwen genyen ijans. Tanpri kontakte m rapid konnya menm. Men lokalizasyon mwen: ";


        //start the foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @SuppressLint("MissingPermission")
            @Override
            public void onShake(int count) {
                //check if the user has shaked the phone for 3 time in a row
                if(count==3) {
                    //vibrate the phone
                    vibrate();

                    //create FusedLocationProviderClient to get the user location
                    FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());



                    //use the PRIORITY_BALANCED_POWER_ACCURACY so that the service doesn't use unnecessary power via GPS
                    //it will only use GPS at this very moment
                    fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, new CancellationToken() {
                        @Override
                        public boolean isCancellationRequested() {
                            return false;
                        }
                        @NonNull
                        @Override
                        public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                            return null;
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {

                                //Get the default SmsManager//
                                SmsManager smsManager = SmsManager.getDefault();

                                // get the list of all the contacts in Database
                                DbHelper dbHelper = new DbHelper(SensorService.this);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                String sosMessage = sosPredefinedLocation + "http://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();


                                Cursor cursor = db.rawQuery("select * from SOSContact", null);
                                while (cursor.moveToNext()) {
                                    String num = cursor.getString(2);
                                    //Send the SOS
                                    smsManager.sendTextMessage(num, null, sosMessage, null, null);
                                }
                                cursor.close();

                                Toast.makeText(SensorService.this, "SOS la ale. Tanpri pran swen ou annatandan.", Toast.LENGTH_SHORT).show();


//                            } else {
//                                // get the list of all the contacts in Database
//                                DbHelper dbHelper = new DbHelper(SensorService.this);
//                                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                                String sosMessage = sosPredefinedNoLocation;
//
//                                    //Get the default SmsManager//
//                                SmsManager smsManager = SmsManager.getDefault();
//
//                                Cursor cursor = db.rawQuery("select * from SOSContact", null);
//                                while (cursor.moveToNext()) {
//                                    String num = cursor.getString(2);
//                                    smsManager.sendTextMessage(num, null, sosMessage, null, null);
//
//                                }
//                                    Toast.makeText(getApplicationContext(), "SOS la ale, san lokalizasyon ou.", Toast.LENGTH_SHORT).show();
//
//                            }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // get the list of all the contacts in Database
                            DbHelper dbHelper = new DbHelper(SensorService.this);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            String sosMessage = sosPredefinedNoLocation ;

                                //Get the default SmsManager//
                            SmsManager smsManager = SmsManager.getDefault();

                            Cursor cursor = db.rawQuery("select * from SOSContact", null);
                            while (cursor.moveToNext()) {

                                String num = cursor.getString(2);
                                //Send the SOS
                                smsManager.sendTextMessage(num, null, sosMessage, null, null);

                            }
                                Toast.makeText(getApplicationContext(), "SMS la nan wout.", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

        //register the listener
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    //method to vibrate the phone
    public void vibrate(){
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        VibrationEffect vibEff;
        //Android Q and above have some predefined vibrating patterns
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibEff=VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK);
            vibrator.cancel();
            vibrator.vibrate(vibEff);
        }else{
            vibrator.vibrate(1000);
        }



    }

    //For Build versions higher than Android Oreo, we launch
    // a foreground service in a different way. This is due to the newly
    // implemented strict notification rules, which require us to identify
    // our own notification channel in order to view them correctly.
    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground()
    {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("Pran swen tet ou")
                .setContentText("SOS la pou ede w")

                //this is important, otherwise the notification will show the way
                //you want i.e. it will show some default notification

                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }


    @Override
    public void onDestroy() {

        //create an Intent to call the Broadcast receiver
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, ReactivateService.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

}
