package com.demo.filter.util;

import android.text.TextUtils;

import com.demo.demobean.FilterBean;


/**
 * 点击筛选数据的保存
 */
public class FilterUtils {

    private volatile static FilterUtils filterUtils;

    private FilterUtils() {
    }

    public static FilterUtils instance() {
        if (filterUtils == null) {
            synchronized (FilterUtils.class) {
                if (filterUtils == null) {
                    filterUtils = new FilterUtils();
                }
            }
        }
        return filterUtils;
    }
    //所有的某个位置的标题
    public int position;
    public String positionTitle;

    public String singleListPosition;

    //双listView数据
    public String doubleListLeft;
    public String doubleListRight;

    //三个Listview数据（省市区）
    public String ThreeListfirst;
    public String ThreeListsecond;
    public String ThreeListThrid;
    //三个Listview数据id（省市区）
    public int ThreeListfirstID;
    public int ThreeListsecondID;
    public int ThreeListThridID;

    //5个gridView的数值
    public FilterBean.InstitutionObject multiGirdOne;
    public FilterBean.PlaceProperty multiGirdTwo;
    public FilterBean.Bed multiGirdThree;
    public FilterBean.InstitutionPlace multiGirdFour;
    public FilterBean.InstitutionFeature multiGirdFive;

    //单个grid数值
    public String singleGridPosition;

    //两个gridView数值
    public String doubleGridTop;
    public String doubleGridBottom;



    /**
     * 查看打印
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        //单listView
        if (!TextUtils.isEmpty(singleListPosition)) {
            buffer.append("singleListPosition=");
            buffer.append(singleListPosition);
            buffer.append("\n");
        }

        //双listView数据
        if (!TextUtils.isEmpty(doubleListLeft)) {
            buffer.append("doubleListLeft=");
            buffer.append(doubleListLeft);
            buffer.append("\n");
        }

        if (!TextUtils.isEmpty(doubleListRight)) {
            buffer.append("doubleListRight=");
            buffer.append(doubleListRight);
            buffer.append("\n");
        }

        //三个Listview数据
        if (!TextUtils.isEmpty(ThreeListfirst)) {
            buffer.append("ThreeListfirst=");
            buffer.append(ThreeListfirst);
            buffer.append("\n");
        }
        if (!TextUtils.isEmpty(ThreeListsecond)) {
            buffer.append("ThreeListsecond=");
            buffer.append(ThreeListsecond);
            buffer.append("\n");
        }
        if (!TextUtils.isEmpty(ThreeListThrid)) {
            buffer.append("ThreeListThrid=");
            buffer.append(ThreeListThrid);
            buffer.append("\n");
        }

        //三个Listview数据id
        if (ThreeListfirstID >= 0) {
            buffer.append("ThreeListfirstID=");
            buffer.append(ThreeListfirstID);
            buffer.append("\n");
        }
        if (ThreeListsecondID >= 0) {
            buffer.append("ThreeListsecondID=");
            buffer.append(ThreeListsecondID);
            buffer.append("\n");
        }
        if (ThreeListThridID >= 0) {
            buffer.append("ThreeListThridID=");
            buffer.append(ThreeListThridID);
            buffer.append("\n");
        }


        //单个gridView
        if (!TextUtils.isEmpty(singleGridPosition)) {
            buffer.append("singleGridPosition=");
            buffer.append(singleGridPosition);
            buffer.append("\n");
        }

        //两个gridView
        if (!TextUtils.isEmpty(doubleGridTop)) {
            buffer.append("doubleGridTop=");
            buffer.append(doubleGridTop);
            buffer.append("\n");
        }

        if (!TextUtils.isEmpty(doubleGridBottom)) {
            buffer.append("doubleGridBottom=");
            buffer.append(doubleGridBottom);
            buffer.append("\n");
        }

        //multiGrid
        if (multiGirdOne != null) {
            buffer.append("multiGirdOne=");
            buffer.append(multiGirdOne.getType());
            buffer.append("\n");
        }
        if (multiGirdTwo != null) {
            buffer.append("multiGirdTwo=");
            buffer.append(multiGirdTwo.getName());
            buffer.append("\n");
        }
        if (multiGirdThree != null) {
            buffer.append("multiGirdThree=");
            buffer.append(multiGirdThree.getName());
            buffer.append("\n");
        }
        if (multiGirdFour != null) {
            buffer.append("multiGirdFour=");
            buffer.append(multiGirdFour.getType());
            buffer.append("\n");
        }
        if (multiGirdFive != null) {
            buffer.append("multiGirdFive=");
            buffer.append(multiGirdFive.getName());
            buffer.append("\n");
        }

        return buffer.toString();
    }

    public void clear() {
        filterUtils = null;
    }


}