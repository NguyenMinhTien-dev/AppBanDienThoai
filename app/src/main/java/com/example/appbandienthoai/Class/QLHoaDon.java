package com.example.appbandienthoai.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbandienthoai.Database;

import java.util.ArrayList;
import java.util.List;

public class QLHoaDon {
    private SQLiteDatabase db;
    private Database dbHelper;
    private Context context;

    public QLHoaDon(Context context) {
        this.context = context;
        dbHelper = new Database(context,"DBDienThoai.sqlite",null,1);
        db = dbHelper.getWritableDatabase();//cho phép ghi dữ liệu vào database
    }
    //Thêm dữ liệu
    public int ThemHoaDon(HoaDon s){
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("ID",s.getID());
        values.put("DATEORDER",s.getDATEORDER());
        values.put("TAIKHOANCUS",s.getTAIKHOANCUS());
        values.put("ADDRESSDELIVERRY",s.getADDRESSDELIVERRY());
        //thực thi Thêm
        long kq = db.insert("BILL",null, values);
        dbHelper.close();
        //Kiểm tra kết quả Insert
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
    //Hiển thị dữ liệu dạng string
    public List<String> getAllHoaDonToString(){
        List<String> ls = new ArrayList<>();//tạo danh sách rỗng
        //tạo con trỏ đọc bảng dữ liệu sản phẩm
        Cursor c = dbHelper.GetData("Select BILL.ID, BILL.DATEORDER, BILL.TAIKHOANCUS, ACCOUNT.DIACHI from BILL, ACCOUNT where BILL.TAIKHOANCUS = ACCOUNT.TAIKHOAN");
        while (c.moveToNext()){
            String chuoi = "Mã ID: "+ c.getString(0)
                    +"\nNgày tạo đơn: "+c.getString(1)
                    +"\nTài khoản mua: "+c.getString(2)
                    +"\nĐịa chỉ giao: " + c.getString(3);
            ls.add(chuoi);
        }
        return ls;
    }
    public ArrayList<HoaDon> getAllHoaDon(){
        ArrayList<HoaDon> hoaDons = new ArrayList<>();
        Cursor cursor = dbHelper.GetData("Select* from BILL");
        while (cursor.moveToNext()){
            HoaDon hoaDon = new HoaDon();
            hoaDon.setID(Integer.parseInt(cursor.getString(0)));
            hoaDon.setDATEORDER(cursor.getString(1));
            hoaDon.setTAIKHOANCUS(cursor.getString(2));
            hoaDon.setADDRESSDELIVERRY(cursor.getString(4));
            hoaDons.add(hoaDon);
        }
        return hoaDons;
    }
    public int XoaHoaDon(Integer ID){
        int kq = db.delete("BILL","ID=?",new String[]{String.valueOf(ID)});
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
    public int SuaHoaDon(HoaDon s) {
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("ID",s.getID());
        values.put("DATEORDER",s.getDATEORDER());
        values.put("TAIKHOANCUS",s.getTAIKHOANCUS());
        values.put("ADDRESSDELIVERRY",s.getADDRESSDELIVERRY());
        //thực thi Thêm
        long kq = db.update("BILL",values,"ID=?",new String[]{String.valueOf(s.getID())});
        //Kiểm tra kết quả Insert
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
}
