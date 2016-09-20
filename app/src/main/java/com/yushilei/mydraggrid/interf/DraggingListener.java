package com.yushilei.mydraggrid.interf;

/**
 * @author by  yushilei.
 * @time 2016/9/20 -14:34.
 * @Desc
 */
public interface DraggingListener {
    /**
     * 拖拽中时，返回的当前拖拽的坐标
     */
    void dragging(float x, float y);

    void dragEnd();
}
