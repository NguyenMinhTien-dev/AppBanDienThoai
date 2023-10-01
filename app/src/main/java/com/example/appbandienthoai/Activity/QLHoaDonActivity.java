package com.example.appbandienthoai.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbandienthoai.Adapter.HoaDonAdapter;
import com.example.appbandienthoai.Class.HoaDon;
import com.example.appbandienthoai.Class.QLHoaDon;
import com.example.appbandienthoai.Class.QLSanPham;
import com.example.appbandienthoai.Database;
import com.example.appbandienthoai.R;

import java.util.ArrayList;
import java.util.List;

public class QLHoaDonActivity extends AppCompatActivity {
    android.widget.ListView ListView;
    Toolbar mToolBar;
    QLHoaDon qlHoaDon;
    ArrayList<HoaDon> listHD = new ArrayList<>();
    List<String> list = new ArrayList<>();
    Database databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhoa_don);
        ListView = (android.widget.ListView) findViewById(R.id.listHD);
        mToolBar =(Toolbar) findViewById(R.id.toolbarHD);
        databaseHelper = new Database(this, "DBDienThoai.sqlite", null, 1);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        setSupportActionBar(mToolBar);

        //hiển thị dữ liệu khi chạy chương trình
        qlHoaDon = new QLHoaDon(QLHoaDonActivity.this);
        HienThiDuLieu();
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QLHoaDonActivity.this);
                builder.setTitle("ID HÓA ĐƠN: " + listHD.get(position).getID());
                String query = "Select SANPHAM.TENSP " +
                        "From BILLDETAIL, SANPHAM " +
                        "Where BILLDETAIL.MASP = SANPHAM.MASP " +
                        "and BILLDETAIL.IDORDER = " + listHD.get(position).getID();
                Cursor cursor = databaseHelper.GetData(query);
                String sanpham = "Sản phẩm đã mua: \n";
                while (cursor.moveToNext()){
                    sanpham += cursor.getString(0) + "\n";
                }
                builder.setMessage(sanpham);
                builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqLiteDatabase.delete("BILLDETAIL", "IDORDER=?", new String[]{listHD.get(position).getID().toString()});
                        long i = sqLiteDatabase.delete("BILL", "ID=?", new String[]{listHD.get(position).getID().toString()});
                        if (i != 0)
                            Toast.makeText(QLHoaDonActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(QLHoaDonActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        HienThiDuLieu();
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadmin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.KH:
                Intent KH = new Intent(QLHoaDonActivity.this, QLAccountActivity.class);
                startActivity(KH);
                return true;
            case R.id.QL:
                Intent QL = new Intent(QLHoaDonActivity.this, AdminActivity.class);
                startActivity(QL);
                return true;
            case R.id.SanPham:
                Intent HD = new Intent(QLHoaDonActivity.this, QLSanPham.class);
                startActivity(HD);
                return true;
            case R.id.Logout:
                Intent Logout = new Intent(QLHoaDonActivity.this, DangNhapActivity.class);
                startActivity(Logout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void HienThiDuLieu(){
        if (listHD.size() != 0){
            listHD.removeAll(listHD);
        }
        listHD = qlHoaDon.getAllHoaDon();
        HoaDonAdapter adapter =new HoaDonAdapter(QLHoaDonActivity.this, listHD);
        ListView.setAdapter(adapter);
    }
}