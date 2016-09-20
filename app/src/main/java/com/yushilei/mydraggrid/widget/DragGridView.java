package com.yushilei.mydraggrid.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.yushilei.mydraggrid.R;
import com.yushilei.mydraggrid.adapter.DragAdapter;
import com.yushilei.mydraggrid.interf.DraggingListener;

/**
 * @author by  yushilei.
 * @time 2016/9/20 -14:27.
 * @Desc
 */
public class DragGridView extends RecyclerView implements DraggingListener {
    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int msHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, msHeight);
    }

    /**
     * 计算当前拖拽点 位于 DragGridView 那个item上
     */
    public int nearToPosition(float x, float y) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (x >= view.getX() && x <= view.getX() + view.getWidth()
                    && y >= view.getY() && y <= view.getY() + view.getHeight()) {
                return ((DragAdapter) getAdapter()).mBeans.indexOf(view.getTag(R.id.id_bean_));
            }
        }
        return -1;
    }

    //***接口回调 DragParent 处于拖拽中，把其拖拽点 传给DragGridView
    @Override
    public void dragging(float x, float y) {
        //获取拖拽点所在的item位置
        int position = nearToPosition(x, y);
        DragAdapter adapter = (DragAdapter) getAdapter();
        //或者当前被拖拽数据Bean所在mBeans的位置
        int dragBeanIndex = adapter.dragBeanIndex();
        //如果 二个位置不一致，则说明原来的数据Bean需要被remove后从新insert到指定位置
        if (position != dragBeanIndex && position != -1) {

            adapter.mBeans.remove(dragBeanIndex);
            adapter.notifyItemRemoved(dragBeanIndex);

            adapter.mBeans.add(position, adapter.draggedBean);
            adapter.notifyItemInserted(position);
        }
    }

    /**
     * 拖拽结束 调用 DragAdapter 的dragEnd().
     */
    @Override
    public void dragEnd() {
        DragAdapter adapter = (DragAdapter) getAdapter();
        adapter.dragEnd();
    }
}
