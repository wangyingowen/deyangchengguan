package com.shibei.deyangcitymanager.service;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import com.shibei.deyangcitymanager.MyApplication;
import com.shibei.deyangcitymanager.URLS;
import com.shibei.deyangcitymanager.uitls.Out;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LocationService extends Service implements LocationListener {

    private boolean flag = true;
    Handler hd, mHandler;
    LocationManager locMan;

    Context ct;
    private double lat;
    private double lon;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new Notification();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;

        startForeground(1, notification);
        hd = new Handler();

        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 0) {
                    Out.out("接受到111---"+lat+"  "+lon);
                    int zb=(int)lat;
                    if (zb > 0) {
                        // TODO: Consider calling
                        Out.out("定位到坐标咯：CCCCCCC");
                        submitLocation3(lat, lon);
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    } else {
                        Out.out("还没得坐标的AAAAAAA");
                    }



                }
            }

            ;
        };



        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat
            .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
            .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0,
                this);
        new myThread().start();


    }

    @Override
    public void onLocationChanged(Location location) {

        lat=location.getLatitude();
        lon=location.getLongitude();
        MyApplication.wd = lat;
               MyApplication.jd = lon;
        Log.e("wangying","lat："+lat);
        Log.e("wangying","lon："+lon);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e("wangying","onStatusChanged：定位的");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e("wangying","onProviderEnabled：定位的222");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e("wangying","onProviderDisabled：定位的3333");
    }


    private class myThread extends Thread {

        @Override
        public void run() {
            while (flag) {

//                if (locationManager == null) {
//                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//
//                }
                mHandler.sendEmptyMessage(0);
                try {
                    sleep(10 * 3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopLocation();
        flag = false;
    }

    private void stopLocation() {

//        if (locationManager != null) {
//            locationManager.removeUpdates(locationListener);
//        }
//        locationManager = null;
    }




//    /**
//     * 获取经纬度
//     *
//     * @param context
//     * @return
//     */
//    @SuppressLint("MissingPermission")
//    private void getLngAndLat(Context context) {
//
//        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
//            @SuppressLint("MissingPermission") final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location != null) {
//                MyApplication.wd = location.getLatitude();
//                MyApplication.jd = location.getLongitude();
//                Log.e("wangying","当前经纬度111:"+location.getLatitude()+","+location.getLongitude());
//                hd.post(new Runnable() {
//                    @Override
//                    public void run() {
//                       submitLocation3(location.getLatitude(),location.getLongitude());
//                    }
//                });
//
//
//
//
//            } else {//当GPS信号弱没获取到位置的时候又从网络获取
//                Out.out("木有获取到坐标");
//
//            }
//        } else {    //从网络获取经纬度
////            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
//           final Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if (location != null) {
//                MyApplication.jd = location.getLatitude();
//                MyApplication.wd = location.getLongitude();
//                hd.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        submitLocation3(location.getLatitude(),location.getLongitude());
//                    }
//                });
//            }
//        }
//
//    }
    public void submitLocation3(double latitude2, double longitude2) {

        SharedPreferences sharedPreferences = getSharedPreferences("zhongba", MODE_PRIVATE);
        String tokens = sharedPreferences.getString("token", "");
        String uid=sharedPreferences.getString("uid","");

        if (TextUtils.isEmpty(tokens)){
            return;
        }

        OkHttpClient mOkHttpClient = new OkHttpClient();


        RequestBody formBody = null;

            formBody=  new FormBody.Builder()
                    .add("police",uid)
//                    .add("status", Out.amendLocation(longitude2,latitude2))

                    .add("location",  Out.amendLocation(longitude2,latitude2))


                    .build();


        Request request = new Request.Builder()
                .url(URLS.UPDATE_LOCAITON)
                .addHeader("Authorization", "Token " + tokens)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {




            }


        });
    }
}
