package com.yushilei.mydraggrid.interf;

import android.view.View;

/**
 * @author by  yushilei.
 * @time 2016/9/20 -14:33.
 * @Desc
 */
public interface PreDragListener {
    /**
     * 某个DragGridView 的ItemView 进入拖拽状态回调
     */
    void preDrag(View view);
}
