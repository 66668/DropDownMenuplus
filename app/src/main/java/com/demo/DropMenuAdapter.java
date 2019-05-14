package com.demo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.demo.demobean.AreaBean;
import com.demo.demobean.CityBean;
import com.demo.demobean.FilterBean;
import com.demo.demobean.InstitutionPriceBean;
import com.demo.demobean.ProvinceBean;
import com.demo.filter.FilterTypeBean;
import com.demo.filter.adapter.MenuAdapter;
import com.demo.filter.adapter.SimpleTextAdapter;
import com.demo.filter.interfaces.OnFilterDoneListener;
import com.demo.filter.typeview.DoubleGridView;
import com.demo.filter.typeview.DoubleListView;
import com.demo.filter.typeview.MultiGridView;
import com.demo.filter.typeview.SingleGridView;
import com.demo.filter.typeview.SingleListView;
import com.demo.filter.typeview.ThreeListView;
import com.demo.filter.util.CommonUtil;
import com.demo.filter.util.DpUtils;
import com.demo.filter.util.FilterUtils;
import com.demo.filter.util.MLog;
import com.demo.filter.view.FilterCheckedTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构筛选 适配
 */

public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;//筛选 标题

    private FilterBean filterBean;//筛选 总数据源

    private List<String> sortListData;//排序 数据源
    private List<InstitutionPriceBean> priceListData;//价格 数据源
    private List<ProvinceBean> provinceBeanList;//省份 数据源(包含 市 区数据)

    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }
        return DpUtils.dpToPx(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {
            case 0://省市区 三级联动

//                view = createDoubleListView();//前辈的省市两级联动

                view = createThreeListView();
                break;
            case 1://多筛选
//                view = createDoubleGrid();//前辈的两个条件的筛选
                view = createMutiGrid();
                break;
            case 2://价格
                view = createSingleGridView();
                break;
            case 3://排序

                view = createSingleListView();
                break;
        }

        return view;
    }

    /**
     * 适配传值
     */
    public void setFilterBean(FilterBean filterBean) {
        this.filterBean = filterBean;
        provinceBeanList = filterBean.getProvince();
        priceListData = filterBean.getPrice();
    }

    public void setSortListData(List<String> sortListData) {
        this.sortListData = sortListData;
    }


    /**
     * 回调监听
     */

    //01 省市区 接口回调
    public OnPlaceCallbackListener onPlaceCallbackListener;

    public interface OnPlaceCallbackListener {
        void onPlaceCallbackListener(int provinceId, int cityId, int areaId);//只需id，
    }

    public void setOnPlaceCallbackListener(OnPlaceCallbackListener onPlaceCallbackListener) {
        this.onPlaceCallbackListener = onPlaceCallbackListener;
    }

    //02
    public OnMultiFilterCallbackListener onMultiFilterCallbackListener;

    public interface OnMultiFilterCallbackListener {
        void onMultiFilterCallbackListener(int objId, int propertyId, int bedId, int typeId, String serviceId);
    }

    public void setOnMultiFilterCallbackListener(OnMultiFilterCallbackListener onMultiFilterCallbackListener) {
        this.onMultiFilterCallbackListener = onMultiFilterCallbackListener;
    }

    //03价格范围监听
    public OnPriceCallbackListener onPriceCallbackListener;

    //本类中调用该实例方法
    public interface OnPriceCallbackListener {
        void onPriceCallbackListener(InstitutionPriceBean item);
    }

    //act中调用
    public void setOnPriceCallbackListener(OnPriceCallbackListener onPriceCallbackListener) {
        this.onPriceCallbackListener = onPriceCallbackListener;
    }

    //04排序范围监听
    public OnSortCallbackListener onSortCallbackListener;

    public interface OnSortCallbackListener {
        void onSortCallbackListener(int item);
    }

    public void setOnSortCallbackListener(OnSortCallbackListener onSortCallbackListener) {
        this.onSortCallbackListener = onSortCallbackListener;
    }


    /**
     * 01 省 市 区三级筛选及点击监听
     * <p>
     * 由于返回的数据，每一条都有 不限且设置的id=0，数据机构是：省-市-区==》不限-不限-不限。所以，设置成当点击不限时，下一级的不限就不显示，直接返回
     * 比如，不限在省布局上，（触发的结果是要筛选全国数据），点击后返回，不会触发市的布局
     * 比如，选择某个省，市上点击不限，（结果是获取该省的数据），点击后返回，不会触发区的布局
     *
     * @return·
     */
    private View createThreeListView() {

        final ThreeListView<ProvinceBean, CityBean, AreaBean> mThreeListView = new ThreeListView<ProvinceBean, CityBean, AreaBean>(mContext);

        //创建adapter
        mThreeListView
                .firstAdapter(new SimpleTextAdapter<ProvinceBean>(null, mContext) {//省适配
                    @Override
                    public String provideText(ProvinceBean provinceBean) {
                        return provinceBean.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10));
                    }
                })
                .secondAdapter(new SimpleTextAdapter<CityBean>(null, mContext) {//市 适配
                    @Override
                    public String provideText(CityBean cityBean) {
                        return cityBean.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .thirdAdapter(new SimpleTextAdapter<AreaBean>(null, mContext) {//区适配
                    @Override
                    public String provideText(AreaBean areaBean) {
                        return areaBean.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onFirstListItemClickListener(new ThreeListView.OnFirstListItemClickListener<ProvinceBean, CityBean, AreaBean>() {//省listView点击监听
                    @Override
                    public List<CityBean> onFistListItemClick(ProvinceBean itemBean, int position) {
                        List<CityBean> cityBeanList = itemBean.getChild();

                        mThreeListView.getThirdListView().setVisibility(View.INVISIBLE);
                        mThreeListView.getSecondListView().setVisibility(View.VISIBLE);
                        //没有下一级数据，就显示
                        if (CommonUtil.isEmpty(cityBeanList)) {

                            FilterUtils.instance().ThreeListfirst = itemBean.getName();

                            FilterUtils.instance().position = 0;//显示在第一个筛选标题的位置，不可改
                            FilterUtils.instance().positionTitle = itemBean.getName();

                            //触发返回
                            onPlaceCallbackListener.onPlaceCallbackListener(itemBean.getId(), 0, 0);//后台规定，不限的id=0
                            onFilterDone(0);//act回调
                        }
                        //点击 不限后，结束显示
                        if (itemBean.getId() == 0) {
                            FilterUtils.instance().ThreeListfirst = itemBean.getName();
                            FilterUtils.instance().position = 0;//显示在第一个筛选标题的位置，不可改
                            FilterUtils.instance().positionTitle = "全国";//筛选器现实的内容-显示全国名称

                            //触发返回
                            onFilterDone(0);//act回调
                            onPlaceCallbackListener.onPlaceCallbackListener(0, 0, 0);//后台规定，不限的id=0
                            //市 区 布局不显示
                            mThreeListView.getSecondListView().setVisibility(View.INVISIBLE);
                            mThreeListView.getThirdListView().setVisibility(View.INVISIBLE);
                        }
                        return cityBeanList;
                    }
                })
                .onSecondListItemClickListener(new ThreeListView.OnSecondListItemClickListener<ProvinceBean, CityBean, AreaBean>() {//市的list点击监听

                    //点击市listView监听
                    @Override
                    public List<AreaBean> onSecondListItemClick(ProvinceBean firstBean, CityBean secondBean, int position) {

                        //获取区的list
                        List<AreaBean> areaBeanList = secondBean.getChild();
                        mThreeListView.getThirdListView().setVisibility(View.VISIBLE);

                        //没有下一级数据，就结束显示
                        if (CommonUtil.isEmpty(areaBeanList)) {

                            FilterUtils.instance().ThreeListfirst = firstBean.getName();
                            FilterUtils.instance().ThreeListsecond = secondBean.getName();

                            FilterUtils.instance().position = 0;//显示在第一个筛选标题的位置，不可改
                            FilterUtils.instance().positionTitle = secondBean.getName();//筛选器现实的内容-显示全国名称


                            onFilterDone(0);//act回调
                        }

                        //点击 不限后，结束显示
                        if (secondBean.getId() == 0) {
                            FilterUtils.instance().ThreeListfirst = firstBean.getName();
                            FilterUtils.instance().ThreeListsecond = secondBean.getName();
                            FilterUtils.instance().position = 0;//显示在第一个筛选标题的位置，不可改
                            FilterUtils.instance().positionTitle = firstBean.getName();//筛选器现实的内容-显示省名称

                            //触发返回
                            onFilterDone(0);//act回调
                            onPlaceCallbackListener.onPlaceCallbackListener(firstBean.getId(), 0, 0);//后台规定，不限的id=0

                            //区的布局不显示
                            mThreeListView.getThirdListView().setVisibility(View.INVISIBLE);
                        }

                        return areaBeanList;
                    }
                })
                .onThirdListItemClickListener(new ThreeListView.OnThirdListItemClickListener<ProvinceBean, CityBean, AreaBean>() {
                    @Override
                    public void onThreelistItemClick(ProvinceBean firstBean, CityBean secondBean, AreaBean thirdBean) {
                        //后台筛选需要传省市区的三个id,需要获取后保存

                        if (thirdBean.getId() == 0) {//若选中不限区，这筛选器的title显示市的名称
                            FilterUtils.instance().ThreeListfirst = firstBean.getName();
                            FilterUtils.instance().ThreeListsecond = secondBean.getName();
                            FilterUtils.instance().ThreeListThrid = thirdBean.getName();

                            FilterUtils.instance().position = 0;//显示在第一个筛选标题的位置，不可改
                            FilterUtils.instance().positionTitle = secondBean.getName();

                            //触发返回
                            onPlaceCallbackListener.onPlaceCallbackListener(firstBean.getId(), secondBean.getId(), thirdBean.getId());
                            onFilterDone(0);
                        } else {
                            FilterUtils.instance().ThreeListfirst = firstBean.getName();
                            FilterUtils.instance().ThreeListsecond = secondBean.getName();
                            FilterUtils.instance().ThreeListThrid = thirdBean.getName();

                            FilterUtils.instance().position = 0;//显示在第一个筛选标题的位置，不可改
                            FilterUtils.instance().positionTitle = thirdBean.getName();

                            //触发返回
                            onPlaceCallbackListener.onPlaceCallbackListener(firstBean.getId(), secondBean.getId(), thirdBean.getId());
                            onFilterDone(0);
                        }
                    }
                });

        //初始化选中.
        mThreeListView.setFirstList(provinceBeanList, 0);
        mThreeListView.setSecondList(provinceBeanList.get(0).getChild(), 0);
        mThreeListView.setThirdList(provinceBeanList.get(0).getChild().get(0).getChild(), 0);
        mThreeListView.getFirstListView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.b_c_fafafa));
        mThreeListView.getSecondListView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.b_c_fafafa));
        mThreeListView.getThirdListView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.b_c_fafafa));

        return mThreeListView;
    }



    /**
     * 02 多筛选设置（5个grid数据）
     */
    private View createMutiGrid() {

        MultiGridView<FilterBean.InstitutionObject, FilterBean.PlaceProperty, FilterBean.Bed, FilterBean.InstitutionPlace, FilterBean.InstitutionFeature> multiGridView =
                new MultiGridView(mContext);

        multiGridView.setFilterBean(filterBean)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build()
                .setOnMultiGridViewClick(new MultiGridView.OnMultiGridViewCallbackListener() {
                    @Override
                    public void onSureClickListener(int objId, int propertyId, int bedId, int typeId, String serviceId) {
                        //筛选选中值处理
                        MLog.e("MultiGridView回调给==DropMenuAdapter的结果==", objId, propertyId, bedId, typeId, serviceId);
                        onMultiFilterCallbackListener.onMultiFilterCallbackListener(objId, propertyId, bedId, typeId, serviceId);//返回act处理
                        onFilterDone(1);
                    }
                });

        return multiGridView;
    }

    /**
     * 03筛选-价格
     *
     * @return
     */
    private View createSingleGridView() {

        SingleGridView<InstitutionPriceBean> singleGridView = new SingleGridView<InstitutionPriceBean>(mContext)
                .adapter(new SimpleTextAdapter<InstitutionPriceBean>(null, mContext) {
                    @Override
                    public String provideText(InstitutionPriceBean s) {
                        MLog.d("createSingleGridView--item点击" + s.getName());
                        return s.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(0, DpUtils.dpToPx(context, 10), 0, DpUtils.dpToPx(context, 10));
                        checkedTextView.setGravity(Gravity.CENTER);
                        checkedTextView.setBackgroundResource(R.drawable.filter_btn_reset_style);
                    }
                })
                .onSingleGridViewClick(new SingleGridView.OnSingleGridViewClickListener<InstitutionPriceBean>() {
                    @Override
                    public void onSingleGridViewCallback(InstitutionPriceBean item) {
                        //获取到点击的数据
                        // ToastUtil.ToastShort(mContext, "item响应 name=" + item.getName() + "--id=" + item.getId());
                        onPriceCallbackListener.onPriceCallbackListener(item);//回调到act中操作
                        onFilterDone(2);
                    }
                });

        //初始化选中
        singleGridView.setList(priceListData, 0);

        return singleGridView;
    }

    /**
     * 04 筛选--排序
     *
     * @return
     */
    private View createSingleListView() {

        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .setSingleListAdapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = DpUtils.dpToPx(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onSingleListClick(new SingleListView.OnSingleListClickListener<String>() {
                    @Override
                    public void onSingleListCallback(String item) {
                        int sortId = -1;
                        if (item.contains("从高到底")) {
                            sortId = 1;
                        } else if (item.contains("从低到高")) {
                            sortId = 2;
                        } else if (item.contains("综合排序")) {
                            sortId = 0;
                        } else {
                            sortId = 0;
                        }
                        onSortCallbackListener.onSortCallbackListener(sortId);//回调到act中操作
                        onFilterDone(3);
                    }
                });

        //设置默认选项
        singleListView.setList(sortListData, 0);
        return singleListView;
    }


    /**
     * 每一块筛选按钮的act回调
     *
     * @param tabPosition
     */
    private void onFilterDone(int tabPosition) {
        if (onFilterDoneListener != null) {
            if (tabPosition == 0) {
                onFilterDoneListener.onFilterDone(0, FilterUtils.instance().positionTitle, "");
            } else if (tabPosition == 1) {
                onFilterDoneListener.onFilterDone(1, "筛选", "");
            } else if (tabPosition == 2) {
                onFilterDoneListener.onFilterDone(2, "价格", "");
            } else if (tabPosition == 3) {
                onFilterDoneListener.onFilterDone(3, "排序", "");
            } else {
                onFilterDoneListener.onFilterDone(tabPosition, "代码错误", "");
            }
        } else {
            MLog.e("DropMenuAdapter中设置的监听不起作用--onFilterDoneListener==null");
        }
    }


    /**
     * ===================================================================================================
     * =================================================未使用的其他样式==================================================
     * ===================================================================================================
     */

    /**
     * 两个grid设置
     */
    private View createDoubleGrid() {

        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }

        List<String> areas = new ArrayList<>();
        for (int i = 9; i < 20; ++i) {
            areas.add("3top" + i);
        }

        DoubleGridView view = new DoubleGridView(mContext)
                .setmTopGridData(phases)
                .setmBottomGridList(areas)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build();

        return view;
    }

    /**
     * 市 区二级筛选 （未删）
     *
     * @return
     */
    private View createDoubleListView() {

        DoubleListView<FilterTypeBean, String> comTypeDoubleListView = new DoubleListView<FilterTypeBean, String>(mContext)
                .leftAdapter(new SimpleTextAdapter<FilterTypeBean>(null, mContext) {
                    @Override
                    public String provideText(FilterTypeBean filterType) {
                        return filterType.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(DpUtils.dpToPx(mContext, 44), DpUtils.dpToPx(mContext, 15), 0, DpUtils.dpToPx(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(DpUtils.dpToPx(mContext, 30), DpUtils.dpToPx(mContext, 15), 0, DpUtils.dpToPx(mContext, 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterTypeBean, String>() {
                    @Override
                    public List<String> provideRightList(FilterTypeBean item, int position) {
                        List<String> child = item.child;
                        if (CommonUtil.isEmpty(child)) {
                            FilterUtils.instance().doubleListLeft = item.desc;
                            FilterUtils.instance().doubleListRight = "";

                            FilterUtils.instance().position = 1;
                            FilterUtils.instance().positionTitle = item.desc;

                            MLog.ToastShort(mContext, "item响应");
                            onFilterDone(0);
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterTypeBean, String>() {
                    @Override
                    public void onRightItemClick(FilterTypeBean item, String string) {
                        FilterUtils.instance().doubleListLeft = item.desc;
                        FilterUtils.instance().doubleListRight = string;

                        FilterUtils.instance().position = 1;
                        FilterUtils.instance().positionTitle = string;

                        MLog.ToastShort(mContext, "item响应");

                        onFilterDone(0);
                    }
                });


        //假数据
        List<FilterTypeBean> list = new ArrayList<>();

        //第一项
        FilterTypeBean filterType = new FilterTypeBean();
        filterType.desc = "北京";
        List<String> childList0 = new ArrayList<>();
        for (int i = 4; i < 8; ++i) {
            childList0.add("北京市区" + i);
        }
        filterType.child = childList0;
        list.add(filterType);

        //第二项
        filterType = new FilterTypeBean();
        filterType.desc = "山东";
        List<String> childList = new ArrayList<>();
        for (int i = 0; i < 23; ++i) {
            childList.add("山东各市" + i);
        }
        filterType.child = childList;
        list.add(filterType);

        //第三项
        filterType = new FilterTypeBean();
        filterType.desc = "12";
        childList = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            childList.add("12" + i);
        }
        filterType.child = childList;
        list.add(filterType);

        //初始化选中.
        comTypeDoubleListView.setLeftList(list, 1);
        comTypeDoubleListView.setRightList(list.get(1).child, -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }
}
