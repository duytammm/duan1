package com.example.myapplication.model;

public class PlayList {
    private int idPlayList;
    private String TenPlayList, BiaPlayList;

    public PlayList(int idPlayList, String tenPlayList, String biaPlayList) {
        this.idPlayList = idPlayList;
        TenPlayList = tenPlayList;
        BiaPlayList = biaPlayList;
    }

    public int getIdPlayList() {
        return idPlayList;
    }

    public void setIdPlayList(int idPlayList) {
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
