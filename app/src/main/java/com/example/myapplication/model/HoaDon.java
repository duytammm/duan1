package com.example.myapplication.model;

public class HoaDon {
    private String idHD, HoTenUser, MailUser, NgayLap, thoigian, idUser;
    private int dongia, trangthai;

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public HoaDon() {}

    public HoaDon(String idHD, String hoTenUser, String mailUser, String ngayLap, String thoigian, String idUser, int dongia,int trangthai) {
        this.idHD = idHD;
        HoTenUser = hoTenUser;
        MailUser = mailUser;
        NgayLap = ngayLap;
        this.thoigian = thoigian;
        this.idUser = idUser;
        this.dongia = dongia;
        this.trangthai = trangthai;
    }

    public String getIdHD() {
        return idHD;
    }

    public void setIdHD(String idHD) {
        this.idHD = idHD;
    }

    public String getHoTenUser() {
        return HoTenUser;
    }

    public void setHoTenUser(String hoTenUser) {
        HoTenUser = hoTenUser;
    }

    public String getMailUser() {
        return MailUser;
    }

    public void setMailUser(String mailUser) {
        MailUser = mailUser;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String ngayLap) {
        NgayLap = ngayLap;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }
}
