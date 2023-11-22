package com.example.myapplication.model;

public class User {
    private String idUser;
    private String Email;
    private String MatKhau;
    private String HotenUser;
    private String Sdt;
    private int TrangThai;
    private int Role;
    private String Avatar;

    public User() {
    }

    public User(String idUser, String email, String matKhau, String hotenUser, String sdt, int trangThai, int role, String avatar) {
        this.idUser = idUser;
        Email = email;
        MatKhau = matKhau;
        HotenUser = hotenUser;
        Sdt = sdt;
        TrangThai = trangThai;
        Role = role;
        Avatar = avatar;
    }

    public User(String id, String email, String hotenUser, String sdt, String matKhau) {
        idUser = id;
        Email = email;
        HotenUser = hotenUser;
        Sdt = sdt;
        MatKhau = matKhau;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getHotenUser() {
        return HotenUser;
    }

    public void setHotenUser(String hotenUser) {
        HotenUser = hotenUser;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }
}
