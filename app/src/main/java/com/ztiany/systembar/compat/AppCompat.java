package com.ztiany.systembar.compat;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Build;

/**
 * Author Ztiany      <br/>
 * Email ztiany3@gmail.com      <br/>
 * Date 2016-03-16 21:32      <br/>
 * Description：App兼容性的处理
 */
public class AppCompat {

    private AppCompat() {

    }

    public static boolean afterLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean afterM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isKITKAT() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
    }

    public static boolean afterKITKAT() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }


    /**
     * @param level minimum API level version that has to support the device
     * @return true when the caller API version is at least level
     */
    public static boolean require(int level) {
        return Build.VERSION.SDK_INT >= level;
    }

    public static int getPrimaryDarkColorId(Activity activity) {
        if (afterLollipop()) {
            try {
                TypedArray typedArray = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimaryDark});
                return typedArray.getResourceId(0, -1);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return -1;
    }

    public static int getPrimaryColorId(Activity activity) {
        if (afterLollipop()) {
            try {
                TypedArray typedArray = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
                return typedArray.getResourceId(0, -1);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return -1;
    }

}
