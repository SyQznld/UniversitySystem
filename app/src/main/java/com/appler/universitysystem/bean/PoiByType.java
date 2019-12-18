package com.appler.universitysystem.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */

public class PoiByType implements Parcelable {


    /**
     * gid : 5
     * name : 操场
     * x : 113.648293
     * y : 34.864747
     * type : 全景
     * quanjingid : 10.0
     * quanjingimg : yingxinimg/quanjing/caochang.jpg
     * campus : 东校区
     * z : 1.0
     * geom : {"X":113.648293,"Y":34.864747,"SRID":0}
     * quanjingtype : 校貌
     */

    private int gid;
    private String name;
    private double x;
    private double y;
    private String type;
    private double quanjingid;
    private String quanjingimg;
    private String campus;
    private double z;
    private GeomBean geom;
    private String quanjingtype;

    protected PoiByType(Parcel in) {
        gid = in.readInt();
        name = in.readString();
        x = in.readDouble();
        y = in.readDouble();
        type = in.readString();
        quanjingid = in.readDouble();
        quanjingimg = in.readString();
        campus = in.readString();
        z = in.readDouble();
        quanjingtype = in.readString();
    }

    public static final Creator<PoiByType> CREATOR = new Creator<PoiByType>() {
        @Override
        public PoiByType createFromParcel(Parcel in) {
            return new PoiByType(in);
        }

        @Override
        public PoiByType[] newArray(int size) {
            return new PoiByType[size];
        }
    };

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getQuanjingid() {
        return quanjingid;
    }

    public void setQuanjingid(double quanjingid) {
        this.quanjingid = quanjingid;
    }

    public String getQuanjingimg() {
        return quanjingimg;
    }

    public void setQuanjingimg(String quanjingimg) {
        this.quanjingimg = quanjingimg;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public GeomBean getGeom() {
        return geom;
    }

    public void setGeom(GeomBean geom) {
        this.geom = geom;
    }

    public String getQuanjingtype() {
        return quanjingtype;
    }

    public void setQuanjingtype(String quanjingtype) {
        this.quanjingtype = quanjingtype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(gid);
        parcel.writeString(name);
        parcel.writeDouble(x);
        parcel.writeDouble(y);
        parcel.writeString(type);
        parcel.writeDouble(quanjingid);
        parcel.writeString(quanjingimg);
        parcel.writeString(campus);
        parcel.writeDouble(z);
        parcel.writeString(quanjingtype);
    }

    public static class GeomBean implements Parcelable{
        /**
         * X : 113.648293
         * Y : 34.864747
         * SRID : 0
         */

        private double X;
        private double Y;
        private int SRID;

        protected GeomBean(Parcel in) {
            X = in.readDouble();
            Y = in.readDouble();
            SRID = in.readInt();
        }

        public static final Creator<GeomBean> CREATOR = new Creator<GeomBean>() {
            @Override
            public GeomBean createFromParcel(Parcel in) {
                return new GeomBean(in);
            }

            @Override
            public GeomBean[] newArray(int size) {
                return new GeomBean[size];
            }
        };

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
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(X);
            parcel.writeDouble(Y);
            parcel.writeInt(SRID);
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
        return "PoiByType{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                ", quanjingid=" + quanjingid +
                ", quanjingimg='" + quanjingimg + '\'' +
                ", campus='" + campus + '\'' +
                ", z=" + z +
                ", geom=" + geom +
                ", quanjingtype='" + quanjingtype + '\'' +
                '}';
    }
}
