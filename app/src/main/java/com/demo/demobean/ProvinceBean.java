package com.demo.demobean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 省
 */

public class ProvinceBean implements Serializable {
    int id;
    String name;
    String pid;
    ArrayList<CityBean> child;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public ArrayList<CityBean> getChild() {
        return child;
    }

    public void setChild(ArrayList<CityBean> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "ProvinceBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", child=" + child +
                '}';
    }
}
