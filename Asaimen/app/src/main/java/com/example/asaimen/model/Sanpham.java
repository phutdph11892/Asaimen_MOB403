package com.example.asaimen.model;

import java.io.Serializable;

public class Sanpham implements Serializable {//truyen mang thi implements Serializable
    public int id;
    public String TenSP;
    public Integer GiaSP;
    public String HinhanhSP;
    public String MotaSP;
    public int IDSanpham;

    public Sanpham() {
    }

    public Sanpham(int id, String tenSP, Integer giaSP, String hinhanhSP, String motaSP, int IDSanpham) {
        this.id=id;
        TenSP=tenSP;
        GiaSP=giaSP;
        HinhanhSP=hinhanhSP;
        MotaSP=motaSP;
        this.IDSanpham=IDSanpham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP=tenSP;
    }

    public Integer getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(Integer giaSP) {
        GiaSP=giaSP;
    }

    public String getHinhanhSP() {
        return HinhanhSP;
    }

    public void setHinhanhSP(String hinhanhSP) {
        HinhanhSP=hinhanhSP;
    }

    public String getMotaSP() {
        return MotaSP;
    }

    public void setMotaSP(String motaSP) {
        MotaSP=motaSP;
    }

    public int getIDSanpham() {
        return IDSanpham;
    }

    public void setIDSanpham(int IDSanpham) {
        this.IDSanpham=IDSanpham;
    }
}
