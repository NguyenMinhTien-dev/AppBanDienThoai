package com.example.appbandienthoai.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbandienthoai.Database;

import java.util.ArrayList;
import java.util.List;

public class QLAccount {
    private SQLiteDatabase db;
    private Database dbHelper;
    private Context context;

    public QLAccount(Context context) {
        this.context = context;
        dbHelper = new Database(context,"DBDienThoai.sqlite",null,1);
        db = dbHelper.getWritableDatabase();//cho phép ghi dữ liệu vào database
    }
    //Thêm dữ liệu
    public int ThemAccount(Account s){
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("TAIKHOAN",s.getTAIKHOAN());
        values.put("MATKHAU",s.getMATKHAU());
        values.put("QUYENHAN",s.getQUYENHAN());
        values.put("TEN",s.getTEN());
        values.put("SDT",s.getSDT());
        values.put("GMAIL",s.getGMAIL());
        values.put("DIACHI",s.getDIACHI());
        //thực thi Thêm
        long kq = db.insert("ACCOUNT",null, values);
        //Kiểm tra kết quả Insert
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
    //Hiển thị dữ liệu dạng string
    public List<String> getAllAccountToString(){
        List<String> ls = new ArrayList<>();//tạo danh sách rỗng
        //tạo con trỏ đọc bảng dữ liệu sản phẩm
        Cursor c = db.query("ACCOUNT",null,null,null,null,null,null);
        c.moveToFirst();//di chuyển con trỏ về bảng ghi đầu tiên
        //đọc
        while(c.isAfterLast()==false){
            Account s = new Account();//tạo đối tượng chứa dữ liệu
            s.setTAIKHOAN(c.getString(0));
            s.setMATKHAU(c.getString(1));
            s.setQUYENHAN(c.getString(2));
            s.setTEN(c.getString(3));
            s.setSDT(c.getString(4));
            s.setGMAIL(c.getString(5));
            s.setDIACHI(c.getString(6));
            //chuyển đối tượng thành chuỗi
            String chuoi =
                    "Tài khoản: "+ s.getTAIKHOAN()
                            +"\nMật khẩu: "+ s.getMATKHAU()
                            +"\nQuyền hạn: " + s.getQUYENHAN()
                            +"\nTên: " + s.getTEN()
                            +"\nSĐT: "+ s.getSDT()
                            +"\nGmail: "+ s.getGMAIL()
                            +"\nĐịa chỉ: "+ s.getDIACHI();
            //đưa chuỗi vào list
            ls.add(chuoi);
            c.moveToNext();//di chuyển đến bảng ghi tiếp theo
        }
        c.close();
        return ls;
    }
    public int XoaAccount(String TAIKHOAN){
        int kq = db.delete("ACCOUNT","TAIKHOAN=?",new String[]{TAIKHOAN});
        if(kq <= 0){
            return -1;//Xoa thất bại
        }
        return 1;//Xoa thành công
    }
    public int SuaAccount(Account s){
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("TAIKHOAN",s.getTAIKHOAN());
        values.put("MATKHAU",s.getMATKHAU());
        values.put("QUYENHAN",s.getQUYENHAN());
        values.put("TEN",s.getTEN());
        values.put("SDT",s.getSDT());
        values.put("GMAIL",s.getGMAIL());
        values.put("DIACHI",s.getDIACHI());
        //thực thi Thêm
        long kq = db.update("ACCOUNT",values,"TAIKHOAN=?", new String[]{s.getTAIKHOAN()});
        //Kiểm tra kết quả update
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
}
