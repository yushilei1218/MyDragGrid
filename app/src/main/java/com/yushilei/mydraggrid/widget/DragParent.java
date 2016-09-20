package com.yushilei.mydraggrid.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yushilei.mydraggrid.interf.DraggingListener;
import com.yushilei.mydraggrid.interf.PreDragListener;

/**
 * @author by  yushilei.
 * @time 2016/9/20 -14:27.
 * @Desc
 */
public class DragParent extends RelativeLayout implements PreDragListener {

    private ImageView mImg;//在DragParent 跟随拖拽的img
    DraggingListener mListener;
    private Bitmap mDrawingCache;//mImg 使用的从item获取到的图像

    public void setListener(DraggingListener listener) {
        mListener = listener;
    }

    public DragParent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isDragged) {
                    //当处于拖拽状态下 Move时
                    int width = mImg.getWidth();
                    int height = mImg.getHeight();
                    //设置 mImg位置
                    mImg.setX(x - width / 2);
                    mImg.setY(y - height / 2);
                    //把位置信息回调给 DragGridView  完成计算
                    if (mListener != null) {
                        mListener.dragging(x, y);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isDragged) {
                    //把拖拽结束 告知 DragGridView
                    if (mListener != null) {
                        mListener.dragEnd();
                    }
                    //移除 拖拽的img
                    removeView(mImg);
                    //释放Bitmap
                    mDrawingCache.recycle();
                    //重置拖拽状态
                    isDragged = false;
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否处于拖拽状态
     */
    boolean isDragged;

    @Override
    public void preDrag(View view) {
        isDragged = true;
        float x = view.getX();
        float y = view.getY();

        view.buildDrawingCache();
        mDrawingCache = view.getDrawingCache();

        mImg = new ImageView(getContext());
        mImg.setImageBitmap(mDrawingCache);
        mImg.setBackgroundColor(Color.BLUE);
        mImg.setPadding(1, 1, 1, 1);
        mImg.setX(x);
        mImg.setY(y);

        addView(mImg);
    }
}
