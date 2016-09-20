package com.yushilei.mydraggrid.bean;

/**
 * @author by  yushilei.
 * @time 2016/9/20 -14:29.
 * @Desc
 */
public class Bean {
    String str;//item+i
    boolean isDragged;//是否处于拖拽状态
    int rid;//该Bean对应的 图片资源id
    boolean isLongClicked;

    public Bean(String str, int rid) {
        this.str = str;
        this.rid = rid;
    }

    public boolean isLongClicked() {
        return isLongClicked;
    }

    public void setIsLongClicked(boolean isLongClicked) {
        this.isLongClicked = isLongClicked;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isDragged() {
        return isDragged;
    }

    public void setIsDragged(boolean isDragged) {
        this.isDragged = isDragged;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
