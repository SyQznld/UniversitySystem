package com.appler.universitysystem.version;

/**
 * 版本更新 版本
 */

public class VersionData {


    /**
     * version : 1.00
     * updateinfo : 优化页面布局，加载图层显示，添加定位导航
     * loadurl : http://192.168.1.186:8077//apk/郑州师范学院迎新app.apk
     */

    private String version;
    private String updateinfo;
    private String loadurl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateinfo() {
        return updateinfo;
    }

    public void setUpdateinfo(String updateinfo) {
        this.updateinfo = updateinfo;
    }

    public String getLoadurl() {
        return loadurl;
    }

    public void setLoadurl(String loadurl) {
        this.loadurl = loadurl;
    }
}
