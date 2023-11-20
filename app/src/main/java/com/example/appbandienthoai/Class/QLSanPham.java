package com.example.appbandienthoai.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbandienthoai.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QLSanPham {
    private SQLiteDatabase db;
    private Database dbHelper;
    private Context context;

    public QLSanPham(Context context) {
        this.context = context;
        dbHelper = new Database(context,"DBFlowerShop.sqlite",null,1);
        db = dbHelper.getWritableDatabase();//cho phép ghi dữ liệu vào database
    }
    //Thêm dữ liệu
    public int ThemSanPham(SanPham s){
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("MASP",s.getMASP());
        values.put("TENSP",s.getTENSP());
        values.put("PHANLOAI",s.getPHANLOAI());
        values.put("SOLUONG",s.getSOLUONG());
        values.put("NOINHAP",s.getNOINHAP());
        values.put("NOIDUNG",s.getNOIDUNG());
        values.put("DONGIA",s.getDONGIA());
        values.put("HINHANH",s.getHINHANH());
        values.put("NGAYNHAP", LocalDate.now().toString());
        //thực thi Thêm
        long kq = db.insert("SANPHAM",null, values);
        //Kiểm tra kết quả Insert
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
    //Hiển thị dữ liệu dạng string
    public List<String> getAllSanPhamToString(){
        List<String> ls = new ArrayList<>();//tạo danh sách rỗng
        //tạo con trỏ đọc bảng dữ liệu sản phẩm
        Cursor c = db.query("SANPHAM",null,null,null,null,null,null);
        c.moveToFirst();//di chuyển con trỏ về bảng ghi đầu tiên
        //đọc
        while(c.isAfterLast()==false){
            SanPham s = new SanPham();//tạo đối tượng chứa dữ liệu
            s.setMASP(c.getString(0));//đọc dữ liệu trường MASP và đưa vào đối tượng
            s.setTENSP(c.getString(1));//đọc dữ liệu trường TENSP và đưa vào đối tượng
            s.setPHANLOAI(c.getString(2));//đọc dữ liệu trường PHANLOAI và đưa vào đối tượng
            s.setSOLUONG(c.getInt(3));//đọc dữ liệu trường SOLUONG và đưa vào đối tượng
            s.setNOINHAP(c.getString(4));//đọc dữ liệu trường NOINHAP và đưa vào đối tượng
            s.setNOIDUNG(c.getString(5));//đọc dữ liệu trường NOIDUNG và đưa vào đối tượng
            s.setDONGIA(c.getDouble(6));//đọc dữ liệu trường DONGIA và đưa vào đối tượng
            s.setHINHANH(c.getInt(7));//đọc dữ liệu trường HINHANH và đưa vào đối tượng
            //chuyển đối tượng thành chuỗi
            String chuoi =
                    "Mã sản phẩm: " + s.getMASP()
                            +"\nTên sản phẩm: " + s.getTENSP()
                            +"\nPhân loại: " + s.getPHANLOAI()
                            +"\nSố lượng: " + s.getSOLUONG()
                            +"\nGiới thiệu: " + s.getNOINHAP()
                            +"\nChi tiết sản phẩm: " + s.getNOIDUNG()
                            +"\nĐơn giá: " + s.getDONGIA()
                            +"\nMã ảnh: " +s.getHINHANH();
            //đưa chuỗi vào list
            ls.add(chuoi);
            c.moveToNext();//di chuyển đến bảng ghi tiếp theo
        }
        c.close();
        return ls;
    }
    public int XoaSanPham(String MASP){
        int kq = db.delete("SANPHAM","MASP=?",new String[]{MASP});
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
    public int SuaSanPham(SanPham s){
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("MASP",s.getMASP());
        values.put("TENSP",s.getTENSP());
        values.put("PHANLOAI",s.getPHANLOAI());
        values.put("SOLUONG",s.getSOLUONG());
        values.put("NOINHAP",s.getNOINHAP());
        values.put("NOIDUNG",s.getNOIDUNG());
        values.put("DONGIA",s.getDONGIA());
        values.put("HINHANH",s.getHINHANH());
        //thực thi Sửa
        long kq = db.update("SANPHAM",values, "MASP=?",new String[]{s.getMASP()});
        //Kiểm tra kết quả Insert
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
}
