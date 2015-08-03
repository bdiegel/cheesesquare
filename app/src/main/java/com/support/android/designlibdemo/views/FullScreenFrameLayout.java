package com.support.android.designlibdemo.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

public class FullScreenFrameLayout extends FrameLayout {

    private Rect windowInsets = new Rect();
    private Rect tempInsets = new Rect();

    private WindowInsets mLastInsets;

    public FullScreenFrameLayout(Context context) {
        this(context, null);
    }

    public FullScreenFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public FullScreenFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ViewCompat.getFitsSystemWindows(this)) {
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

                setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {

                        mLastInsets = insets;

                        return insets.consumeSystemWindowInsets();
                    }
                });
            }
        }
    }

//    @Override
//    protected boolean fitSystemWindows(Rect insets) {
//        super.fitSystemWindows(insets);
//        return false;
//    }


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
//            wi = wi.replaceSystemWindowInsets(wi.getSystemWindowInsetLeft(),
//                    wi.getSystemWindowInsetTop(), wi.getSystemWindowInsetRight(), wi.getSystemWindowInsetBottom());

            if (ViewCompat.getFitsSystemWindows(child)) {
                child.dispatchApplyWindowInsets(wi);
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                lp.leftMargin = wi.getSystemWindowInsetLeft();
                lp.topMargin = wi.getSystemWindowInsetTop();
                lp.rightMargin = wi.getSystemWindowInsetRight();
                lp.bottomMargin = wi.getSystemWindowInsetBottom();
            }

        }
    }
}
