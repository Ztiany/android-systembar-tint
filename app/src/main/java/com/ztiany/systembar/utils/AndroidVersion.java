package com.ztiany.systembar.utils;

import android.os.Build;

/**
 * 版本判断
 */
public class AndroidVersion {

    private AndroidVersion() {

    }

    /**
     * @param level 要求的版本
     * @return true when the caller API version is > level
     */
    public static boolean above(int level) {
        return Build.VERSION.SDK_INT > level;
    }

    /**
     * @param level 要求的版本
     * @return true when the caller API version >= level
     */
    public static boolean atLeast(int level) {
        return Build.VERSION.SDK_INT >= level;
    }

    /**
     * 当前系统版本  == level
     */
    public static boolean at(int level) {
        return Build.VERSION.SDK_INT == level;
    }

}
