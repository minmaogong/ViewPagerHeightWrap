package com.example.viewpagerheightwrap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewPager extends ViewPager {

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int maxHeight = 0;
        for (int i=0;i<getChildCount();i++) {
            View childView = getChildAt(i);
            //获取LayoutParam
            ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();

            //构建子view的MeasureSpec
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,paddingLeft+paddingRight,layoutParams.width);
            //getChildMeasureSpec 内部实现 ：MeasureSpec.makeMeasureSpec(resultSize, resultMode)
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,paddingTop+paddingBottom,layoutParams.height);
            //子view测量
            childView.measure(childWidthMeasureSpec,childHeightMeasureSpec);

            //获取子View的高度尺寸  把子View的高度设置成ViewPager的高度
            maxHeight = Math.max(maxHeight,childView.getMeasuredHeight());

        }
        //根据子View的高度，构建新的heightMeasureSpec
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight,MeasureSpec.EXACTLY);
        //将构建的新的高度代替父View默认的高度
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }
}
