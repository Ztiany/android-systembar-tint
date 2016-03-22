package com.ztiany.systembar.trans;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ztiany.systembar.R;
import com.ztiany.systembar.compat.AppCompat;
import com.ztiany.systembar.compat.ResourceUtil;
import com.ztiany.systembar.compat.SystemBarCompat;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class SwipeBackTestActivity extends SwipeBackActivity {
    private static final String TAG = SwipeBackTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back);
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setBackgroundDrawable(new ColorDrawable(ResourceUtil.getColor(R.color.colorAccent, this)));
            if (AppCompat.afterLollipop()) {
                supportActionBar.setElevation(0);
            }
        }

//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        WindowManager.LayoutParams attributes = getWindow().getAttributes();



        if (AppCompat.afterKITKAT()) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.root);
            SystemBarCompat.setupStatusBarView(this, viewGroup, ResourceUtil.getColor(R.color.colorAccent, this));
        }
    }
}
