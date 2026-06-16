package com.lautung.smart.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class outerViewGroup extends ViewGroup {
    public outerViewGroup(Context context) {
        super(context);
    }

    public outerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public outerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public outerViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
