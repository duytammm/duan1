package com.example.myapplication.model;

public class BaiHat {
    private int idBaiHat, stt;

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    private String TenCaSi, TenBH, BiaBH, linkBH;

    public String getLinkBH() {
        return linkBH;
    }

    public void setLinkBH(String linkBH) {
        this.linkBH = linkBH;
    }

    public BaiHat() {
    }

    public BaiHat(int idBaiHat, String tenCaSi, String tenBH, String biaBH, String linkBH) {
        this.idBaiHat = idBaiHat;
        TenCaSi = tenCaSi;
        TenBH = tenBH;
        BiaBH = biaBH;
        this.linkBH = linkBH;
    }

    public int getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(int idBaiHat) {
        this.idBaiHat = idBaiHat;
    }

    public String getTenCaSi() {
        return TenCaSi;
    }

    public void setTenCaSi(String tenCaSi) {
        TenCaSi = tenCaSi;
    }

    public String getTenBH() {
        return TenBH;
    }

    public void setTenBH(String tenBH) {
        TenBH = tenBH;
    }

    public String getBiaBH() {
        return BiaBH;
    }

    public void setBiaBH(String biaBH) {
        BiaBH = biaBH;
    }
}
