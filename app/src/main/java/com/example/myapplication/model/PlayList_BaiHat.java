package com.example.myapplication.model;

public class PlayList_BaiHat {
    private String idplbh, idpl;
    private int idbh;

    public PlayList_BaiHat(){}

    public PlayList_BaiHat(String idplbh, String idpl, int idbh) {
        this.idplbh = idplbh;
        this.idpl = idpl;
        this.idbh = idbh;
    }

    public String getIdplbh() {
        return idplbh;
    }

    public void setIdplbh(String idplbh) {
        this.idplbh = idplbh;
    }

    public String getIdpl() {
        return idpl;
    }

    public void setIdpl(String idpl) {
        this.idpl = idpl;
    }

    public int getIdbh() {
        return idbh;
    }

    public void setIdbh(int idbh) {
        this.idbh = idbh;
    }

}
