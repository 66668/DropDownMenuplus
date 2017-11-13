package com.demo.filter.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


/**
 * 在代码中要打印log,就直接DebugUtil.debug(....).
 * 然后如果发布的时候,就直接把这个类的DEBUG 改成false,这样所有的log就不会再打印在控制台.
 */

public class MLog {
    public static boolean DEBUG = false;
    private static String TAG = "SJY";

    public static void init(boolean isDebug, String logTag) {
        DEBUG = isDebug;
        if (!TextUtils.isEmpty(logTag)) {
            TAG = logTag;
        }
    }

    //toast弹窗提示
    public static void toastLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

    //toast弹窗提示
    public static void ToastShort(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    //View的顶部Snackbar显示
    public static void SnackbarShow(@NonNull View view, String content) {
        Snackbar.make(view, "加载数据失败！", Snackbar.LENGTH_SHORT).show();//底部提示
    }

    // 自定义toast
//    public static void toastInMiddle(Context context, String str) {
//        LayoutInflater inflater = LayoutInflater.from(MyApplication.getInstance());
//        View view = inflater.inflate(R.layout.toast_in_middle, null);
//        TextView chapterNameTV = view.findViewById(R.id.toast);
//        chapterNameTV.setText(str);
//
//        Toast toast = new Toast(context);
//        toast.setGravity(Gravity.CENTER, 0, 50);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(view);
//        toast.show();
//    }

    private static boolean isCanLog() {
        return DEBUG;
    }

    public static String getLogcat(Object... message) {
        StringBuilder sb = new StringBuilder();

        if (message != null) {
            for (Object object : message) {
                sb.append("--->> ");
                sb.append(object);
            }
        }

        sb.append("<<---");
        return sb.toString();
    }

    public static void d(Object... message) {
        if (isCanLog()) {
            Log.d(TAG, getLogcat(message));
        }
    }

    public static void d(int message) {
        if (isCanLog()) {
            Log.d(TAG, getLogMessage(String.valueOf(message)));
        }
    }

    public static void i(Object... message) {
        if (isCanLog()) {
            Log.i(TAG, getLogcat(message));
        }
    }

    public static void i(int message) {
        if (isCanLog()) {
            Log.i(TAG, getLogMessage(String.valueOf(message)));
        }
    }

    public static void w(Object... message) {
        if (isCanLog()) {
            Log.w(TAG, getLogcat(message));
        }
    }

    public static void w(int message) {
        if (isCanLog()) {
            Log.w(TAG, getLogMessage(String.valueOf(message)));
        }
    }

    public static void e(Object... message) {
        if (isCanLog()) {
            Log.e(TAG, getLogcat(message));
        }
    }

    public static void e(int message) {
        if (isCanLog()) {
            Log.e(TAG, getLogMessage(String.valueOf(message)));
        }
    }

    public static String getLogMessage(String message) {
        //        String timeFor24 = LFDateTimeUtils.getTimeForSS(new Date(System.currentTimeMillis()));
        return "time--".concat(System.currentTimeMillis() + "").concat("--").concat(message);
    }
}