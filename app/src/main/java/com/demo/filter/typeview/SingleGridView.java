package com.demo.filter.typeview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.demo.filter.adapter.BaseBaseAdapter;
import com.demo.filter.util.CommonUtil;
import com.demo.filter.util.DpUtils;

import java.util.List;

/**
 * 单gridView的自定义
 */
public class SingleGridView<DATA> extends GridView implements AdapterView.OnItemClickListener {

    private BaseBaseAdapter<DATA> mAdapter;


    public SingleGridView(Context context) {
        this(context, null);
    }

    public SingleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SingleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        setSelector(new ColorDrawable(Color.TRANSPARENT));
        setNumColumns(3);
        setBackgroundColor(Color.WHITE);
        setSmoothScrollbarEnabled(false);


        int dp = DpUtils.dpToPx(context, 15);

        setVerticalSpacing(dp);
        setHorizontalSpacing(dp);
        setPadding(dp, dp, dp, dp);

        setOnItemClickListener(this);
    }

    public SingleGridView<DATA> adapter(BaseBaseAdapter<DATA> adapter) {
        this.mAdapter = adapter;
        setAdapter(adapter);
        return this;
    }


    public void setList(List<DATA> list, int checkedPositoin) {
        mAdapter.setList(list);

        if (checkedPositoin != -1) {
            setItemChecked(checkedPositoin, true);
        }
    }

    //接口回调
    private OnSingleGridViewClickListener<DATA> mOnSingleGridViewClickListener;

    public interface OnSingleGridViewClickListener<DATA> {
        void onSingleGridViewCallback(DATA item);
    }
    public SingleGridView<DATA> onSingleGridViewClick(OnSingleGridViewClickListener<DATA> onItemClickListener) {
        this.mOnSingleGridViewClickListener = onItemClickListener;
        return this;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (CommonUtil.isFastDoubleClick()) {
            return;
        }

        DATA item = mAdapter.getItem(position);

        if (mOnSingleGridViewClickListener != null) {
            mOnSingleGridViewClickListener.onSingleGridViewCallback(item);
        }
    }


}
