package com.example.appbandienthoai.Class;

import androidx.annotation.NonNull;

public class HoaDon {
    private Integer ID;
    private String DATEORDER;
    private String TAIKHOANCUS;
    private  String ADDRESSDELIVERRY;

    public HoaDon(Integer ID, String DATEORDER, String TAIKHOANCUS, String ADDRESSDELIVERRY) {
        this.ID = ID;
        this.DATEORDER = DATEORDER;
        this.TAIKHOANCUS = TAIKHOANCUS;
        this.ADDRESSDELIVERRY = ADDRESSDELIVERRY;
    }

    public String getADDRESSDELIVERRY() {
        return ADDRESSDELIVERRY;
    }

    public void setADDRESSDELIVERRY(String ADDRESSDELIVERRY) {
        this.ADDRESSDELIVERRY = ADDRESSDELIVERRY;
    }

    public HoaDon(){}

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDATEORDER() {
        return DATEORDER;
    }

    public void setDATEORDER(String DATEORDER) {
        this.DATEORDER = DATEORDER;
    }

    public String getTAIKHOANCUS() {
        return TAIKHOANCUS;
    }

    public void setTAIKHOANCUS(String TAIKHOANCUS) {
        this.TAIKHOANCUS = TAIKHOANCUS;
    }
    @NonNull
    @Override
    public String toString() {
        return getID() + " - " + getADDRESSDELIVERRY() + " - \n" + getTAIKHOANCUS() + " - " + getDATEORDER();
    }
}
