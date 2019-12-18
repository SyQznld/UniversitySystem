package com.appler.universitysystem.bean;

import java.util.List;

public class DZJGData {

    /**
     * parents : 党群工作机构
     * children : [{"id":8,"name":"组织部(党校)","pid":1,"url":"http://zzb.zznu.edu.cn/","jianjie":"贯彻落实党的干部路线、方针、政策，制定或参与干部人事工作的有关规定和干部人事制度改革建议方案.","tel":"0371-65502305","geom":null,"address":"北大学城英才街6号","neiyou":"党政人员","zuobiao":"4145524.313975, 12651572.661564"},{"id":9,"name":"宣传部","pid":1,"url":"http://zzb.zznu.edu.cn/","jianjie":"全面负责宣传工作。坚持以正面宣传为主的方针，唱响主旋律，打好主动仗.","tel":"xcb218@163.com","geom":null,"address":"北大学城英才街6号","neiyou":"宣传人员","zuobiao":"4145486.09546, 12651575.050221"},{"id":12,"name":"工会","pid":1,"url":"http://gh.zznu.edu.cn/","jianjie":"围绕我校本科教学合格评估中心工作，充分发挥自身职能。","tel":"0371-65502001","geom":null,"address":"郑州市惠济区英才街6号","neiyou":"纪检人员","zuobiao":"4145437.127989, 12651569.974325"},{"id":7,"name":"党政办公室","pid":1,"url":"http://office.zznu.edu.cn/","jianjie":"贯彻落实党和国家的方针、政策，执行上级部门和学校党委、行政的指示、决定.","tel":"0371-65501002","geom":null,"address":"北大学城英才街6号","neiyou":"党政人员","zuobiao":"POINT(113.651729794745 34.8627693989588)"},{"id":10,"name":"统战部","pid":1,"url":"http://tzb.zznu.edu.cn/","jianjie":"院党委设立的独立职能部门，履行学习、宣传和贯彻党对统一战线工作的方针和政策.","tel":"0371-65502007","geom":null,"address":"河南省郑州市英才街6号郑州师范学院东校区信息楼9楼","neiyou":"统战人员","zuobiao":null},{"id":11,"name":"纪委(监察处)","pid":1,"url":"http://jw.zznu.edu.cn/","jianjie":"党为完成党章赋予的纪律检查任务而设立的重要工作机构.","tel":"0371-65502889","geom":null,"address":"郑州师范学院东校区综合楼1018室","neiyou":"纪检人员","zuobiao":null},{"id":13,"name":"团委","pid":1,"url":"http://gh.zznu.edu.cn/","jianjie":"宣传、贯彻、执行党的路线、方针政策。根据校党委和上级团组织的工作部署，制定中长期的工作规划和阶段性工作计划。","tel":"0371-65502001","geom":null,"address":"郑州市惠济区英才街6号","neiyou":"团委人员","zuobiao":null}]
     */

    private String parents;
    private List<ChildrenBean> children;

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 8
         * name : 组织部(党校)
         * pid : 1
         * url : http://zzb.zznu.edu.cn/
         * jianjie : 贯彻落实党的干部路线、方针、政策，制定或参与干部人事工作的有关规定和干部人事制度改革建议方案.
         * tel : 0371-65502305
         * geom : null
         * address : 北大学城英才街6号
         * neiyou : 党政人员
         * zuobiao : 4145524.313975, 12651572.661564
         */

        private int id;
        private String name;
        private int pid;
        private String url;
        private String jianjie;
        private String tel;
        private String geom;
        private String address;
        private String neiyou;
        private String zuobiao;

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

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getJianjie() {
            return jianjie;
        }

        public void setJianjie(String jianjie) {
            this.jianjie = jianjie;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getGeom() {
            return geom;
        }

        public void setGeom(String geom) {
            this.geom = geom;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNeiyou() {
            return neiyou;
        }

        public void setNeiyou(String neiyou) {
            this.neiyou = neiyou;
        }

        public String getZuobiao() {
            return zuobiao;
        }

        public void setZuobiao(String zuobiao) {
            this.zuobiao = zuobiao;
        }

        @Override
        public String toString() {
            return "ChildrenBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", pid=" + pid +
                    ", url='" + url + '\'' +
                    ", jianjie='" + jianjie + '\'' +
                    ", tel='" + tel + '\'' +
                    ", geom=" + geom +
                    ", address='" + address + '\'' +
                    ", neiyou='" + neiyou + '\'' +
                    ", zuobiao='" + zuobiao + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DZJGData{" +
                "parents='" + parents + '\'' +
                ", children=" + children +
                '}';
    }
}
