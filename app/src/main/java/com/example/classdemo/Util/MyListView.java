package com.example.classdemo.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class MyListView extends ListView {
    private boolean isTop;
    private boolean isAll = true;
    private float current;
    private float y;
    private int pointItem;

    public MyListView(Context context) {
        super(context);
        init();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        setDivider(null);
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i == 0) {
                    View childAt = getChildAt(i);
                    if (childAt != null && (childAt.getTop() + childAt.getPaddingTop() >= 0)) {
                        isTop = true;
                    } else {
                        isTop = false;
                    }
                } else {
                    isTop = false;
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        current = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float X = ev.getX();
                y = ev.getY();
                pointItem = pointToPosition((int) X, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                float move = current - y;
                int tran = (int) (move * 0.025f);
                if (isTop) {
                    if (move < 200 || move > 0) {
                        setDividerHeight(tran);
                    }
                } else {
                    for (int i = 0; i < getChildCount(); i++) {
                        int r = i;
                        View childAt = getChildAt(i);
                        if (i == pointItem) {
                            r = pointItem;
                        }
                        childAt.setTranslationY(tran * r);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                setDividerHeight(0);
                for (int i = 0; i < getChildCount(); i++) {
                    getChildAt(i).setTranslationY(0);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
