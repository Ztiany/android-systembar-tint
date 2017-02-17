package com.ztiany.systembar.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * SystemBar工具类
 *
 * @author Ztiany
 *         Date :   2016-03-16 21:52
 */
public class SystemBarCompat {


    private SystemBarCompat() {
        throw new UnsupportedOperationException();
    }

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";

    ///////////////////////////////////////////////////////////////////////////
    //                                                  Kitkat
    ///////////////////////////////////////////////////////////////////////////

    public static void setTranslucentStatusOnKitkat(Activity activity) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            setTranslucent(activity, true, false);

        }
    }

    public static void setTranslucentNavigationOnKitkat(Activity activity) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            setTranslucent(activity, false, true);
        }
    }

    public static void setTranslucentOnKitkat(Activity activity) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            setTranslucent(activity, true, true);
        }
    }


    public static View setStatusBarColorOnKitkat(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            return setupStatusBarView(activity, decorView, color);
        }
        return null;
    }


    ///////////////////////////////////////////////////////////////////////////
    //                                               After L
    ///////////////////////////////////////////////////////////////////////////

    public static void setTranslucentStatusAfterKitkat(Activity activity) {
        if (!AndroidVersion.above(19)) {
            return;
        }
        setTranslucent(activity, true, false);
    }

    public static void setTranslucentNavigationAfterKitkat(Activity activity) {
        if (!AndroidVersion.above(19)) {
            return;
        }
        setTranslucent(activity, false, true);
    }


    public static void setTranslucentAfterKitkat(Activity activity) {
        if (!AndroidVersion.above(19)) {
            return;
        }
        setTranslucent(activity, true, true);
    }


    public static void setupStatusBarColorAfterLollipop(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT > 20) {
            activity.getWindow().setStatusBarColor(color);
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Common
    ///////////////////////////////////////////////////////////////////////////
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucent(Activity activity, boolean status, boolean navigation) {

        if (!AndroidVersion.atLeast(19)) {
            return;
        }

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (status) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (navigation) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 适用于4.4，在rootView中添加一个与StatusBar高度一样的View，用于对状态栏着色
     *
     * @param context  上下文
     * @param rootView 用于添加着色View的根View
     * @param color    着色
     * @return 被添加的View
     */
    public static View setupStatusBarView(Context context, ViewGroup rootView, @ColorInt int color) {
        View mStatusBarTintView = new View(context);
        mStatusBarTintView.setBackgroundColor(color);
        FrameLayout.LayoutParams mStatusBarParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT
                , getStatusBarHeight(context));
        mStatusBarParams.gravity = Gravity.TOP;
        mStatusBarTintView.setLayoutParams(mStatusBarParams);
        rootView.addView(mStatusBarTintView, 0);
        return mStatusBarTintView;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(STATUS_BAR_HEIGHT_RES_NAME, "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 获取NavigationBar高度
     *
     * @param context 上下文
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier(NAV_BAR_HEIGHT_RES_NAME, "dimen", "android");
        if (id > 0 && hasSoftKeys(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }


    /**
     * 获取ActionBar高度
     *
     * @param activity activity
     * @return ActionBar高度
     */
    public static int getActionBarHeight(Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }


    /**
     * 判断有没有导航栏，参考：https://github.com/roughike/BottomBar
     *
     * @param context 上下文
     * @return true表示有
     */
    public static boolean hasSoftKeys(Context context) {

        boolean hasSoftwareKeys = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display d = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            hasSoftwareKeys = (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            hasSoftwareKeys = !hasMenuKey && !hasBackKey;
        }
        return hasSoftwareKeys;
    }


}
