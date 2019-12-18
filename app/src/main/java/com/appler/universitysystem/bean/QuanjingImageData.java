package com.appler.universitysystem.bean;


/**
 *全景图片信息
 */

public class QuanjingImageData {


    /**
     * gid : 53
     * id : 53
     * x : null
     * y : null
     * name : 南门
     * type : 全景
     * geom : {"X":113.650946,"Y":34.860939,"SRID":0}
     * quanjingid : 1
     * quanjingimg : http://192.168.2.253:8077/yingxinimg/quanjing/nanmen.jpg
     */

    private int gid;
    private int id;
    private String x;
    private String y;
    private String name;
    private String type;
    private GeomBean geom;
    private int quanjingid;
    private String quanjingimg;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeomBean getGeom() {
        return geom;
    }

    public void setGeom(GeomBean geom) {
        this.geom = geom;
    }

    public int getQuanjingid() {
        return quanjingid;
    }

    public void setQuanjingid(int quanjingid) {
        this.quanjingid = quanjingid;
    }

    public String getQuanjingimg() {
        return quanjingimg;
    }

    public void setQuanjingimg(String quanjingimg) {
        this.quanjingimg = quanjingimg;
    }

    public static class GeomBean {
        /**
         * X : 113.650946
         * Y : 34.860939
         * SRID : 0
         */

        private double X;
        private double Y;
        private int SRID;

        public double getX() {
            return X;
        }

        public void setX(double X) {
            this.X = X;
        }

        public double getY() {
            return Y;
        }

        public void setY(double Y) {
            this.Y = Y;
        }

        public int getSRID() {
            return SRID;
        }

        public void setSRID(int SRID) {
            this.SRID = SRID;
        }

        @Override
        public String toString() {
            return "GeomBean{" +
                    "X=" + X +
                    ", Y=" + Y +
                    ", SRID=" + SRID +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QuanjingImageData{" +
                "gid=" + gid +
                ", id=" + id +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", geom=" + geom +
                ", quanjingid=" + quanjingid +
                ", quanjingimg='" + quanjingimg + '\'' +
                '}';
    }
}
