//package com.slg.G3.sos;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import androidx.core.app.ActivityCompat;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.PowerManager;
//import android.provider.ContactsContract;
//import android.provider.Settings;
//import android.util.Log;
//
//import android.widget.Toast;
//
//import com.google.android.material.dialog.MaterialAlertDialogBuilder;
//
//import com.slg.G3.sos.Sensors.ReactivateService;
//import com.slg.G3.sos.Sensors.SensorService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SensorControl extends AppCompatActivity    {
//
//        private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;
//
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//
//            //check for runtime permissions
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED) {
//                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS,Manifest.permission.READ_CONTACTS}, 100);
//                }
//            }
//
//            //this is a special permission required only by devices using
//            //Android Q and above. The Access Background Permission is responsible
//            //for populating the dialog with "ALLOW ALL THE TIME" option
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 100);
//            }
//
//            //check for BatteryOptimization,
//            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (pm != null && !pm.isIgnoringBatteryOptimizations(getPackageName())) {
//                    askIgnoreOptimization();
//                }
//            }
//
//            //start the service
//            SensorService sensorService = new SensorService();
//            Intent intent = new Intent(this, sensorService.getClass());
//            if (!isMyServiceRunning(sensorService.getClass())) {
//                startService(intent);
//            }
//
//
//
//        }
//
//        //method to check if the service is running
//        private boolean isMyServiceRunning(Class<?> serviceClass) {
//            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//                if (serviceClass.getName().equals(service.service.getClassName())) {
//                    Log.i ("Service status", "Running");
//                    return true;
//                }
//            }
//            Log.i ("Service status", "Not running");
//            return false;
//        }
//
//        @Override
//        protected void onDestroy() {
//            Intent broadcastIntent = new Intent();
//            broadcastIntent.setAction("restartservice");
//            broadcastIntent.setClass(this, ReactivateService.class);
//            this.sendBroadcast(broadcastIntent);
//            super.onDestroy();
//        }
//
//
//
//
//
//        //this method prompts the user to remove any battery optimisation constraints from the App
//        private void askIgnoreOptimization() {
//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                @SuppressLint("BatteryLife") Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, IGNORE_BATTERY_OPTIMIZATION_REQUEST);
//            }
//
//        }
//
//
//
//}
