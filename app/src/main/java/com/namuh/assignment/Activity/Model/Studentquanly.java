package com.namuh.assignment.Activity.Model;

public class Studentquanly {
    private int msvv;
    private String ten;
    private String ngay;
    private int id_class;
    private String sdt;
    private String email;

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Studentquanly(int msvv, String ten, String ngay, int id_class, String sdt, String email) {
        this.msvv = msvv;
        this.ten = ten;
        this.ngay = ngay;
        this.id_class = id_class;
        this.sdt = sdt;
        this.email = email;
    }

    public void setMsvv(int msvv) {
        this.msvv = msvv;
    }

    public int getId_class() {
        return id_class;
    }

    public void setId_class(int id_class) {
        this.id_class = id_class;
    }


    public int getMsvv() {
        return msvv;
    }

    public void setId(int msvv) {
        this.msvv = msvv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }


}
