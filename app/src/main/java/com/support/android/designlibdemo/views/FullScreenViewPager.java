package com.support.android.designlibdemo.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;

public class FullScreenViewPager extends ViewPager {

    private WindowInsets mLastInsets;

    public FullScreenViewPager(Context context) {
        this(context, null);
    }

    @SuppressLint("NewApi")
    public FullScreenViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ViewCompat.getFitsSystemWindows(this)) {
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

                setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {

                        mLastInsets = insets;

                        requestLayout();

                        return insets.consumeSystemWindowInsets();
                    }
                });
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final boolean applyInsets = mLastInsets != null && ViewCompat.getFitsSystemWindows(this);

        if (!applyInsets) {
            return;
        }

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            WindowInsets wi = mLastInsets;

            if (ViewCompat.getFitsSystemWindows(child)) {
                child.dispatchApplyWindowInsets(wi);
            } else {
                if (child.getLayoutParams() instanceof  MarginLayoutParams) {
                    final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                    lp.leftMargin = wi.getSystemWindowInsetLeft();
                    lp.topMargin = wi.getSystemWindowInsetTop();
                    lp.rightMargin = wi.getSystemWindowInsetRight();
                    lp.bottomMargin = wi.getSystemWindowInsetBottom();
                }
            }

        }
    }
}
