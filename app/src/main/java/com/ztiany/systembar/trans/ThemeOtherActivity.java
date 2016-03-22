package com.ztiany.systembar.trans;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ztiany.systembar.R;

/**
 * @author Ztiany
 *         email 1169654504@qq.com & ztiany3@gmail.com
 *         date 2016-03-22 15:43
 *         description
 *         vsersion
 */
public class ThemeOtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_theme_other_status);
    }

}
