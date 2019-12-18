package com.appler.universitysystem.bean;

/**
 * 信息列表
 */

public class MessageListData {


    /**
     * id : 2
     * title : 寻狗
     * tcontent : 我也不知道
     * addtime : 2019-06-28
     * name : 后勤科科长
     * importance : 3
     * frequency : 1
     * timestart : 2019-06-28T00:00:00
     * timeend : 2019-06-28T00:00:00
     * color : #fb5b5b
     */

    private int id;
    private String title;
    private String tcontent;
    private String addtime;
    private String name;
    private String importance;
    private int frequency;
    private String timestart;
    private String timeend;
    private String color;

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

    public String getTcontent() {
        return tcontent;
    }

    public void setTcontent(String tcontent) {
        this.tcontent = tcontent;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "MessageListData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tcontent='" + tcontent + '\'' +
                ", addtime='" + addtime + '\'' +
                ", name='" + name + '\'' +
                ", importance='" + importance + '\'' +
                ", frequency=" + frequency +
                ", timestart='" + timestart + '\'' +
                ", timeend='" + timeend + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
