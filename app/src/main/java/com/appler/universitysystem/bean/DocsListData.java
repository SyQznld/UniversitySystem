package com.appler.universitysystem.bean;

/**
 * 资料列表
 */
public class DocsListData  {

    /**
     * id : 8
     * title : 迎新流程
     * addtime : 2019-05-22 11:05:08
     * url : http://192.168.2.253:8077/uploadfiles/yingxin/4fc434d0fb7a4d02b1ccc01d930cbbd8/迎新功能需求.xlsx
     * filename : 迎新功能需求.xlsx
     * ren : 张三
     */

    private int id;
    private String title;
    private String addtime;
    private String url;
    private String filename;
    private String ren;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRen() {
        return ren;
    }

    public void setRen(String ren) {
        this.ren = ren;
    }

    @Override
    public String toString() {
        return "DocsListData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", addtime='" + addtime + '\'' +
                ", url='" + url + '\'' +
                ", filename='" + filename + '\'' +
                ", ren='" + ren + '\'' +
                '}';
    }
}
