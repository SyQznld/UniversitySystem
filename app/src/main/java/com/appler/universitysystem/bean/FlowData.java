package com.appler.universitysystem.bean;

import java.util.List;

/**
 * 流程 实体类
 * */
public class FlowData {


    /**
     * flowid : 4
     * flow : [{"name":"缴费","url":null,"remarks":"<p>新生须提前登录指定校园缴费平台，进行网上\r\n缴费。未缴费的新生，到学费收缴处咨询、办理交\r\n费事宜。<\/p>","theme":"注意事项","floorname":"东校区广场","campus":"东校区","floorurl":null,"flowid":"1","id":3,"zong":0,"zuobiao":"POINT(113.650957 34.863453)","routeid":null},{"name":"学院报到","url":"http://yjsc.shcc.edu.cn/41/87/c225a16775/page.htm","remarks":"<p>新生凭录取通知书，到学院报到处核实学生录\r\n取名单及缴费情况，领取《新生报到程序表》,由学\r\n院引导员带领、办理全程报到手续。<\/p>","theme":"流程","floorname":"综合教学楼A区","campus":"东校区","floorurl":"","flowid":"2","id":11,"zong":23,"zuobiao":"POINT(113.651728 34.862750)","routeid":null},{"name":"学院注册","url":"","remarks":"<p>新生凭缴费收据到新生报到处查验准考证、身份证，收缴学生个人档案、团组织关系、照片等，\r\n确定本人所在班级,办理注册手续。<\/p>","theme":"报到流程","floorname":"综合教学楼B区","campus":"东校区","floorurl":"","flowid":"3","id":9,"zong":170,"zuobiao":"POINT(113.652630 34.862774)","routeid":null},{"name":"住宿","url":"","remarks":"<p>1．新生凭缴费收据办理住宿手续;&nbsp;<\/p><p>2．请新生到（西校区、东校区、国基生活园区）办\r\n理住宿手续;&nbsp;<\/p><p>3．入住的是（西校区、东校区、国基生活园区）_号_楼号房间。<\/p>","theme":"住宿流程","floorname":"宿舍区","campus":"东校区","floorurl":"","flowid":"4","id":8,"zong":5,"zuobiao":"POINT(113.651429 34.865856)","routeid":null},{"name":"户口","url":null,"remarks":"<p>外省籍新生如需办理户籍迁移的，到保卫处（许胜利老师处办理，65502890）办理户籍迁移手续，\r\n本省学生不必办理。<\/p>","theme":"注意事项","floorname":"保卫处","campus":"东校区","floorurl":null,"flowid":"5","id":3,"zong":27,"zuobiao":"POINT(113.650793 34.864063)","routeid":null},{"name":"入班","url":null,"remarks":"<p>1．报到完毕后请将此表交本学院辅导员处;&nbsp;<\/p><p>2．确认班级第一次集会时间、地点；<\/p>","theme":"注意事项","floorname":"教学楼","campus":"东校区","floorurl":null,"flowid":"6","id":2,"zong":324,"zuobiao":"POINT(113.650290 34.862463)","routeid":null}]
     */

    private String flowid;
    private List<FlowBean> flow;

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid;
    }

    public List<FlowBean> getFlow() {
        return flow;
    }

    public void setFlow(List<FlowBean> flow) {
        this.flow = flow;
    }

    public static class FlowBean {
        /**
         * name : 缴费
         * url : null
         * remarks : <p>新生须提前登录指定校园缴费平台，进行网上
         缴费。未缴费的新生，到学费收缴处咨询、办理交
         费事宜。</p>
         * theme : 注意事项
         * floorname : 东校区广场
         * campus : 东校区
         * floorurl : null
         * flowid : 1
         * id : 3
         * zong : 0
         * zuobiao : POINT(113.650957 34.863453)
         * routeid : null
         */

        private String name;
        private String url;
        private String remarks;
        private String theme;
        private String floorname;
        private String campus;
        private String floorurl;
        private String flowid;
        private int id;
        private int zong;
        private String zuobiao;
        private String routeid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getFloorname() {
            return floorname;
        }

        public void setFloorname(String floorname) {
            this.floorname = floorname;
        }

        public String getCampus() {
            return campus;
        }

        public void setCampus(String campus) {
            this.campus = campus;
        }

        public String getFloorurl() {
            return floorurl;
        }

        public void setFloorurl(String floorurl) {
            this.floorurl = floorurl;
        }

        public String getFlowid() {
            return flowid;
        }

        public void setFlowid(String flowid) {
            this.flowid = flowid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getZong() {
            return zong;
        }

        public void setZong(int zong) {
            this.zong = zong;
        }

        public String getZuobiao() {
            return zuobiao;
        }

        public void setZuobiao(String zuobiao) {
            this.zuobiao = zuobiao;
        }

        public String getRouteid() {
            return routeid;
        }

        public void setRouteid(String routeid) {
            this.routeid = routeid;
        }
    }
}
