package com.demo.demobean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 多筛选条件
 */

public class FilterBean implements Serializable {

    private ArrayList<InstitutionObject> object;//收住对象
    private ArrayList<PlaceProperty> property;//机构性质
    private ArrayList<Bed> bed;//机构床位
    private ArrayList<InstitutionPlace> type;//机构类型
    private ArrayList<InstitutionFeature> feature;//机构特色
    //价格数据
    private ArrayList<InstitutionPriceBean> price;//价格
    //区域数据
    private ArrayList<ProvinceBean> area;//省市区数据

    public ArrayList<ProvinceBean> getProvince() {
        return area;
    }

    public void setProvince(ArrayList<ProvinceBean> province) {
        this.area = province;
    }

    public ArrayList<InstitutionPlace> getType() {
        return type;
    }

    public void setType(ArrayList<InstitutionPlace> type) {
        this.type = type;
    }

    public ArrayList<PlaceProperty> getProperty() {
        return property;
    }

    public void setProperty(ArrayList<PlaceProperty> property) {
        this.property = property;
    }

    public ArrayList<Bed> getBed() {
        return bed;
    }

    public void setBed(ArrayList<Bed> bed) {
        this.bed = bed;
    }

    public ArrayList<InstitutionObject> getObject() {
        return object;
    }

    public void setObject(ArrayList<InstitutionObject> object) {
        this.object = object;
    }

    public ArrayList<InstitutionFeature> getFeature() {
        return feature;
    }

    public void setFeature(ArrayList<InstitutionFeature> feature) {
        this.feature = feature;
    }

    public ArrayList<ProvinceBean> getArea() {
        return area;
    }

    public void setArea(ArrayList<ProvinceBean> area) {
        this.area = area;
    }

    public ArrayList<InstitutionPriceBean> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<InstitutionPriceBean> price) {
        this.price = price;
    }


    /**
     * 场所
     */
    public static class InstitutionPlace implements Serializable {
        int id;
        String name;
        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return name;
        }

        public void setType(String type) {
            this.name = type;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof InstitutionPlace))
                return false;
            InstitutionPlace that = (InstitutionPlace) o;
            return getId() == that.getId() &&
                    Objects.equals(getType(), that.getType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getType());
        }
    }

    /**
     * PlaceProperty 场所性质
     */
    public static class PlaceProperty implements Serializable {
        int id;
        String name;
        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof PlaceProperty))
                return false;
            PlaceProperty that = (PlaceProperty) o;
            return getId() == that.getId() &&
                    Objects.equals(getName(), that.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    /**
     * 床位
     */
    public static class Bed implements Serializable {

        int id;
        String name;
        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Bed))
                return false;
            Bed bed = (Bed) o;
            return getId() == bed.getId() &&
                    Objects.equals(getName(), bed.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    /**
     * 项目
     */

    public static class InstitutionObject implements Serializable {


        int id;
        String name;

        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return name;
        }

        public void setType(String admittype) {
            this.name = admittype;
        }


    }

    /**
     * 服务
     */
    public static class InstitutionFeature implements Serializable {
        int id;
        String name;

        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof InstitutionFeature))
                return false;
            InstitutionFeature that = (InstitutionFeature) o;
            return getId() == that.getId() &&
                    Objects.equals(getName(), that.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }


}
