package com.appler.universitysystem.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *POI点
 */

public class POIData implements Parcelable{

    /**
     * gid : 1
     * name : 静馨公寓3
     * x : 113.651719
     * y : 34.86691
     * type : 住宿
     * quanjingid : null
     * quanjingimg : null
     * campus : 东校区
     * z : 22.5
     * geom : {"X":113.651719449625,"Y":34.8669099823474,"SRID":0}
     */

    private int gid;
    private String name;
    private String x;
    private String y;
    private String type;
    private Object quanjingid;
    private Object quanjingimg;
    private String campus;
    private String z;
    private GeomBean geom;

    protected POIData(Parcel in) {
        gid = in.readInt();
        name = in.readString();
        x = in.readString();
        y = in.readString();
        type = in.readString();
        campus = in.readString();
        z = in.readString();
    }

    public static final Creator<POIData> CREATOR = new Creator<POIData>() {
        @Override
        public POIData createFromParcel(Parcel in) {
            return new POIData(in);
        }

        @Override
        public POIData[] newArray(int size) {
            return new POIData[size];
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getQuanjingid() {
        return quanjingid;
    }

    public void setQuanjingid(Object quanjingid) {
        this.quanjingid = quanjingid;
    }

    public Object getQuanjingimg() {
        return quanjingimg;
    }

    public void setQuanjingimg(Object quanjingimg) {
        this.quanjingimg = quanjingimg;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public GeomBean getGeom() {
        return geom;
    }

    public void setGeom(GeomBean geom) {
        this.geom = geom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(gid);
        parcel.writeString(name);
        parcel.writeString(x);
        parcel.writeString(y);
        parcel.writeString(type);
        parcel.writeString(campus);
        parcel.writeString(z);
    }

    public static class GeomBean implements Parcelable{
        /**
         * X : 113.651719449625
         * Y : 34.8669099823474
         * SRID : 0
         */

        private String X;
        private String Y;
        private int SRID;

        protected GeomBean(Parcel in) {
            X = in.readString();
            Y = in.readString();
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

        public String getX() {
            return X;
        }

        public void setX(String X) {
            this.X = X;
        }

        public String getY() {
            return Y;
        }

        public void setY(String Y) {
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
            parcel.writeString(X);
            parcel.writeString(Y);
            parcel.writeInt(SRID);
        }

        @Override
        public String toString() {
            return "GeomBean{" +
                    "X='" + X + '\'' +
                    ", Y='" + Y + '\'' +
                    ", SRID=" + SRID +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "POIData{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", type='" + type + '\'' +
                ", quanjingid=" + quanjingid +
                ", quanjingimg=" + quanjingimg +
                ", campus='" + campus + '\'' +
                ", z='" + z + '\'' +
                ", geom=" + geom +
                '}';
    }
}
