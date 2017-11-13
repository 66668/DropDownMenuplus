package com.demo.other;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.demo.filter.util.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjy on 2017/4/18.
 */

public class MyApplication extends Application {
    private static MyApplication MyApplication;
    private boolean isLogin = false;

    private List<Activity> listActOfALL = new ArrayList<Activity>();//退出app使用
    private List<Activity> listActOfSome = new ArrayList<Activity>();//关闭多个使用

    public static MyApplication getInstance() {
        return MyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication = this;

        //设置打印,正式打包，设为 false
        MLog.init(true, "SJY");//true

    }

    //是否登录
    public boolean isLogin(Context context) {
                return isLogin;

    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    /**
     * application管理所有activity,暂不用广播
     */

    public void addActOfAll(Activity activity) {
        listActOfALL.add(activity);
        Log.d("SJY", "Current Acitvity Size :" + getCurrentActivitySize());
    }

    public void removeOneActOfAll(Activity activity) {
        listActOfALL.remove(activity);
        activity.finish();
        Log.d("SJY", "Current Acitvity Size :" + getCurrentActivitySize());
    }

    /**
     * 退出程序
     */
    public void exit() {
        for (Activity activity : listActOfALL) {
            activity.finish();
        }
    }

    /**
     * act的数量
     *
     * @return
     */
    public int getCurrentActivitySize() {
        return listActOfALL.size();
    }

    /**
     * 管理多个界面使用,不同于 管理所有界面
     */

    public void addOneActOfSome(Activity activity) {
        listActOfSome.add(activity);
    }

    public void closeAllActOFSome() {
        for (Activity activity : listActOfSome) {
            activity.finish();
        }
        //清空数据
        listActOfSome.clear();
    }
}


