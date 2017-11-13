package com.demo.filter.interfaces;

/**
 * 选中item条件后的对tab的处理/或数据的处理
 */
public interface OnFilterDoneListener {
    void onFilterDone(int position, String positionTitle, String urlValue);
}