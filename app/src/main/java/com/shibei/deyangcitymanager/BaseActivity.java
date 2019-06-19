package com.shibei.deyangcitymanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.shibei.deyangcitymanager.uitls.Out;

/**
 * Created by wy250 on 2017/9/26.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    public FragmentManager manager;
    public Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getSupportFragmentManager();
        MyApplication.getInstance().addActivity(this);

        initView();
        loadData();
    }


    @SuppressWarnings("unchecked")
    public final <E extends View> E getViewWithClick(int id) {
        try {

            ((E) findViewById(id)).setOnClickListener(this);

            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            Out.out("没找到控件");
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    public final <E extends View> E getView(int id) {
        try {

            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            Out.out("没找到控件");
            throw ex;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }




    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        MyApplication.getInstance().addActivity(this);
//        initView();
//        loadData();
//    }



    public void JumpAct(Context ct, Class<?> cls) {

        Intent i = new Intent(ct, cls);
        startActivity(i);
    }

    public void JumpActWithNoData(Class<?> cls) {

        Intent i = new Intent(getApplicationContext(), cls);
        startActivity(i);
    }

    public void JumpActAsResult(Context ct, Class<?> cls, int code) {

        Intent i = new Intent(ct, cls);
        i.putExtra("requescode", code);

        startActivityForResult(i, code);
    }

    public SharedPreferences getMyShareperance() {
        SharedPreferences sp = getSharedPreferences(MyApplication.LOGIN_INFO,
                MODE_PRIVATE);
        return sp;
    }

    protected abstract void initView();

    protected abstract void loadData();

    @Override
    public void onClick(View v) {
    }
    /**
     * fragment切换
     *
     * @param from
     *            当前fragment
     * @param to
     *            目标fragment
     * @param content
     *            fragment容器id
     */
    public void switchContent(Fragment from, Fragment to, int content) {
        if (currentFragment != to) {
            currentFragment = to;
            // 先判断是否被add过
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                // manager.beginTransaction().hide(from).addToBackStack(null)
                // .add(content, to).commit();
                manager.beginTransaction().hide(from).add(content, to)
                        .commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                // manager.beginTransaction().hide(from).addToBackStack(null)
                // .show(to).commit();
                manager.beginTransaction().hide(from).show(to)
                        .commitAllowingStateLoss();
            }
        }
    }
    public  void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(
            Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public  boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

}
