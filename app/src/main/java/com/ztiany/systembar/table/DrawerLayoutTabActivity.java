package com.ztiany.systembar.table;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.ztiany.systembar.R;
import com.ztiany.systembar.utils.SystemBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrawerLayoutTabActivity extends AppCompatActivity {


    @Bind(R.id.act_main_bottom_bar)
    AHBottomNavigation mAHBottomNavigation;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    @Bind(R.id.act_main_drawer_layout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;


    @Bind(R.id.act_main_appbar)
    AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_tab);
        ButterKnife.bind(this);
        setStatusBar();
        initToolbar();
        initBottomBar();
        initDrawer();
        setAppBarScrollBehavior();
    }

    private void setStatusBar() {
        //status bar color
        int color = ContextCompat.getColor(this, R.color.colorPrimaryDark);
        //status bar for api 19
        SystemBarCompat.setTranslucentStatusOnKitkat(this);
        SystemBarCompat.setStatusBarColorOnKitkat(this, color);
    }


    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);//显示actonBar左边的home按钮
            supportActionBar.setHomeButtonEnabled(true);//启动actonBar左边的的启用或禁用“home”按钮
        }
    }


    private void initBottomBar() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Tab1", R.drawable.sel_main_home, R.color.cyan);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Tab1", R.drawable.sel_main_design, R.color.gray);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Tab1", R.drawable.sel_main_show, R.color.green);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Tab1", R.drawable.sel_main_me, R.color.blue);

        mAHBottomNavigation.setDefaultBackgroundColor(Color.DKGRAY);
        mAHBottomNavigation.setForceTint(true);
        mAHBottomNavigation.addItem(item1);
        mAHBottomNavigation.addItem(item2);
        mAHBottomNavigation.addItem(item3);
        mAHBottomNavigation.addItem(item4);
        mAHBottomNavigation.setUseElevation(true);

    }


    private void initDrawer() {
        //ActionBarDrawerToggle实现了DrawerListener
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        //能将drawerLayout的展开和隐藏与actionbar的app 图标关联起来
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);

    }

    public static Intent getLaunchIntent(AppCompatActivity appCompatActivity) {
        return new Intent(appCompatActivity, DrawerLayoutTabActivity.class);
    }

    private void setAppBarScrollBehavior() {
        mAHBottomNavigation.post(new Runnable() {
            @Override
            public void run() {
                mAppBarLayout.addOnOffsetChangedListener(
                        new AppBarWithBottomBarScrollChecker(mAHBottomNavigation, mAppBarLayout.getMeasuredHeight()));

            }
        });
    }


}
