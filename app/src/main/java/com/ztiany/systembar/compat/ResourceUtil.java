package com.ztiany.systembar.compat;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;

/**
 * Author Ztiany      <br/>
 * Email ztiany3@gmail.com      <br/>
 * Date 2016-03-16 21:36      <br/>
 * Description：资源工具类
 */
public class ResourceUtil {

    private ResourceUtil(){}


    public static int getColor(int id, Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return activity.getResources().getColor(id);
        } else {
            return activity.getResources().getColor(id, activity.getTheme());
        }
    }


    public static String getString(Context context, @StringRes int id) {
        return context.getString(id);
    }

    public static String[] getStringArray(Context context, @ArrayRes int id) {
        return context.getResources().getStringArray(id);
    }


    public static int[] getIntArray(Context context, @ArrayRes int id) {
        return context.getResources().getIntArray(id);
    }


}
