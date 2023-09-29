package com.example.appbandienthoai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.appbandienthoai.Class.Account;
import com.example.appbandienthoai.Class.GioHang;

import java.time.LocalDate;
import java.util.Calendar;

public class Database extends SQLiteOpenHelper {
    Context context;
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public Database(Context context) {
        super(context, "DBDienThoai.sqlite", null, 1);
    }

    //Truy vấn không trả kết quả
    //Truy vấn không trả kết quả là truy vấn thêm, xóa, sửa trên database
    public void WriteQuery(String content){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(content);
    }
    //Truy vấn trả kết quả (Select)
    public Cursor GetData(String content){
        SQLiteDatabase db =  getReadableDatabase();
        return db.rawQuery(content, null);
    }
    //Hàm AddRole, Thêm dữ liệu vào bảng "ROLE"

    @Override
    public void onCreate(SQLiteDatabase db) {
        //region Tạo bảng ROLE: Quyền hạn
        db.execSQL("CREATE TABLE IF NOT EXISTS [ROLE] (" +
                "QUYENHAN VARCHAR PRIMARY KEY NOT NULL," +
                "NOIDUNG Text NOT NULL)");
        //Thêm dữ liệu vào bảng [ROLE]
        String s = "Insert into [ROLE] values " +
                "('admin', 'Quản trị viên');";
        db.execSQL("Insert into [ROLE] values" +
                "('admin', 'Quản trị viên')," +
                "('customer', 'Khách hàng')");
        //endregion

        //region Tạo bảng ACCOUNT: chứa các tài khoản
        db.execSQL("CREATE TABLE IF NOT EXISTS ACCOUNT (\n" +
                "\tTAIKHOAN VARCHAR PRIMARY KEY NOT NULL,\n" +
                "\tMATKHAU VARCHAR NOT NULL,\n" +
                "\tQUYENHAN VARCHAR NOT NULL, \n" +
                "\tTEN VARCHAR,\n" +
                "\tSDT VARCHAR,\n" +
                "\tGMAIL VARCHAR,\n" +
                "\tDIACHI VARCHAR,\n" +
                "\tFOREIGN KEY (QUYENHAN) REFERENCES [ROLE](QUYENHAN)\n" +
                ");");
        //Thêm tài khoản admin và khách hàng mẫu để test
        db.execSQL("Insert into ACCOUNT values " +
                "('123', '123', 'admin', 'Nguyen Van A', '0924939352', 'voquinamit1@gmail.com', 'thailan'), " +
                "('1234', '1234', 'customer', 'Nguyen Thi B', '0334379439', '', '119');");
        //endregion

        //region Tạo bảng CATEGORY: Phân loại sản phẩm
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS [CATEGORY] (" +
                        "NAME VARCHAR PRIMARY KEY NOT NULL, " +
                        "NOIDUNG VARCHAR);"
        );
        //Thêm một số CATEGORY
        db.execSQL("Insert into [CATEGORY] values " +
                "('IOS', 'IPHONE'), " +
                "('ANDROID', 'ANDROID'), " +
                "('CAC LOAI KHAC', 'CAC LOAI KHAC')");
        //endregion

        //region Tạo bảng SẢN PHẨM: Lưu trữ sản phẩm (hoa)
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS SANPHAM (\n" +
                        "MASP VARCHAR PRIMARY KEY NOT NULL,\n" +
                        "TENSP VARCHAR NOT NULL, \n" +
                        "PHANLOAI VARCHAR NOT NULL, \n" +
                        "SOLUONG INTEGER NOT NULL,\n" +
                        "NOINHAP VARCHAR NOT NULL,\n" +
                        "NOIDUNG VARCHAR, \n" +
                        "DONGIA REAL CHECK(DONGIA > 0) NOT NULL,\n" +
                        "HINHANH INTEGER NOT NULL,\n" +
                        "NGAYNHAP date,\n" +
                        "FOREIGN KEY (PHANLOAI) REFERENCES [CATEGORY](NAME)" +
                        ");"
        );
        String date = "2023-04-02";
        db.execSQL("Insert into SANPHAM values \n" +
                "('IP001', 'You Look Gorgeous', 'COMBO', 10, 'Đà Lạt', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 9500000, "+R.drawable.imgpro_you_look_gorgeous+", '"+date+"'), \n " +
                "('CB002', 'Hello Sweetheart', 'COMBO', 10, 'Đà Lạt', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 5000000, "+R.drawable.imgpro_hello_sweetheart+", '"+date+"'), \n" +
                "('CB003', 'Strawberry Sundea', 'COMBO', 10, 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 9500000, "+R.drawable.imgpro_strawberry_sundea+", '"+date+"'), \n" +
                "('CB004', 'Wintry Wonder', 'COMBO', 10, 'Đà Lạt', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 5000000, "+R.drawable.imgpro_wintry_wonder+", '"+date+"'),  \n" +
                "('CB005', 'Hopeful Romantic', 'COMBO', 10, 'Đà Lạt', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 9500000, "+R.drawable.imgpro_hopeful_romantic+", '"+date+"'),  \n" +
                "('TL001', 'All In Bloom', 'TULIP', 10, 'TPHCM', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 1500000, "+R.drawable.imgpro_all_in_bloom+", '"+date+"'),  \n" +
                "('TL002', 'Blue Day', 'TULIP', 10, 'TPHCM', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 1500000, "+R.drawable.imgpro_blue_day+", '"+date+"'),  \n" +
                "('TL003', 'Red Love', 'TULIP', 10, 'TPHCM', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 1500000, "+R.drawable.imgpro_red_love+", '"+date+"'),  \n" +
                "('TL004', 'Pure White', 'TULIP', 10, 'TPHCM', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 1500000, "+R.drawable.imgpro_pure_white+", '"+date+"'),  \n" +
                "('TL005', 'Pastel Tulip', 'TULIP', 10, 'TPHCM', 'Tương tự như iPhone 12, siêu phẩm năm nay vẫn giữ nguyên bộ khung vuông vắn sang trọng. Chiếc điện thoại này sẽ được bổ sung thêm phiên bản màu hồng hoàn toàn mới. Ngoài ra, các màu cũ như đen, trắng, xanh navy, đỏ vẫn được duy trì. Thân máy iPhone 13 256GB được chế tác bằng kim loại kết hợp với kính cường lực chắc chắn. Các góc cạnh của máy vuông vắn với các góc được bo tròn. Vì vậy, thiết bị có vẻ ngoài hiện đại, vô cùng tinh tế và cũng dễ cầm nắm. Giống như các sản phẩm cùng loại, iP 13 đạt chức năng chống nước, chống bụi và chống xước IP68.', 1000000, "+R.drawable.imgpro_pastel_tulip+", '"+date+"'),  \n" +
                "('BH001', 'Hope For Love', 'VASE', 0, 'TPHCM', 'ASD', 3000000, "+R.drawable.imgpro_hope_for_love+", '"+date+"'),  \n" +
                "('BH002', 'Big Rose', 'VASE', 10, 'TPHCM', 'ASD', 3000000, "+R.drawable.imgpro_big_rose+", '"+date+"');");

        //region Tạo bảng BILL: Lưu trữ các hóa đơn của người mua
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS BILL (\n" +
                        "   ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "   DATEORDER date NOT NULL,\n" +
                        "   TAIKHOANCUS VARCHAR NOT NULL,\n" +
                        "   NAMECUS VARCHAR NOT NULL,\n" +
                        "   ADDRESSDELIVERRY VARCHAR NOT NULL,\n" +
                        "   SDT VARCHAR not null);"
        );
        //endregion

        //region Tạo bảng Bill_Detail: Chi tiết hóa đơn
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS BILLDETAIL (\n" +
                        "    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    MASP VARCHAR NOT NULL,\n" +
                        "    IDORDER   INTEGER not NULL,\n" +
                        "    IDVoucher VARCHAR not null, \n" +
                        "    QUANTITY  INTEGER check(QUANTITY > 0) not NULL,\n" +
                        "    UNITPRICE Real check(UNITPRICE > 0) not NULL,\n" +
                        "    TOTALPRICE Real check (TOTALPRICE > 0) not Null,\n" +
                        "    FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP),\n" +
                        "    FOREIGN KEY (IDORDER) REFERENCES BILL(ID), \n" +
                        "    FOREIGN KEY (IDVoucher) REFERENCES VOUCHER(MAVOUCHER)" +
                        ");"
        );

        //endregion

        //region Tạo bảng VOUCHER: Lưu trữ các voucher hiện có
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS VOUCHER(\n" +
                        "\tMAVOUCHER VARCHAR PRIMARY KEY not null,\n" +
                        "\tNOIDUNG TEXT," +
                        "\tHSD date," +
                        "\tGIAM INTEGER DEFAULT(1) Check(GIAM >= 0)\n" +
                        ");"
        );
        int year = LocalDate.now().getYear();
        db.execSQL("Insert into VOUCHER values \n" +
                "('SALET5', 'Sale tháng 5', '2023-05-31' , 10.0/100), \n" +
                "('SALENEW', 'Sale mới', '2023-05-31' , 30.0/100), \n" +
                "('TVBTRAN', 'Sale báo', '2023-02-31' , 15.0/100), \n" +
                "('TVBTRAN19T2', 'Vẫn là báo sale', '2023-04-31' , 20.0/100)");

        //endregion

        //region Tạo bảng VOUCHER DETAIL: Chi tiết voucher sử dụng cho một hoặc nhiều sản phẩm cụ thể
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS VOUCHER_DETAIL(\n" +
                        "MAVOUCHER VARCHAR," +
                        "MASP VARCHAR NOT NULL," +
                        "FOREIGN KEY (MAVOUCHER) REFERENCES VOUCHER(MAVOUCHER)," +
                        "  FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP)" +
                        ");"
        );
        db.execSQL("Insert into VOUCHER_DETAIL values " +
                "('SALET5', 'CB001'), " +
                "('SALET5', 'CB002'), " +
                "('SALET5', 'CB003') ");
        //endregion
        //region Tạo bảng CARTLIST: Lưu trữ giỏ hàng của người dùng, tự động cập nhật khi người dùng đăng nhập lại
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS CARTLIST (\n" +
                        "\tIDCARTLIST   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "\tIDCUS        VARCHAR,\n" +
                        "\tIDSANPHAM    VARCHAR NOT NULL,\n" +
                        "\tIDVoucher    VARCHAR,\n" +
                        "\tSOLUONG      INTEGER CHECK(SOLUONG > 0) NOT NULL," +
                        "\tDONGIA       REAL,\n" +
                        "\tFOREIGN KEY (IDCUS) REFERENCES ACCOUNT(TAIKHOAN),\n" +
                        "\tFOREIGN KEY (IDSANPHAM) REFERENCES SANPHAM(MASP), \n" +
                        "\tFOREIGN KEY (IDVoucher) REFERENCES VOUCHER(MAVOUCHER)\n" +
                        ")"
        );
        //endregion
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //Thêm nội dung vào db
    private boolean CheckExists(String PrimaryColumn, String TableName){
        Cursor i = this.GetData("Select* from " + TableName);
        while (i.moveToNext()){
            if (PrimaryColumn.equals(i.getString(0))){
                return false;
            }
        }
        return true;
    }
    public boolean AddAccount(String taikhoan, String matkhau, String quyenhan, String ten, String sdt, String gmail,String diachi){
        boolean check = CheckExists(taikhoan, "Account");
        if (check){
            this.WriteQuery("Insert into ACCOUNT Values" +
                    "('" + taikhoan + "', '" + matkhau + "', '" + quyenhan + "', '" + ten + "', '" + sdt + "', '" + gmail + "','" + diachi + "');");
        }
        return check;
    }
    public boolean AddRole(String role, String content){
        boolean check = CheckExists(role, "[ROLE]");
        if (check){
            this.WriteQuery("Insert into [ROLE] Values" +
                    "('" + role + "', '" + content + "');");
        }
        return check;
    }
    public boolean AddProduct(String MASP, String TENSP, String PHANLOAI, Integer SOLUONG, String NOINHAP, String NOIDUNG, double DONGIA, int HINHANH){
        boolean check = CheckExists(MASP, "SANPHAM");
        if (check){
            LocalDate currentDate = LocalDate.now(); //định dạng ngày sẽ là "YYYY/MM/dd"
            this.WriteQuery("Insert into SANPHAM Values" +
                    "('" + MASP + "', '" + TENSP + "', '" + PHANLOAI + "', '" + SOLUONG + "', '" + NOINHAP + "', '" + NOIDUNG + "', '" + DONGIA + "', '" + HINHANH + "', '" + currentDate + "');");
        }
        return check;
    }
    public boolean AddCategory(String NAME, String CONTENT){
        boolean check = CheckExists(NAME, "[CATEGORY]");
        if (check){
            this.WriteQuery("Insert into [CATEGORY] Values" +
                    "('" + NAME + "', '" + CONTENT + "');");
        }
        return check;
    }
    public boolean AddBill(String TAIKHOANCUS, String ADDRESSDELIVERRY) {
        try {
            Calendar c = Calendar.getInstance();
            String DATEORDER = Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1) + "/" + Integer.toString(c.get(Calendar.YEAR));
            SQLiteDatabase db = getReadableDatabase();
            this.WriteQuery("Insert into [CATEGORY] (DATEORDER, TAIKHOANCUS, ADDRESSDELIVERRY) Values" +
                    "('" + DATEORDER + "', '" + TAIKHOANCUS + "', '" + ADDRESSDELIVERRY + "');");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean AddVoucher(String MAVOUCHER, String NOIDUNG, String HSD, double GIAM){
        boolean check = CheckExists(MAVOUCHER, "VOUCHER");
        if (check){
            this.WriteQuery("Insert into VOUCHER values" +
                    "('"+ MAVOUCHER +"', '"+ NOIDUNG +"', '"+ HSD +"', "+ GIAM +");");
        }
        return check;
    }
    public boolean AddVoucherProduct(String MAVOUCHER, String MASP){
        try{
            Cursor cursor = this.GetData(
                    "Select*" +
                            " From VOUCHER_DETAIL" +
                            " Where MAVOUCHER = '" + MAVOUCHER + "'" +
                            " and MASP = '" + MASP + "'"
            );
            if (!cursor.moveToFirst()){
                this.WriteQuery("Insert into VOUCHER_DETAIL values" +
                        "('" + MAVOUCHER + "', '" + MASP + "')");
                return true;
            }
            return false;
        }
        catch (Exception e){
            return false;
        }
    }
    public long addCartList(GioHang gioHang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IDCUS", gioHang.getIdCus());
        values.put("IDSANPHAM", gioHang.getIdSanPham());
        values.put("IDVoucher", gioHang.getIdVoucher());
        values.put("SOLUONG", gioHang.getSoLuong());
        values.put("DONGIA", gioHang.getDonGia());
        return db.insert("CARTLIST", "IDCUS", values);
    }
    public long updateCartList(GioHang gioHang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IDCARTLIST", gioHang.getIdCartList());
        values.put("IDCUS", gioHang.getIdCus());
        values.put("IDSANPHAM", gioHang.getIdSanPham());
        values.put("IDVoucher", gioHang.getIdVoucher());
        values.put("SOLUONG", gioHang.getSoLuong());
        values.put("DONGIA", gioHang.getDonGia());

        long kq = db.update("CARTLIST", values, "IDCARTLIST=?",  new String[]{gioHang.getIdCartList().toString()});
        db.close();
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }

    public void updateUser(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//            values.put("TAIKHOAN",account.getTAIKHOAN());
//            values.put("MATKHAU",account.getMATKHAU());
//            values.put("QUYENHAN",account.getQUYENHAN());
        values.put("TEN", account.getTEN());
        values.put("SDT", account.getSDT());
        values.put("GMAIL", account.getGMAIL());
        values.put("DIACHI",account.getDIACHI());

        long check = db.update("ACCOUNT",values,"TAIKHOAN=?",new String[]{account.getTAIKHOAN().toString()});
        //long check = db.insert("ACCOUNT",null,values);
//            if (check != -1){
//                Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show();
//                db.close();
//
//            }else {
//                Toast.makeText(context, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
//            }
    }

    public Cursor getUser(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from ACCOUNT",null);
        return cursor;
    }

    public long changePassword(String user, String oldPass, String newPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MATKHAU", newPass);
        return db.update("ACCOUNT", values, "TAIKHOAN=?", new String[]{user});
    }
}
