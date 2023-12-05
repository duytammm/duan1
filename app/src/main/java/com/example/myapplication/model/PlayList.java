package com.example.myapplication.model;

public class PlayList {
    private String idPlayList;
    private String TenPlayList, BiaPlayList;

    public PlayList() {}

    public PlayList(String idPlayList, String tenPlayList, String biaPlayList) {
        this.idPlayList = idPlayList;
        TenPlayList = tenPlayList;
        BiaPlayList = biaPlayList;
    }

    public String getIdPlayList() {
        return idPlayList;
    }

    public void setIdPlayList(String idPlayList) {
        this.idPlayList = idPlayList;
    }

    public String getTenPlayList() {
        return TenPlayList;
    }

    public void setTenPlayList(String tenPlayList) {
        TenPlayList = tenPlayList;
    }

    public String getBiaPlayList() {
        return BiaPlayList;
    }

    public void setBiaPlayList(String biaPlayList) {
        BiaPlayList = biaPlayList;
    }
}
