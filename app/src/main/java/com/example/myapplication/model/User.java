package com.example.myapplication.model;

public class User {
    private int idUser, TrangThai, Role;
    private String Email, MatKhau, HotenUser, Sdt, NgaySinh,Avatar;

    public User() {
    }

    public User(String email, String matKhau) {
        Email = email;
        MatKhau = matKhau;
    }

    public User(int idUser, int trangThai, int role, String email, String matKhau, String hotenUser, String sdt, String ngaySinh, String avatar) {
        this.idUser = idUser;
        TrangThai = trangThai;
        Role = role;
        Email = email;
        MatKhau = matKhau;
        HotenUser = hotenUser;
        Sdt = sdt;
        NgaySinh = ngaySinh;
        Avatar = avatar;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
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

    public String getHoTenUser() {
        return HotenUser;
    }

    public void setHoTenUser(String hoTenUser) {
        HotenUser = hoTenUser;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }
}
