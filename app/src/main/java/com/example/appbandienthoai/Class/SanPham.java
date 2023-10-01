package com.example.appbandienthoai.Class;

public class SanPham {
    private String MASP;
    private String TENSP;
    private String PHANLOAI;
    private Integer SOLUONG;
    private String NOINHAP;
    private String NOIDUNG;
    private Double DONGIA;
    private Integer HINHANH;

    public SanPham(String MASP, String TENSP, String PHANLOAI, Integer SOLUONG, String NOINHAP, String NOIDUNG, Double DONGIA, Integer HINHANH) {
        this.MASP = MASP;
        this.TENSP = TENSP;
        this.PHANLOAI = PHANLOAI;
        this.SOLUONG = SOLUONG;
        this.NOINHAP = NOINHAP;
        this.NOIDUNG = NOIDUNG;
        this.DONGIA = DONGIA;
        this.HINHANH = HINHANH;
    }
    public SanPham(){

    }
    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getTENSP() {
        return TENSP;
    }

    public void setTENSP(String TENSP) {
        this.TENSP = TENSP;
    }

    public String getPHANLOAI() {
        return PHANLOAI;
    }

    public void setPHANLOAI(String PHANLOAI) {
        this.PHANLOAI = PHANLOAI;
    }

    public Integer getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(Integer SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public String getNOINHAP() {
        return NOINHAP;
    }

    public void setNOINHAP(String NOINHAP) {
        this.NOINHAP = NOINHAP;
    }

    public String getNOIDUNG() {
        return NOIDUNG;
    }

    public void setNOIDUNG(String NOIDUNG) {
        this.NOIDUNG = NOIDUNG;
    }

    public Double getDONGIA() {
        return DONGIA;
    }

    public void setDONGIA(Double DONGIA) {
        this.DONGIA = DONGIA;
    }

    public Integer getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(Integer HINHANH) {
        this.HINHANH = HINHANH;
    }
}
