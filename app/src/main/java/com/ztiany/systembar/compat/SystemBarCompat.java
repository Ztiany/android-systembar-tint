package com.ztiany.systembar.compat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Method;

/**
 * Author Ztiany      <br/>
 * Email ztiany3@gmail.com      <br/>
 * Date 2016-03-16 21:52      <br/>
 * Description：状态栏颜色工具类
 */
public class SystemBarCompat {


    private static final String TAG = SystemBarCompat.class.getSimpleName();

    private static String sNavBarOverride;


    private SystemBarCompat() {

    }

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";


    public static void setTranslucentStatusForKitkat(Activity activity) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            setTranslucentStatusAfterKitkat(activity, true);
        }
    }

    public static void setTranslucentNavigationForKitkat(Activity activity) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            setTranslucentNavigationAfterKitkat(activity, true);
        }
    }

    public static void setTranslucentForKitkat(Activity activity) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            setTranslucentAfterKitkat(activity, true);
        }
    }

    public static View setStatusBarColorForKitkat(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            return setupStatusBarView(activity, decorView, color);
        }
        return null;
    }


    public static void setTranslucentStatusAfterKitkat(Activity activity, boolean on) {
        if (!AppCompat.afterKITKAT()) {
            return;
        }
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        @SuppressLint("InlinedApi")//已经用AppCompat做了判断
                int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public static void setTranslucentNavigationAfterKitkat(Activity activity, boolean on) {
        if (!AppCompat.afterKITKAT()) {
            return;
        }
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        @SuppressLint("InlinedApi")//已经用AppCompat做了判断
                int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @SuppressLint("InlinedApi")//已经用AppCompat做了判断
    public static void setupStatusBarColorAfterLollipop(Activity activity, @ColorInt int color) {
        if (AppCompat.afterLollipop()) {
            activity.getWindow().setStatusBarColor(color);
        }
    }

    @SuppressLint("InlinedApi")
    public static void setTranslucentAfterKitkat(Activity activity, boolean on) {
        if (!AppCompat.afterKITKAT()) {
            return;
        }
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


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


    private static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag (see static block)
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier(NAV_BAR_HEIGHT_RES_NAME, "dimen", "android");
        if (id > 0 && hasNavBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(STATUS_BAR_HEIGHT_RES_NAME, "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    static {
        // Android allows a system property to override the presence of the navigation bar.
        // Used by the emulator.
        // See https://github.com/android/platform_frameworks_base/blob/master/policy/src/com/android/internal/policy/impl/PhoneWindowManager.java#L1076
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                sNavBarOverride = null;
            }
        }
    }

}
