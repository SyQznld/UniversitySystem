package com.appler.universitysystem.bean;

/**
 *  流程关联道路信息
 */

public class FlowRouteData {


    /**
     * gid : 11
     * roadname : 学院路
     * boadbegin : 西校区广场
     * boadend : 综合教学楼A区
     * roadgps : null
     * geom : LINERING(113.651015 34.863272,113.651099 34.863266,113.651104 34.862403,113.651479 34.862426,113.651474 34.862740,113.651727 34.862718)
     * flowid : 2
     * campus : 东校区
     */

    private int gid;
    private String roadname;
    private String boadbegin;
    private String boadend;
    private String roadgps;
    private String geom;
    private String flowid;
    private String campus;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getRoadname() {
        return roadname;
    }

    public void setRoadname(String roadname) {
        this.roadname = roadname;
    }

    public String getBoadbegin() {
        return boadbegin;
    }

    public void setBoadbegin(String boadbegin) {
        this.boadbegin = boadbegin;
    }

    public String getBoadend() {
        return boadend;
    }

    public void setBoadend(String boadend) {
        this.boadend = boadend;
    }

    public String getRoadgps() {
        return roadgps;
    }

    public void setRoadgps(String roadgps) {
        this.roadgps = roadgps;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    @Override
    public String toString() {
        return "FlowRouteData{" +
                "gid=" + gid +
                ", roadname='" + roadname + '\'' +
                ", boadbegin='" + boadbegin + '\'' +
                ", boadend='" + boadend + '\'' +
                ", roadgps='" + roadgps + '\'' +
                ", geom='" + geom + '\'' +
                ", flowid='" + flowid + '\'' +
                ", campus='" + campus + '\'' +
                '}';
    }
}
