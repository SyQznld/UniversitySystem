package com.appler.universitysystem.bean;

import java.io.Serializable;


@SuppressWarnings("serial")
public class LoginUser implements Serializable {

    /**
     * id : 7
     * user_name : 2
     * user_password : 4
     * user_role : null
     * user_tele : 5
     * user_email : 6
     * user_depart : null
     * isdelete : true
     */

    private String id;
    private String user_name;
    private String user_password;
    private String user_role;
    private String user_tele;
    private String user_email;
    private String user_depart;
    private boolean isdelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "LoginUser{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_role='" + user_role + '\'' +
                ", user_tele='" + user_tele + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_depart='" + user_depart + '\'' +
                ", isdelete=" + isdelete +
                '}';
    }
}
