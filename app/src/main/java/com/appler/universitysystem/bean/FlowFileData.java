package com.appler.universitysystem.bean;

/**
 * 流程相关文件   实体类
 */

public class FlowFileData {

    /**
     * id : 2
     * flowid : 6
     * filename : 迎新功能需求
     * fileurl : http://192.168.2.253:8077/uploadfiles/flow/601e3dd59a37405b9d2e9ec029cbc5e7/迎新功能需求.xlsx
     * filetype : .xlsx
     * filetime : 2019/5/27 11:47:34
     * uploadr : 后勤科科长
     */

    private int id;
    private String flowid;
    private String filename;
    private String fileurl;
    private String filetype;
    private String filetime;
    private String uploadr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFiletime() {
        return filetime;
    }

    public void setFiletime(String filetime) {
        this.filetime = filetime;
    }

    public String getUploadr() {
        return uploadr;
    }

    public void setUploadr(String uploadr) {
        this.uploadr = uploadr;
    }

    @Override
    public String toString() {
        return "FlowFileData{" +
                "id=" + id +
                ", flowid='" + flowid + '\'' +
                ", filename='" + filename + '\'' +
                ", fileurl='" + fileurl + '\'' +
                ", filetype='" + filetype + '\'' +
                ", filetime='" + filetime + '\'' +
                ", uploadr='" + uploadr + '\'' +
                '}';
    }
}
