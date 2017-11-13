package com.demo.demobean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 市
 */

public class CityBean implements Serializable {
    int id;
    String name;
    String pid;
    ArrayList<AreaBean> child;

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

    public ArrayList<AreaBean> getChild() {
        return child;
    }

    public void setChild(ArrayList<AreaBean> child) {
        this.child = child;
    }
}
