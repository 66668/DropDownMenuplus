package com.demo.demobean;

/**
 * Created by sjy on 2017/8/31.
 */

import java.io.Serializable;

/**
 * 价格
 */
public class InstitutionPriceBean implements Serializable {
    int id;
    String name;

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
    public String toString() {
        return "InstitutionPriceBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
