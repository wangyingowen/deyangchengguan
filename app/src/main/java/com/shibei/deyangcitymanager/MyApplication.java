package com.shibei.deyangcitymanager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.shibei.deyangcitymanager.mode.PoliceInfo;
import com.shibei.deyangcitymanager.uitls.Out;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wy250 on 2017/9/26.
 */

public class MyApplication extends Application {
    public static int schemeColors;
    public static PoliceInfo myPliceinfo;
    /**
     * 偏移配置
     */
    public static Double[] offset = new Double[]{
            0.0, 0.0
    };



    private static MyApplication instance;
    public static double jd=0;
    public  static  double wd=0;
    private Context mainActivity;
    public static Context sContext;

    public static String LOGIN_INFO="deyangchengguan";
    private List<Activity> activityList = new LinkedList<Activity>();




    public static PoliceInfo getMyPliceinfo() {
        return myPliceinfo;
    }

    public static void setMyPliceinfo(PoliceInfo myPliceinfo) {
        MyApplication.myPliceinfo = myPliceinfo;
    }

    @Override
    public void onCreate() {
        Out.out("APP木有启动吗？");
        super.onCreate();
        sContext=getApplicationContext();
//        mu.install(this);

//        schemeColors = getApplicationContext().getResources().getColor(R.color.main_bg);


    }

    // 閸欏本顥呴柨浣稿礋娓氬膩瀵繗骞忛崣鏈紁plication鐎圭偘绶�
    public static MyApplication getInstance() {

        if (instance == null) {
            synchronized (MyApplication.class) {
                if (null == instance) {
                    instance = new MyApplication();
                }
            }
        }

        return instance;
    }


    // 濞ｈ濮炴潻鎰攽閻ㄥ垷ctivity
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    // 闁拷閸戝搫绨查悽顭掔礉閸忔娊妫撮幍锟介張澶庣箥鐞涘瞼娈慳ctivity
    public void exit() {

        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
    public void setMainActivity(Context mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Context getMainActivity() {
        return mainActivity;
    }
    public boolean clearAct() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        mainActivity = null;
        return true;
    }
}
