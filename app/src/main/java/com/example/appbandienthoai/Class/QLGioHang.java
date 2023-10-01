package com.example.appbandienthoai.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbandienthoai.Database;
import com.example.appbandienthoai.GioHang;

public class QLGioHang {
    private SQLiteDatabase db;
    private Database databaseHelper;
    private Context context;
    public QLGioHang(Context context) {
        this.context = context;
        databaseHelper = new Database(context,"DBDienThoai.sqlite",null,1);
        db = databaseHelper.getWritableDatabase();//cho phép ghi dữ liệu vào database
    }
    public long themGioHang(GioHang gioHang){
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IDCUS", gioHang.getIdCus());
        values.put("IDSANPHAM", gioHang.getIdSanPham());
        values.put("IDVoucher", gioHang.getIdVoucher());
        values.put("SOLUONG", gioHang.getSoLuong());
        return db.insert("CARTLIST", null, values);
    }
}
