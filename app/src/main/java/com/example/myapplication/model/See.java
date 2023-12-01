package com.example.myapplication.model;

public class See {
    private String idUser;
    private String idSee;
    private int idBH;

    public See(String idUser, String idSee, int idBH) {
        this.idUser = idUser;
        this.idSee = idSee;
        this.idBH = idBH;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdSee() {
        return idSee;
    }

    public void setIdSee(String idSee) {
        this.idSee = idSee;
    }

    public int getIdBH() {
        return idBH;
    }

    public void setIdBH(int idBH) {
        this.idBH = idBH;
    }
}
