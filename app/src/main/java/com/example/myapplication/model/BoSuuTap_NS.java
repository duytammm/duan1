package com.example.myapplication.model;

public class BoSuuTap_NS {
    private String isBst, idND, idNS;
    public BoSuuTap_NS(String isBst, String idND, String idcs) {
        this.isBst = isBst;
        this.idND = idND;
        this.idNS = idcs;
    }

    public BoSuuTap_NS() {
    }

    public String getIdNS() {
        return idNS;
    }

    public void setIdNS(String idNS) {
        this.idNS = idNS;
    }

    public String getIsBst() {
        return isBst;
    }

    public void setIsBst(String isBst) {
        this.isBst = isBst;
    }

    public String getIdND() {
        return idND;
    }

    public void setIdND(String idND) {
        this.idND = idND;
    }
}

