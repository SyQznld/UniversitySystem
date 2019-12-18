package com.appler.universitysystem.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PoiTypeData implements Parcelable{

    /**
     * type : 其他
     */

    private String type;

    public PoiTypeData(String type) {
        this.type = type;
    }

    protected PoiTypeData(Parcel in) {
        type = in.readString();
    }

    public static final Creator<PoiTypeData> CREATOR = new Creator<PoiTypeData>() {
        @Override
        public PoiTypeData createFromParcel(Parcel in) {
            return new PoiTypeData(in);
        }

        @Override
        public PoiTypeData[] newArray(int size) {
            return new PoiTypeData[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PoiTypeData{" +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
    }
}
