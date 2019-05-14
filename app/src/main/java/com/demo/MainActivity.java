package com.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.demo.demobean.AreaBean;
import com.demo.demobean.CityBean;
import com.demo.demobean.FilterBean;
import com.demo.demobean.InstitutionPriceBean;
import com.demo.demobean.ProvinceBean;
import com.demo.filter.DropDownMenu;
import com.demo.filter.interfaces.OnFilterDoneListener;
import com.demo.filter.util.MLog;
import com.demo.other.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFilterDoneListener {

    DropDownMenu dropDownMenu;

    TextView layout_loaddata;
    //数据
    private FilterBean filterBean;//总数据源
    private List<ProvinceBean> provinceBeans;//省
    private List<CityBean> cityBeans;//市
    private List<AreaBean> areaBeans;//区
    private List<InstitutionPriceBean> priceBeans;//价格
    private String[] titleList;//标题
    private List<String> sortListData;//排序
    private List<FilterBean.InstitutionObject> objectbeans;//收住对象
    private List<FilterBean.PlaceProperty> propertyBeans;//机构性质
    private List<FilterBean.Bed> beds;//床位
    private List<FilterBean.InstitutionPlace> places;//类型
    private List<FilterBean.InstitutionFeature> features;//特色

    //筛选器适配
    private DropMenuAdapter dropMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        dropDownMenu = findViewById(R.id.dropDownMenu);
        layout_loaddata = findViewById(R.id.layout_loaddata);
        initView();
        initData();

        initFilterDropDownView();
    }

    /**
     * 显示
     */
    /**
     * 筛选框 初始化+获取列表数据+筛选条件监听
     */
    private void initFilterDropDownView() {
        //绑定数据源
        dropMenuAdapter = new DropMenuAdapter(this, titleList, this);
        dropMenuAdapter.setSortListData(sortListData);
        dropMenuAdapter.setFilterBean(filterBean);
        dropDownMenu.setMenuAdapter(dropMenuAdapter);

        //01 省市区 回调参数
        dropMenuAdapter.setOnPlaceCallbackListener(new DropMenuAdapter.OnPlaceCallbackListener() {
            @Override
            public void onPlaceCallbackListener(int provinceId, int cityId, int areaId) {


                ToastUtil.ToastShort(MainActivity.this, "省的id=" + provinceId + "--" + cityId + "--" + areaId);
            }
        });
        //02 多条件 回调参数
        dropMenuAdapter.setOnMultiFilterCallbackListener(new DropMenuAdapter.OnMultiFilterCallbackListener() {
            @Override
            public void onMultiFilterCallbackListener(int objId, int propertyId, int bedId, int typeId, String serviceId) {


                ToastUtil.ToastShort(MainActivity.this, "多选的i依次是=" + objId + "--" + propertyId + "--" + bedId + "--" + typeId + "--" + serviceId);
            }
        });

        //03 dropMenuAdapter 价格范围回调
        dropMenuAdapter.setOnPriceCallbackListener(new DropMenuAdapter.OnPriceCallbackListener() {
            @Override
            public void onPriceCallbackListener(InstitutionPriceBean item) {


                ToastUtil.ToastShort(MainActivity.this, "价格=" + item.getName() + "--id=" + item.getId());
            }
        });

        //04 dropMenuAdapter 排序回调 0 1 2
        dropMenuAdapter.setOnSortCallbackListener(new DropMenuAdapter.OnSortCallbackListener() {
            @Override
            public void onSortCallbackListener(int item) {


                ToastUtil.ToastShort(MainActivity.this, "排序id=" + item);
            }
        });

        //        //异步加载列表数据
        //        loadListData();
    }

    /**
     * 初始化
     */
    private void initView() {
        filterBean = new FilterBean();
        provinceBeans = new ArrayList<>();
        cityBeans = new ArrayList<>();
        areaBeans = new ArrayList<>();
        priceBeans = new ArrayList<>();

        objectbeans = new ArrayList<>();
        propertyBeans = new ArrayList<>();
        beds = new ArrayList<>();
        places = new ArrayList<>();
        features = new ArrayList<>();
    }

    /**
     * 筛选器title的变化
     * <p>
     * 点击到选中的item，自动收回
     *
     * @param position
     * @param positionTitle
     * @param urlValue
     */
    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        //数据显示到筛选标题中
        dropDownMenu.setPositionIndicatorText(position, positionTitle);
        dropDownMenu.close();
    }


    //**************************************************************************************************************************
    //*******************************以下是假数据，正常是接口数据，demo中设置假数据方便演示****************************************
    //**************************************************************************************************************************

    /**
     * 制作假数据
     * 说明：FilterBean是公司项目接口返回的数据bean，现在用假数据实现，用注释+gif给大家解释
     */
    private void initData() {

        //筛选标题数据（固定数据）
        titleList = new String[]{"区域", "筛选", "价格", "排序"};

        //排序数据
        sortListData = new ArrayList<>();
        sortListData.add("综合排序");
        sortListData.add("价格 从高到底");
        sortListData.add("价格 从低到高");

        //区数据
        for (int i = 0; i < 33; i++) {
            AreaBean bean = new AreaBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");
                bean.setPid("没用String");

                areaBeans.add(bean);
            } else {
                bean.setId(i);
                bean.setName("区" + i);
                bean.setPid("没用String");

                areaBeans.add(bean);
            }
        }

        MLog.d("区的数据:" + areaBeans.size());

        //市数据
        for (int i = 0; i < 26; i++) {
            CityBean bean = new CityBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");
                bean.setPid("没用String");

                cityBeans.add(bean);

                //添加区数据
                bean.setChild((ArrayList<AreaBean>) areaBeans);
            } else {
                bean.setId(i);
                bean.setName("市" + i);
                bean.setPid("没用String");

                cityBeans.add(bean);
                //添加区数据
                bean.setChild((ArrayList<AreaBean>) areaBeans);
            }
        }

        //省数据
        for (int i = 0; i < 36; i++) {
            ProvinceBean bean = new ProvinceBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");
                bean.setPid("没用String");

                provinceBeans.add(bean);

                //省份添加市数据
                bean.setChild((ArrayList<CityBean>) cityBeans);
            } else {
                bean.setId(i);
                bean.setName("省" + i);
                bean.setPid("没用String");

                provinceBeans.add(bean);
                //省份添加市数据
                bean.setChild((ArrayList<CityBean>) cityBeans);
            }
        }

        //价格
        for (int i = 0; i < 10; i++) {
            InstitutionPriceBean bean = new InstitutionPriceBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("2000元以下");
                priceBeans.add(bean);
            } else if (i == 9) {
                bean.setId(i);
                bean.setName("xxx元以上");
                priceBeans.add(bean);
            } else {
                bean.setId(i);
                bean.setName((2000 + i * 100) + "元");
                priceBeans.add(bean);
            }

        }


        //多选数据（5条）
        //01收住对象
        for (int i = 0; i < 7; i++) {

            FilterBean.InstitutionObject bean = new FilterBean.InstitutionObject();
            if (i == 0) {
                bean.setId(i);
                bean.setType("不限");

                objectbeans.add(bean);
            } else {
                bean.setId(i);
                bean.setType("对象" + i);

                objectbeans.add(bean);
            }
        }
        //02 机构性质
        for (int i = 0; i < 3; i++) {

            FilterBean.PlaceProperty bean = new FilterBean.PlaceProperty();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");

                propertyBeans.add(bean);
            } else if (i == 1) {
                bean.setId(i);
                bean.setName("公办");

                propertyBeans.add(bean);
            } else if (i == 2) {
                bean.setId(i);
                bean.setName("民办");

                propertyBeans.add(bean);
            }
        }

        //03床位

        for (int i = 0; i < 4; i++) {

            FilterBean.Bed bean = new FilterBean.Bed();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");

                beds.add(bean);
            } else if (i == 1) {
                bean.setId(i);
                bean.setName("100以下");

                beds.add(bean);
            } else if (i == 2) {
                bean.setId(i);
                bean.setName("100-500张");

                beds.add(bean);
            } else if (i == 3) {
                bean.setId(i);
                bean.setName("500张以上");

                beds.add(bean);
            }
        }
        //04机构类型

        for (int i = 0; i < 7; i++) {

            FilterBean.InstitutionPlace bean = new FilterBean.InstitutionPlace();
            if (i == 0) {
                bean.setId(i);
                bean.setType("不限");

                places.add(bean);
            } else if (i == 1) {
                bean.setId(i);
                bean.setType("社区养老院");

                places.add(bean);
            } else if (i == 2) {
                bean.setId(i);
                bean.setType("敬老院");

                places.add(bean);
            } else if (i == 3) {
                bean.setId(i);
                bean.setType("疗养院");

                places.add(bean);
            } else {
                bean.setId(i);
                bean.setType("其他" + i);

                places.add(bean);
            }
        }
        //05特色

        for (int i = 0; i < 7; i++) {

            FilterBean.InstitutionFeature bean = new FilterBean.InstitutionFeature();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");

                features.add(bean);
            } else if (i == 1) {
                bean.setId(i);
                bean.setName("医养结合");

                features.add(bean);
            } else if (i == 2) {
                bean.setId(i);
                bean.setName("免费试住");

                features.add(bean);
            } else if (i == 3) {
                bean.setId(i);
                bean.setName("合作医院");

                features.add(bean);
            } else {
                bean.setId(i);
                bean.setName("其他" + i);

                features.add(bean);
            }
        }

        //假数据整合，当成接口返回bean
        filterBean.setType((ArrayList<FilterBean.InstitutionPlace>) places);
        filterBean.setFeature((ArrayList<FilterBean.InstitutionFeature>) features);
        filterBean.setBed((ArrayList<FilterBean.Bed>) beds);
        filterBean.setObject((ArrayList<FilterBean.InstitutionObject>) objectbeans);
        filterBean.setProperty((ArrayList<FilterBean.PlaceProperty>) propertyBeans);
        filterBean.setPrice((ArrayList<InstitutionPriceBean>) priceBeans);

        filterBean.setProvince((ArrayList<ProvinceBean>) provinceBeans);
    }

}
