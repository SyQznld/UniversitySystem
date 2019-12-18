package com.appler.universitysystem.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class UserData  implements Parcelable {
    @Id
    private Long id;


    private String userId;
    private String user_name;
    private String user_password;
    private String user_role;
    private String user_tele;
    private String user_email;
    private String user_depart;
    private boolean isdelete;


    protected UserData(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        userId = in.readString();
        user_name = in.readString();
        user_password = in.readString();
        user_role = in.readString();
        user_tele = in.readString();
        user_email = in.readString();
        user_depart = in.readString();
        isdelete = in.readByte() != 0;
    }

    @Generated(hash = 270799224)
    public UserData(Long id, String userId, String user_name, String user_password,
            String user_role, String user_tele, String user_email,
            String user_depart, boolean isdelete) {
        this.id = id;
        this.userId = userId;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_role = user_role;
        this.user_tele = user_tele;
        this.user_email = user_email;
        this.user_depart = user_depart;
        this.isdelete = isdelete;
    }

    @Generated(hash = 1838565001)
    public UserData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_tele() {
        return user_tele;
    }

    public void setUser_tele(String user_tele) {
        this.user_tele = user_tele;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_depart() {
        return user_depart;
    }

    public void setUser_depart(String user_depart) {
        this.user_depart = user_depart;
    }

    public boolean isIsdelete() {
        return isdelete;
    }

    public void setIsdelete(boolean isdelete) {
        this.isdelete = isdelete;
    }

    public static Creator<UserData> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", userId=" + userId +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_role='" + user_role + '\'' +
                ", user_tele='" + user_tele + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_depart='" + user_depart + '\'' +
                ", isdelete=" + isdelete +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(userId);

    }

    public boolean getIsdelete() {
        return this.isdelete;
    }

}
