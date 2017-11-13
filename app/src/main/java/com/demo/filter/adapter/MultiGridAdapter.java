package com.demo.filter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demobean.FilterBean;
import com.demo.filter.typeview.grid_holder.MultiItemHolder;
import com.demo.filter.typeview.grid_holder.TitleViewHolder;
import com.demo.filter.util.FilterUtils;

import java.util.List;

/**
 * 多个gridView的自定义控件
 */
public class MultiGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TITLE = 0;//设置title的地方
    private static final int TYPE_ITEM = 1;//设置item的地方

    private Context mContext;
    private LayoutInflater inflater;

    //数据
    private List<FilterBean.InstitutionObject> obj_list;//收住对象
    private List<FilterBean.PlaceProperty> property_list;//机构性质
    private List<FilterBean.Bed> bed_list;//机构床位
    private List<FilterBean.InstitutionPlace> type_list;//机构类型
    private List<FilterBean.InstitutionFeature> special_list;//机构特色

    //返回参数 重置时，该处的值也要清零
    private int objId;
    private int propertyId;
    private int bedId;
    private int typeId;
    private String serviceId = "0";

    //默认 选中的数值 标记
    private int default_obj_position;
    private int default_property_position;
    private int default_bed_position;
    private int default_type_position;
    private int default_service_position;

    public MultiGridAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    //set值
    public void setObj_list(List<FilterBean.InstitutionObject> obj_list) {
        this.obj_list = obj_list;
    }

    public void setProperty_list(List<FilterBean.PlaceProperty> property_list) {
        this.property_list = property_list;
    }

    public void setBed_list(List<FilterBean.Bed> bed_list) {
        this.bed_list = bed_list;
    }

    public void setType_list(List<FilterBean.InstitutionPlace> type_list) {
        this.type_list = type_list;
    }

    public void setSpecial_list(List<FilterBean.InstitutionFeature> special_list) {
        this.special_list = special_list;
    }

    /**
     * set值 带默认初始化值的赋值
     * <p>
     * 由于recyclerView的item复用问题，导致item状态混乱
     * <p>
     * 本app的默认选中 (1)以修改数据源参数状态，(2)设置额外标记，已达到选中要求
     * 其实这里只做标记就能避免item混乱，但是筛选器有个重置功能，所以做了修改数据源状态处理
     */

    public void setObj_list(List<FilterBean.InstitutionObject> obj_list, int default_objId) {
        this.obj_list = obj_list;
        this.default_obj_position = default_objId;
        obj_list.get(default_objId).setSelect(true);
    }

    public void setProperty_list(List<FilterBean.PlaceProperty> property_list, int default_propertyId) {
        this.property_list = property_list;
        this.default_property_position = default_propertyId;
        property_list.get(default_propertyId).setSelect(true);
    }

    public void setBed_list(List<FilterBean.Bed> bed_list, int default_bedId) {
        this.bed_list = bed_list;
        this.default_bed_position = default_bedId;
        bed_list.get(default_bedId).setSelect(true);
    }

    public void setType_list(List<FilterBean.InstitutionPlace> type_list, int default_typeId) {
        this.type_list = type_list;
        this.default_type_position = default_typeId;
        type_list.get(default_typeId).setSelect(true);
    }

    public void setSpecial_list(List<FilterBean.InstitutionFeature> special_list, int default_serviceId) {
        this.special_list = special_list;
        this.default_service_position = default_serviceId;
        special_list.get(default_serviceId).setSelect(true);
    }

    /**
     * 设置 适配的回调
     */

    //02添加筛选回调
    private OnAdpaterCallbackListener onAdpaterCallbackListener;

    public interface OnAdpaterCallbackListener {
        void onItemClickListener(int objId, int propertyId, int bedId, int typeId, String serviceId);
    }

    public void setAdpaterCallback(OnAdpaterCallbackListener onAdpaterCallbackListener) {
        this.onAdpaterCallbackListener = onAdpaterCallbackListener;
    }

    /**
     * 获取类型标记
     * <p>
     * 这里用了两种布局，标题位置一种布局，数据显示一种布局
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0//收住对象的标题位置
                || position == (obj_list.size() + 1)//机构性质标题位置
                || position == ((obj_list.size() + 1) + (property_list.size() + 1))//机构床位标题位置
                || position == ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1))//机构类型标题位置
                || position == ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1) + (type_list.size() + 1))//机构特色标题位置
                ) {
            return TYPE_TITLE;
        }
        return TYPE_ITEM;//显示数据的标记
    }

    /**
     * 根据标记 创建不同的布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case TYPE_TITLE:
                holder = new TitleViewHolder(mContext, parent);
                break;
            case TYPE_ITEM:
                holder = new MultiItemHolder(mContext, parent);
                break;
        }
        return holder;

    }

    /**
     * 该处是recyclerView的item复用的地方，注意保存状态
     * <p>
     * 该处有默认选中的item,也有手动选中item，如果冲突，优先选手动的结果
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);

        switch (itemViewType) {
            case TYPE_TITLE:// 设置标题
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                if (position == 0) {
                    titleViewHolder.bind("收住对象");
                } else if (position == (obj_list.size() + 1)) {
                    titleViewHolder.bind("机构性质");
                } else if (position == ((obj_list.size() + 1) + (property_list.size() + 1))) {
                    titleViewHolder.bind("机构床位");
                } else if (position == ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1))) {
                    titleViewHolder.bind("机构类型");
                } else {//if (position == ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1)) + (type_list.size() + 1))
                    titleViewHolder.bind("机构特色");
                }

                break;

            case TYPE_ITEM:

                MultiItemHolder itemViewHolder = (MultiItemHolder) holder;

                if (position > 0
                        && position < obj_list.size() + 1) {

                    //数据的位置 0-list.size
                    int current_firstPosition = position - 1;
                    //obj_list的数据
                    FilterBean.InstitutionObject firstBean = obj_list.get(position - 1);
                    //绑定数据
                    itemViewHolder.bind(obj_list.get(current_firstPosition).getType(), firstBean);


                    //绑定选中，手动选中优先
                    if (firstBean.isSelect()) {
                        itemViewHolder.textView.setSelected(true);
                        //由于我的筛选，每次点击都会new，所以上一个标记很容易被GC，所以再new出来的时候，把之前状态保留只有在这里设置
                        multiSelectOne = itemViewHolder.textView;
                        seletpositionOne = position;
                    } else {
                        //绑定默认选中
                        if (current_firstPosition == default_obj_position) {
                            itemViewHolder.textView.setSelected(true);
                            multiSelectOne = itemViewHolder.textView;
                            seletpositionOne = position;
                        } else {
                            itemViewHolder.textView.setSelected(false);
                        }
                    }

                    //绑定状态
                    onItemClick(holder, position);

                } else if (position > obj_list.size() + 1
                        && position < ((obj_list.size() + 1) + (property_list.size() + 1))) {

                    //数据的位置 0-list.size
                    int current_secondPosition = position - obj_list.size() - 2;
                    //property_list的数据
                    FilterBean.PlaceProperty secondBean = property_list.get(position - obj_list.size() - 2);
                    //绑定数据
                    itemViewHolder.bind(property_list.get(position - obj_list.size() - 2).getName(), secondBean);

                    //绑定选中，手动选中优先
                    if (secondBean.isSelect()) {
                        itemViewHolder.textView.setSelected(true);
                        multiSelectTwo = itemViewHolder.textView;
                        seletpositionTwo = position;
                    } else {
                        //绑定默认选中
                        if (current_secondPosition == default_property_position) {
                            itemViewHolder.textView.setSelected(true);
                            multiSelectTwo = itemViewHolder.textView;
                            seletpositionTwo = position;
                        } else {
                            itemViewHolder.textView.setSelected(false);
                        }
                    }

                    //绑定状态
                    onItemClick(holder, position);

                } else if (position > ((obj_list.size() + 1) + (property_list.size() + 1))
                        && position < ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1))) {

                    //数据的位置 0-list.size
                    int current_thirdPosition = position - obj_list.size() - property_list.size() - 3;
                    //bed_list数据
                    FilterBean.Bed thirdBean = bed_list.get(position - obj_list.size() - property_list.size() - 3);
                    //绑定数据
                    itemViewHolder.bind(bed_list.get(position - obj_list.size() - property_list.size() - 3).getName(), thirdBean);

                    //绑定选中，手动选中优先
                    if (thirdBean.isSelect()) {
                        itemViewHolder.textView.setSelected(true);
                        multiSelectThree = itemViewHolder.textView;
                        seletpositionThree = position;
                    } else {
                        //绑定默认选中
                        if (current_thirdPosition == default_bed_position) {
                            itemViewHolder.textView.setSelected(true);
                            multiSelectThree = itemViewHolder.textView;
                            seletpositionThree = position;
                        } else {
                            itemViewHolder.textView.setSelected(false);
                        }
                    }
                    //绑定状态
                    onItemClick(holder, position);

                } else if (position > ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1))
                        && position < ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1) + (type_list.size() + 1))) {

                    //数据的位置 0-list.size
                    int current_forthPosition = position - obj_list.size() - property_list.size() - bed_list.size() - 4;
                    //type_listt数据
                    FilterBean.InstitutionPlace forthBean = type_list.get(position - obj_list.size() - property_list.size() - bed_list.size() - 4);
                    //绑定数据
                    itemViewHolder.bind(type_list.get(position - obj_list.size() - property_list.size() - bed_list.size() - 4).getType(), forthBean);
                    //判断状态
                    if (forthBean.isSelect()) {
                        itemViewHolder.textView.setSelected(true);
                        multiSelectFour = itemViewHolder.textView;
                        seletpositionFour = position;
                    } else {
                        //绑定默认选中
                        if (current_forthPosition == default_type_position) {
                            itemViewHolder.textView.setSelected(true);
                            multiSelectFour = itemViewHolder.textView;
                            seletpositionFour = position;
                        } else {
                            itemViewHolder.textView.setSelected(false);
                        }
                    }
                    //绑定状态
                    onItemClick(holder, position);


                } else if (position > ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1) + (type_list.size() + 1))
                        && position < ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1) + (type_list.size() + 1) + (special_list.size() + 1))) {
                    //数据的位置 0-list.size
                    int current_fifthPosition = position - obj_list.size() - property_list.size() - bed_list.size() - type_list.size() - 5;

                    //special_list数据
                    FilterBean.InstitutionFeature fifthBean = special_list.get(position - obj_list.size() - property_list.size() - bed_list.size() - type_list.size() - 5);
                    //绑定数据
                    itemViewHolder.bind(special_list.get(position - obj_list.size() - property_list.size() - bed_list.size() - type_list.size() - 5).getName(), fifthBean);
                    //判断状态
                    if (fifthBean.isSelect()) {
                        itemViewHolder.textView.setSelected(true);
                        multiSelectFive = itemViewHolder.textView;
                        seletpositionFive = position;
                    } else {
                        if (current_fifthPosition == default_service_position) {
                            itemViewHolder.textView.setSelected(true);
                            multiSelectFive = itemViewHolder.textView;
                            seletpositionFive = position;
                        } else {
                            itemViewHolder.textView.setSelected(false);
                        }
                    }
                    //绑定状态
                    onItemClick(holder, position);
                } else {

                }
                break;

        }
    }

    //还原上一个状态使用的变量
    private TextView multiSelectOne;
    private TextView multiSelectTwo;
    private TextView multiSelectThree;
    private TextView multiSelectFour;
    private TextView multiSelectFive;

    private int seletpositionOne = -1;
    private int seletpositionTwo = -1;
    private int seletpositionThree = -1;
    private int seletpositionFour = -1;
    private int seletpositionFive = -1;

    /**
     * 状态监听
     *
     * @param holder
     * @param position
     */
    private void onItemClick(RecyclerView.ViewHolder holder, final int position) {

        final MultiItemHolder itemViewHolder = (MultiItemHolder) holder;

        itemViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position > 0
                        && position < obj_list.size() + 1) {//first

                    //复原 上一个位置状态
                    if (multiSelectOne != null && seletpositionOne != -1) {
                        multiSelectOne.setSelected(false);
                        obj_list.get(seletpositionOne - 1).setSelect(false);
                    }
                    //统一保存选中数据
                    FilterUtils.instance().multiGirdOne = multiSelectOne == null ? null : (FilterBean.InstitutionObject) multiSelectOne.getTag();

                    //设置新位置
                    itemViewHolder.textView.setSelected(true);

                    seletpositionOne = position;
                    multiSelectOne = itemViewHolder.textView;

                    //修改数据源状态，避免item状态混乱
                    obj_list.get(position - 1).setSelect(true);
                    //修改标记，避免item状态混乱
                    default_obj_position = -1;

                    //筛选参数-id
                    objId = obj_list.get(position - 1).getId();

                } else if (position > obj_list.size() + 1
                        && position < ((obj_list.size() + 1) + (property_list.size() + 1))) {//second

                    //复原 上一个位置状态
                    if (multiSelectTwo != null && seletpositionTwo != -1) {
                        multiSelectTwo.setSelected(false);
                        property_list.get(seletpositionTwo - obj_list.size() - 2).setSelect(false);
                    }
                    //统一保存选中数据
                    FilterUtils.instance().multiGirdTwo = multiSelectTwo == null ? null : (FilterBean.PlaceProperty) multiSelectTwo.getTag();

                    //设置新位置
                    itemViewHolder.textView.setSelected(true);

                    seletpositionTwo = position;
                    multiSelectTwo = itemViewHolder.textView;

                    //修改数据源状态，避免item状态混乱
                    property_list.get(position - obj_list.size() - 2).setSelect(true);
                    //修改标记，避免item状态混乱
                    default_property_position = -1;

                    //筛选参数-id
                    propertyId = property_list.get(position - obj_list.size() - 2).getId();

                } else if (position > ((obj_list.size() + 1) + (property_list.size() + 1))
                        && position < ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1))) {//third

                    //复原 上一个位置状态
                    if (multiSelectThree != null && seletpositionThree != -1) {
                        multiSelectThree.setSelected(false);
                        bed_list.get(seletpositionThree - obj_list.size() - property_list.size() - 3).setSelect(false);
                    }
                    //统一保存选中数据
                    FilterUtils.instance().multiGirdThree = multiSelectThree == null ? null : (FilterBean.Bed) multiSelectThree.getTag();

                    //设置新位置
                    itemViewHolder.textView.setSelected(true);

                    seletpositionThree = position;
                    multiSelectThree = itemViewHolder.textView;

                    //修改数据源状态，避免item状态混乱
                    bed_list.get(position - obj_list.size() - property_list.size() - 3).setSelect(true);
                    //修改标记，避免item状态混乱
                    default_bed_position = -1;

                    //筛选参数-id
                    bedId = bed_list.get(position - obj_list.size() - property_list.size() - 3).getId();


                } else if (position > ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1))
                        && position < ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1) + (type_list.size() + 1))) {//forth

                    //复原 上一个位置状态
                    if (multiSelectFour != null && seletpositionFour != -1) {
                        multiSelectFour.setSelected(false);
                        type_list.get(seletpositionFour - obj_list.size() - property_list.size() - bed_list.size() - 4).setSelect(false);
                    }
                    //统一保存选中数据
                    FilterUtils.instance().multiGirdFour = multiSelectFour == null ? null : (FilterBean.InstitutionPlace) multiSelectFour.getTag();

                    //设置新位置
                    itemViewHolder.textView.setSelected(true);

                    seletpositionFour = position;
                    multiSelectFour = itemViewHolder.textView;

                    //修改数据源状态，避免item状态混乱
                    type_list.get(position - obj_list.size() - property_list.size() - bed_list.size() - 4).setSelect(true);
                    //修改标记，避免item状态混乱
                    default_type_position = -1;

                    //筛选参数-id
                    typeId = type_list.get(position - obj_list.size() - property_list.size() - bed_list.size() - 4).getId();

                } else if (position > ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1) + (type_list.size() + 1))
                        && position < ((obj_list.size() + 1) + (property_list.size() + 1) + (bed_list.size() + 1) + (type_list.size() + 1) + (special_list.size() + 1))) {//fifth

                    //复原 上一个位置状态
                    if (multiSelectFive != null && seletpositionFive != -1) {
                        multiSelectFive.setSelected(false);
                        special_list.get(seletpositionFive - obj_list.size() - property_list.size() - bed_list.size() - type_list.size() - 5).setSelect(false);
                    }
                    //统一保存选中数据
                    FilterUtils.instance().multiGirdFive = multiSelectFive == null ? null : (FilterBean.InstitutionFeature) multiSelectFive.getTag();

                    //设置新位置
                    itemViewHolder.textView.setSelected(true);

                    seletpositionFive = position;
                    multiSelectFive = itemViewHolder.textView;

                    //修改数据源状态，避免item状态混乱
                    special_list.get(position - obj_list.size() - property_list.size() - bed_list.size() - type_list.size() - 5).setSelect(true);
                    //修改标记，避免item状态混乱
                    default_service_position = -1;

                    //筛选参数-id
                    serviceId = "" + special_list.get(position - obj_list.size() - property_list.size() - bed_list.size() - type_list.size() - 5).getId();
                }

                //添加回调，把筛选结果返回
                onAdpaterCallbackListener.onItemClickListener(objId, propertyId, bedId, typeId, serviceId);
            }
        });
    }

    /**
     * 数据的总长度
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return obj_list.size() + property_list.size() + bed_list.size() + type_list.size() + special_list.size() + 5;
    }
}
