package com.shibei.deyangcitymanager.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import com.google.gson.Gson;
import com.shibei.deyangcitymanager.BaseActivity;
import com.shibei.deyangcitymanager.MyApplication;
import com.shibei.deyangcitymanager.R;
import com.shibei.deyangcitymanager.URLS;
import com.shibei.deyangcitymanager.mode.PoliceInfo;
import com.shibei.deyangcitymanager.mode.TokenInfo;
import com.shibei.deyangcitymanager.mode.Version;
import com.shibei.deyangcitymanager.service.LocationService;
import com.shibei.deyangcitymanager.uitls.Out;
import com.shibei.deyangcitymanager.uitls.StatusBarUtil;
import com.shibei.deyangcitymanager.view.DialogCommon;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 启动页
 * 王莹
 */

public class LoadingActivity extends BaseActivity {

    Handler hd;
    String uid;
    private static LocationManager locationManager;
    int versioncode;
    PackageManager pm;
    PackageInfo pi;
    Context ct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_loading);
        StatusBarUtil.setStatusBarTranslucent(this, true);
//        x.image().
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ct=this;
        hd = new Handler();
        pm = getPackageManager();
        try {
            pi = pm.getPackageInfo(ct.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            versioncode=1;
            e.printStackTrace();
        }


        getNewVersion();
    }

    @Override
    protected void loadData() {





    }

    @Override
    public void onClick(View v) {

    }

    private void getToken() {


        OkHttpClient mOkHttpClient = new OkHttpClient();
        SharedPreferences sharedPreferences = getSharedPreferences("deyang", 0);

        String uname = sharedPreferences.getString("uname", "");
        String psw = sharedPreferences.getString("psw", "");
        ;
        RequestBody formBody = new FormBody.Builder()
                .add("username", uname)
                .add("password", psw)
                .build();
        Request request = new Request.Builder()
                .url(URLS.GET_TOKEN)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                hd.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JumpActWithNoData(LoginActivity.class);
                        finish();
                        ;
                    }
                }, 1500);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() == 200) {
                    Gson gs = new Gson();
                    TokenInfo tokenInfo = gs.fromJson(response.body().string(), TokenInfo.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("deyang", 0);
                    SharedPreferences.Editor ed = sharedPreferences.edit();
                    ed.putString("token", tokenInfo.getToken());
                    ed.commit();

                    getPolicInfo(uid);
                } else {
                    hd.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            JumpActWithNoData(LoginActivity.class);
                            finish();
                            ;
                        }
                    }, 1500);
                }


            }


        });


    }

    private void getPolicInfo(String police_id) {
        Out.out("请求信息:" + URLS.GET_POLICEINFO + police_id + "/");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        SharedPreferences sharedPreferences = getSharedPreferences("deyang", MODE_PRIVATE);
        String tokens = sharedPreferences.getString("token", "");

        Request request = new Request.Builder()
                .url(URLS.GET_POLICEINFO + police_id + "/?format=json")
//                .post(formBody)put("Authorization", "Token "+token)
                .addHeader("Authorization", "Token " + tokens)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hd.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JumpActWithNoData(LoginActivity.class);
                        finish();
                        ;
                    }
                }, 1500);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                String databody = response.body().string();
                Out.out("请求结果:" + databody);
                if (response.code() == 200) {
                    Gson gs = new Gson();
                    PoliceInfo policeInfo = gs.fromJson(databody, PoliceInfo.class);
                    MyApplication.setMyPliceinfo(policeInfo);
                    hd.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            JumpActWithNoData(MainActivity.class);
                            finish();
                            ;
                        }
                    }, 1500);


                } else {
                    hd.post(new Runnable() {
                        @Override
                        public void run() {
                            hd.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    JumpActWithNoData(LoginActivity.class);
                                    finish();
                                    ;
                                }
                            }, 1500);
                        }
                    });

                }


            }


        });
    }

    private void getNewVersion(){

        OkHttpClient mOkHttpClient = new OkHttpClient();

        ;
        Out.out("请求URL=="+URLS.GET_NEWVERSION);

        Request request = new Request.Builder()
                .url(URLS.GET_NEWVERSION)

                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hd.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     isOpenGPS();
                        ;
                    }
                }, 1500);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String body=response.body().string();

                if (response.code() == 200) {
                    Gson gs = new Gson();
                    final Version tokenInfo = gs.fromJson(body, Version.class);

                    final String xzdz=tokenInfo.getFile();
                    int newbbh= Integer.parseInt(tokenInfo.getLevel());
                    if (newbbh>versioncode){
                        hd.post(new Runnable() {
                            @Override
                            public void run() {
                                DialogCommon dialogCommon=new DialogCommon(
                                    LoadingActivity.this);
                                dialogCommon.setTitle("发现新版本："+tokenInfo.getVersion());
                                dialogCommon.setMessage(tokenInfo.getContent());
                                dialogCommon.setCancel("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                       isOpenGPS();
                                    }
                                });
                                dialogCommon.setConfirm("更新", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse(xzdz);
                                        intent.setData(content_url);
                                        startActivity(intent);
                                    }
                                });
                                dialogCommon.show();
                            }
                        });



                    }else{
                        isOpenGPS();

                    }

                } else {
                    hd.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isOpenGPS();
                            ;
                        }
                    }, 1500);
                }


            }


        });
    }



    private void isOpenGPS(){

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            hd.post(new Runnable() {
                @Override
                public void run() {
                    final   DialogCommon dc=new DialogCommon(
                        LoadingActivity.this);
                    dc.setTitle("温馨提示");
                    dc.setMessage("GPS未打卡，请立即打卡GPS设置~");
                    dc.setConfirm("前往设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dc.dismiss();
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            // 设置完成后返回到原来的界面
                            startActivityForResult(intent, 11);
                        }
                    });
                    dc.setCancel("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });

                    dc.show();
                }
            });

        }else{

            doGoon();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Out.out("requescode--"+requestCode);
        Out.out("resultcode=="+resultCode);
        if (requestCode==11){
            isOpenGPS();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private  void  doGoon(){
        uid = getSharedPreferences("deyang", MODE_PRIVATE).getString("uid", "");


        Intent i=new Intent();
        i.setClass(LoadingActivity.this, LocationService.class);
        startService(i);

        if (TextUtils.isEmpty(uid)) {
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    JumpActWithNoData(LoginActivity.class);
                    finish();
                    ;
                }
            }, 1500);
        } else {
            getToken();


        }
    }
}
