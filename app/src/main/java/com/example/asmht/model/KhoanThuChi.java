package com.example.asmht.model;

public class KhoanThuChi {
    private int maKhoan;
    private String tenKhoan;
    private int tien;
    private String ngay;
    private int maLoai;
    private String tenLoai;


    public KhoanThuChi(int maKhoan, String tenKhoan, int tien, String ngay, int maLoai) {
        this.maKhoan = maKhoan;
        this.tenKhoan = tenKhoan;
        this.tien = tien;
        this.ngay = ngay;
        this.maLoai = maLoai;
    }

    public KhoanThuChi(int maKhoan, String tenKhoan, int tien, String ngay, int maLoai, String tenLoai) {
        this.maKhoan = maKhoan;
        this.tenKhoan = tenKhoan;
        this.tien = tien;
        this.ngay = ngay;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public KhoanThuChi(int tien, int maLoai) {
        this.tien = tien;
        this.maLoai = maLoai;
    }

    public int getMaKhoan() {
        return maKhoan;
    }

    public void setMaKhoan(int maKhoan) {
        this.maKhoan = maKhoan;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public String getTenKhoan() {
        return tenKhoan;
    }

    public void setTenKhoan(String tenKhoan) {
        this.tenKhoan = tenKhoan;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
