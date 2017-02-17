package com.ztiany.systembar.trans;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.ViewGroup;

import com.ztiany.systembar.R;
import com.ztiany.systembar.utils.AndroidVersion;
import com.ztiany.systembar.utils.ResourceUtil;
import com.ztiany.systembar.utils.SystemBarCompat;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class SwipeBackTestActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back);

        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setBackgroundDrawable(new ColorDrawable(ResourceUtil.getColor(R.color.colorAccent, this)));
            if (AndroidVersion.above(20)) {
                supportActionBar.setElevation(0);
            }
        }
 
        if (AndroidVersion.atLeast(19)) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.root);
            SystemBarCompat.setupStatusBarView(this, viewGroup, ResourceUtil.getColor(R.color.colorAccent, this));
        }
    }
}
