package com.appler.universitysystem.bean;

public class PoiTypeStateData {
    private String type;
    private Boolean flag;

    public PoiTypeStateData(String type, Boolean flag) {
        this.type = type;
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "PoiTypeStateData{" +
                "type='" + type + '\'' +
                ", flag=" + flag +
                '}';
    }

    @Override
    public int hashCode() {
        return this.getType().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        PoiTypeStateData poiTypeStateData = (PoiTypeStateData) obj;
        if (this.getType().equals(poiTypeStateData.getType())) {
            return true;
        } else {
            return false;
        }
    }
}
