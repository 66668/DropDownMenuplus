package com.demo.filter.typeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.demo.R;
import com.demo.filter.adapter.BaseBaseAdapter;
import com.demo.filter.adapter.SimpleTextAdapter;
import com.demo.filter.util.CommonUtil;
import com.demo.filter.util.MLog;

import java.util.List;

/**
 * 创建 省-市-区 三级联动 自定义listView
 */
public class ThreeListView<FIRSTD, SECONDD, THIRDD> extends LinearLayout implements AdapterView.OnItemClickListener {

    private BaseBaseAdapter<FIRSTD> mFirstAdpater;
    private BaseBaseAdapter<SECONDD> mSecondAdapter;
    private BaseBaseAdapter<THIRDD> mThirdAdapter;
    private ListView lv_first;
    private ListView lv_second;
    private ListView lv_third;

    public ThreeListView(Context context) {
        this(context, null);
        init(context);
    }

    public ThreeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ThreeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.act_filter_three_list, this);

        //初始化控件
        lv_first = findViewById(R.id.lv_first);
        lv_second = findViewById(R.id.lv_second);
        lv_third = findViewById(R.id.lv_three);

        //设置单选
        lv_first.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv_second.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv_third.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //设置监听
        lv_first.setOnItemClickListener(this);
        lv_second.setOnItemClickListener(this);
        lv_third.setOnItemClickListener(this);
    }

    //listView的adapter
    public ThreeListView<FIRSTD, SECONDD, THIRDD> firstAdapter(SimpleTextAdapter<FIRSTD> firstAdapter) {
        mFirstAdpater = firstAdapter;
        lv_first.setAdapter(firstAdapter);
        return this;
    }

    public ThreeListView<FIRSTD, SECONDD, THIRDD> secondAdapter(SimpleTextAdapter<SECONDD> secondAdpater) {
        mSecondAdapter = secondAdpater;
        lv_second.setAdapter(secondAdpater);
        return this;
    }

    public ThreeListView<FIRSTD, SECONDD, THIRDD> thirdAdapter(SimpleTextAdapter<THIRDD> threeAdapter) {
        mThirdAdapter = threeAdapter;
        lv_third.setAdapter(threeAdapter);
        return this;
    }


    //设置默认选中
    public void setFirstList(List<FIRSTD> list, int checkedPosition) {
        mFirstAdpater.setList(list);
        //是否默认选中
        if (checkedPosition != -1) {
            //            lv_first.performItemClick(mFirstAdpater.getView(checkedPositoin, null, null), checkedPositoin, mFirstAdpater.getItemId(checkedPositoin));//调用此方法相当于点击.第一次进来时会触发重复加载.
            lv_first.setItemChecked(checkedPosition, true);
        }
    }

    public void setSecondList(List<SECONDD> list, int checkedPosition) {
        mSecondAdapter.setList(list);
        if (checkedPosition != -1) {
            lv_second.setItemChecked(checkedPosition, true);
        }
    }

    public void setThirdList(List<THIRDD> list, int checkedPosition) {
        mThirdAdapter.setList(list);

        if (checkedPosition != -1) {
            lv_third.setItemChecked(checkedPosition, true);
        }
    }

    //01省市区 点击监听 对象引用
    private OnFirstListItemClickListener<FIRSTD, SECONDD, THIRDD> mOnFirstListItemClickListener;
    private OnSecondListItemClickListener<FIRSTD, SECONDD, THIRDD> mOnSecondListItemClickListener;
    private OnThirdListItemClickListener<FIRSTD, SECONDD, THIRDD> mOnThirdListItemClickListener;


    //02省市区 点击监听 接口对象
    public interface OnFirstListItemClickListener<FIRSTD, SECONDD, THIRDD> {
        //链式调用 省的监听后跟市的数据
        List<SECONDD> onFistListItemClick(FIRSTD firstBean, int position);
    }

    public interface OnSecondListItemClickListener<FIRSTD, SECONDD, THIRDD> {
        //链式调用 市的监听后跟区的数据
        List<THIRDD> onSecondListItemClick(FIRSTD firstBean, SECONDD secondBean, int position);
    }

    public interface OnThirdListItemClickListener<FIRSTD, SECONDD, THIRDD> {
        //区的调用后没有调用
        void onThreelistItemClick(FIRSTD firstBean, SECONDD secondBean, THIRDD thirdBean);
    }

    //03省市区 点击监听 外部调用
    public ThreeListView<FIRSTD, SECONDD, THIRDD> onFirstListItemClickListener(OnFirstListItemClickListener<FIRSTD, SECONDD, THIRDD> mOnFirstListItemClickListener) {
        this.mOnFirstListItemClickListener = mOnFirstListItemClickListener;
        return this;
    }

    public ThreeListView<FIRSTD, SECONDD, THIRDD> onSecondListItemClickListener(OnSecondListItemClickListener<FIRSTD, SECONDD, THIRDD> mOnSecondListItemClickListener) {
        this.mOnSecondListItemClickListener = mOnSecondListItemClickListener;
        return this;
    }

    public ThreeListView<FIRSTD, SECONDD, THIRDD> onThirdListItemClickListener(OnThirdListItemClickListener<FIRSTD, SECONDD, THIRDD> mOnThirdListItemClickListener) {
        this.mOnThirdListItemClickListener = mOnThirdListItemClickListener;
        return this;
    }

    //
    public ListView getFirstListView() {
        return lv_first;
    }

    public ListView getSecondListView() {
        return lv_second;
    }

    public ListView getThirdListView() {
        return lv_third;
    }

    //========================点击事件===================================

    private int mFirstListLastPosition;
    private int msecondListLastPosition;
    private int mThirdListLastPosition;

    //记录最后选择的位置，用于和新位置postion比较使用
    private int mFistListLastCheckedPosition;
    private int msecondListLastCheckedPosition;
    private int mThirdListLastCheckedPosition;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //0.8s内没反应，返回
        if (CommonUtil.isFastDoubleClick()) {
            return;
        }

        //没初始化，返回
        if (mFirstAdpater == null || mSecondAdapter == null || mThirdAdapter == null) {
            MLog.d("有adapter是空");
            return;
        }

        if (parent == lv_first) {//省view监听
            mFirstListLastPosition = position;
            MLog.d("省--");
            if (mOnFirstListItemClickListener != null) {
                //根据position切换省份
                FIRSTD proviceBean = mFirstAdpater.getItem(position);
                //更换新省份下的市列表
                List<SECONDD> cityList = mOnFirstListItemClickListener.onFistListItemClick(proviceBean, position);

                mSecondAdapter.setList(cityList);
                //list为空
                if (CommonUtil.isEmpty(cityList)) {
                    //当前点的就是这个条目
                    mFistListLastCheckedPosition = -1;
                }
            }

            lv_second.setItemChecked(msecondListLastPosition, mFistListLastCheckedPosition == position);

        } else if (parent == lv_second) {//市view点击监听

            msecondListLastPosition = position;
            //标记一下选中省份的itemview位置
            mFistListLastCheckedPosition = mFirstListLastPosition;


            if (mOnSecondListItemClickListener != null) {
                //根据position切换市
                SECONDD cityBean = mSecondAdapter.getItem(msecondListLastPosition);

                //再次调用省的bean,供点击获取实用
                FIRSTD proviceBean = mFirstAdpater.getItem(mFistListLastCheckedPosition);

                List<THIRDD> areaBeanList = mOnSecondListItemClickListener.onSecondListItemClick(proviceBean, cityBean, msecondListLastPosition);

                mThirdAdapter.setList(areaBeanList);
                if (CommonUtil.isEmpty(areaBeanList)) {
                    //当前点的就是这个条目
                    msecondListLastCheckedPosition = -1;
                }
            }

            lv_third.setItemChecked(mThirdListLastPosition, msecondListLastCheckedPosition == position);

        } else if (parent == lv_third) {//区view点击监听

            mThirdListLastPosition = position;
            //标记一下选中市的itemview位置
            msecondListLastCheckedPosition = msecondListLastPosition;

            //根据postion获取区的bean
            THIRDD areaBean = mThirdAdapter.getItem(mThirdListLastPosition);
            //还需要省 市的bean,供点击调用
            FIRSTD proviceBean = mFirstAdpater.getItem(mFistListLastCheckedPosition);
            SECONDD cityBean = mSecondAdapter.getItem(msecondListLastCheckedPosition);

            if (mOnThirdListItemClickListener != null) {
                mOnThirdListItemClickListener.onThreelistItemClick(proviceBean, cityBean, areaBean);
            }
        }
    }


}
