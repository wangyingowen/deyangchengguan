package com.shibei.deyangcitymanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import com.shibei.deyangcitymanager.BaseActivity;
import com.shibei.deyangcitymanager.MyApplication;
import com.shibei.deyangcitymanager.R;
import com.shibei.deyangcitymanager.URLS;
import com.shibei.deyangcitymanager.mode.PoliceInfo;
import com.shibei.deyangcitymanager.mode.TokenInfo;
import com.shibei.deyangcitymanager.uitls.Out;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录页面<br>
 * 王莹<br>
 * 2017-10-11 15:01
 */
public class LoginActivity extends BaseActivity {
    EditText ed_uname, ed_psw;
    Button btn_comit;
    Handler hd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadData() {
        SharedPreferences sharedPreferences=getSharedPreferences("zhongba",MODE_PRIVATE);
        String unname=sharedPreferences.getString("uname","");
        String psw=sharedPreferences.getString("psw","");

        if (!TextUtils.isEmpty(unname)){
            ed_uname.setText(unname);
        }
        if (!TextUtils.isEmpty(psw)){
            ed_psw.setText(psw);
        }
    }

    @Override
    protected void initView() {
        hd=new Handler();
        ed_psw = getView(R.id.password_et);
        ed_uname = getView(R.id.username_et);
        btn_comit = getViewWithClick(R.id.login_btn);


    }

    @Override
    public void onClick(View v) {
        if (v == btn_comit) {
            if (TextUtils.isEmpty(ed_psw.getText().toString().trim()) || TextUtils
                .isEmpty(ed_uname.getText().toString().trim())) {
                Out.Toast( LoginActivity.this,"请输入用户名和密码!");
                return;
            }
            doLogin();
        }
    }

    private void doLogin() {
        OkHttpClient mOkHttpClient=new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", ed_uname.getText().toString().trim())
                .add("password",ed_psw.getText().toString().trim())

                .build();
        Request request = new Request.Builder()
                .url(URLS.GET_TOKEN)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hd.post(new Runnable() {
                    @Override
                    public void run() {
                        Out.toast("网络繁忙，请稍后再试试", LoginActivity.this);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int code=response.code();
                String body=response.body().string();
                Out.out("code===="+code);
                Out.out("body===="+body);
                if (code==200){
                    Gson gs=new Gson();

                    TokenInfo tokenInfo=gs.fromJson(body,TokenInfo.class);
                    SharedPreferences sharedPreferences=getSharedPreferences("zhongba",MODE_PRIVATE);
                    SharedPreferences.Editor ed=sharedPreferences.edit();
                    ed.putString("token",tokenInfo.getToken());
                    ed.putString("uid",tokenInfo.getPolice_id());
                    ed.putString("uname",ed_uname.getText().toString().trim());
                    ed.putString("psw",ed_psw.getText().toString().trim());
                    ed.commit();
                    getPolicInfo(tokenInfo.getPolice_id());
                }else {
                    hd.post(new Runnable() {
                        @Override
                        public void run() {
                            Out.toast("用户名或密码错误！", LoginActivity.this);
                        }
                    });
                }


            }


        });

    }

    private void getPolicInfo(String police_id) {

        OkHttpClient mOkHttpClient=new OkHttpClient();
        SharedPreferences sharedPreferences=getSharedPreferences("zhongba",MODE_PRIVATE);
     String tokens=sharedPreferences.getString("token","");

        Request request = new Request.Builder()
                .url(URLS.GET_POLICEINFO+police_id+"/?format=json")
//                .post(formBody)
                .addHeader("Authorization", "Token " + tokens)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hd.post(new Runnable() {
                    @Override
                    public void run() {
                        Out.toast("网络繁忙，请稍后再试试", LoginActivity.this);
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (response.code()==200){
                    Gson gs=new Gson();
                    PoliceInfo policeInfo=gs.fromJson(response.body().string(),PoliceInfo.class);
                    MyApplication.setMyPliceinfo(policeInfo);
                    JumpActWithNoData(MainActivity.class);
                    finish();;


                }else {
                    hd.post(new Runnable() {
                        @Override
                        public void run() {
                            Out.toast(response.message(),
                                LoginActivity.this);
                        }
                    });

                }


            }


        });
    }
}

