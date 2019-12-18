package com.appler.universitysystem.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ApkTimeLimitData implements Parcelable{

    /**
     * timeend : 2019-07-31
     * now : 2019-07-01
     * timestart : 2019-07-01
     */

    private String timeend;
    private String now;
    private String timestart;

    protected ApkTimeLimitData(Parcel in) {
        timeend = in.readString();
        now = in.readString();
        timestart = in.readString();
    }

    public static final Creator<ApkTimeLimitData> CREATOR = new Creator<ApkTimeLimitData>() {
        @Override
        public ApkTimeLimitData createFromParcel(Parcel in) {
            return new ApkTimeLimitData(in);
        }

        @Override
        public ApkTimeLimitData[] newArray(int size) {
            return new ApkTimeLimitData[size];
        }
    };

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    @Override
    public String toString() {
        return "ApkTimeLimitData{" +
                "timeend='" + timeend + '\'' +
                ", now='" + now + '\'' +
                ", timestart='" + timestart + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(timeend);
        parcel.writeString(now);
        parcel.writeString(timestart);
    }
}
