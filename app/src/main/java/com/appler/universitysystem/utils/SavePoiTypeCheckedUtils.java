package com.appler.universitysystem.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SavePoiTypeCheckedUtils {

    //checkBox选中状态
    public Map<Integer, Boolean> poiTypeCheckedMap = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        return "SavePoiTypeCheckedUtils{" +
                "poiTypeCheckedMap=" + poiTypeCheckedMap +
                '}';
    }
}
