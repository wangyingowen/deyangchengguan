package com.shibei.deyangcitymanager.uitls;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Out {
  public static void out(String str){
    Log.e("wangying",str);
  }
  public static void  Toast(Context ct,String str){
    Toast.makeText(ct,str,Toast.LENGTH_SHORT).show();
  }
  public static void  toast(String str,Context ct){
    Toast.makeText(ct,str,Toast.LENGTH_SHORT).show();
  }
  public static String amendLocation(Double lon, Double lat) {
    if (lon == null || lat == null) {
      return "";
    }
    String offsetLon = lon + "";// + MyApplication.offset[0] + "";
    String offsetLat = lat + "";// + MyApplication.offset[1] + "";
    return "POINT (" + offsetLon + " " + offsetLat + ")";
  }
}
