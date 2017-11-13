package com.demo.filter.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.lang.reflect.Field;


/**
 * 屏幕尺寸工具类
 *
 * dpi即每英寸多少像素
 * px代表像素点
 * dp最终设计使用的尺寸
 *
 * Created by sjy on 2017/4/28.
 */

public class DpUtils {

    /**
     * 获取屏幕宽的总像素px
     */
    public static int getwithPx(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);

        int width = metric.widthPixels;
        int hight = metric.heightPixels;
        float density = metric.density;
        float densityDpi = metric.densityDpi;
        Log.d("SJY", "dp宽=" + (width / density) + "--dp高=" + (hight / density) + "--densityDpi=" + densityDpi + "--density=" + density);
        return width;
    }

    /**
     * 获取屏幕高的总像素px
     *
     * @param context
     * @return
     */
    public static int getHeigthPx(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        int hight = metric.heightPixels;
        return hight;
    }

    /**
     * 获取屏幕宽的总dp
     */
    public static int getwithDp(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        float density = metric.density;
        return (int) (width / density);
    }

    /**
     * 获取屏幕高的总dp
     */
    public static int getheightDp(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        int hight = metric.heightPixels;
        float density = metric.density;
        return (int) (hight / density);
    }

    /**
     * 获取屏幕密度：屏幕长宽的分辨率，根据勾股定律求出对角线的像素数，再除以屏幕的尺寸，即可得densitydpi
     *
     * @param context
     * @return
     */
    public static float getdensityDpi(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        float densityDpi = metric.densityDpi;
        return densityDpi;
    }

    /**
     * 获取屏幕密度：每一英寸像素点个数
     *
     * @param context
     * @return
     */
    public static float getdensity(Context context) {
        //方式一
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        float density = metric.density;

        //方式二
        //        final float density = context.getResources().getDisplayMetrics().density;
        return density;
    }

    /**
     * 获取手机状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 38;//通常这个值会是38
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 将dp转成px,代码设置尺寸需要用到
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        //
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * density + 0.5f);
    }

    /**
     * 将px转成dp,代码设置尺寸需要用到
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics()) + 0.5F);
    }
    // 根据手机的分辨率将dp的单位转成px(像素)
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 根据手机的分辨率将px(像素)的单位转成dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 将px值转换为sp值
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    // 将sp值转换为px值
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    // 屏幕宽度（像素）
    public static  int getWindowWidth(Activity context){
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    // 屏幕高度（像素）
    public static int getWindowHeight(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static View infalte(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(layoutId, parent, false);

    }

}
