package com.ztiany.systembar.table;

import android.support.design.widget.AppBarLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationBehavior;

/**
 * Author Ztiany                   <br/>
 * Email ztiany3@gmail.com      <br/>
 * Date 2016-05-05 13:42      <br/>
 * Description：首页应该保证当ToolBar完全显示后才能显示BottomBar，ToolBar完全隐藏时才能隐藏BottomBar
 */
class AppBarWithBottomBarScrollChecker implements AppBarLayout.OnOffsetChangedListener {

    private AHBottomNavigation mAHBottomNavigation;
    private int mPreviousOffset;
    private static final int DIRECTION_UP = 0x01;
    private static final int DIRECTION_DOWN = 0x02;
    private static final int DIRECTION_NONE = 0x00;
    private int mCurrentDirection = DIRECTION_NONE;
    private final int mScrollRange;
    private final AHBottomNavigationBehavior<AHBottomNavigation> mBottomNavigationBehavior;

    AppBarWithBottomBarScrollChecker(AHBottomNavigation ahBottomNavigation, int offset) {
        mAHBottomNavigation = ahBottomNavigation;
        mAHBottomNavigation.setBehaviorTranslationEnabled(false);
        mBottomNavigationBehavior = AHBottomNavigationBehavior.from(mAHBottomNavigation);
        mScrollRange = offset;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int previousOffset = mPreviousOffset;
        calcDirection(previousOffset, verticalOffset);
        if (mCurrentDirection == DIRECTION_DOWN) {
            if (verticalOffset == 0) {
                mBottomNavigationBehavior.resetOffset(mAHBottomNavigation, true);
            }

        } else if (mCurrentDirection == DIRECTION_UP) {
            if (verticalOffset == -mScrollRange) {
                mBottomNavigationBehavior.hideView(mAHBottomNavigation, mScrollRange, true);
            }
        }
        mPreviousOffset = verticalOffset;
    }

    private void calcDirection(int previousOffset, int verticalOffset) {
        int offset = verticalOffset - previousOffset;
        if (offset < 0) {
            mCurrentDirection = DIRECTION_UP;
        } else if (offset > 0) {
            mCurrentDirection = DIRECTION_DOWN;
        }
    }
}
