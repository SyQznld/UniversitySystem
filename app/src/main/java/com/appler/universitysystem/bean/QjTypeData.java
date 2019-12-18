package com.appler.universitysystem.bean;

public class QjTypeData {


    /**
     * quanjingtype : 食堂
     */

    private String quanjingtype;

    public String getQuanjingtype() {
        return quanjingtype;
    }

    public void setQuanjingtype(String quanjingtype) {
        this.quanjingtype = quanjingtype;
    }

    @Override
    public String toString() {
        return "QjTypeData{" +
                "quanjingtype='" + quanjingtype + '\'' +
                '}';
    }
}
