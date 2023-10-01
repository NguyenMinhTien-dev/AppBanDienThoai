package com.example.appbandienthoai.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbandienthoai.Adapter.PickImageAdapter;
import com.example.appbandienthoai.Class.DienThoaiMoi;
import com.example.appbandienthoai.Class.QLSanPham;
import com.example.appbandienthoai.Class.SanPham;
import com.example.appbandienthoai.Database;
import com.example.appbandienthoai.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class QLSanPhamActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Database dbHelper;
    ListView spListView;
    Toolbar mToolBar;
    EditText edtMa, edtTen, edtGia, edtSL, edtND, edtNN;
    TextView edtHA;
    String cate;
    Button btnXoa,btnSua,btnThem,btnchonanh;
    ImageView imageViewSP;
    QLSanPham qlSanPham;
    Spinner spinnercate;
    List<String> list = new ArrayList<>();
    private static final int PICK_IMAGE_REQUEST = 1; // replace 1 with any non-zero integer value
    ArrayList<String> dataList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlsan_pham);
        //ánh xạ
        btnchonanh = (Button) findViewById(R.id.btn_selectimg);
        imageViewSP = (ImageView) findViewById(R.id.imgSP);
        edtMa = (EditText) findViewById(R.id.edtMSP);
        edtTen = (EditText) findViewById(R.id.edtTSP);
        edtGia = (EditText) findViewById(R.id.edtGia);
        edtSL = (EditText) findViewById(R.id.edtSL);
        edtNN = (EditText) findViewById(R.id.edtNN);
        edtND = (EditText) findViewById(R.id.edtND);
        edtHA = (TextView) findViewById(R.id.tvHA);
        btnXoa = (Button) findViewById(R.id.btnXoa);
        btnSua = (Button) findViewById(R.id.btnSua);
        btnThem = (Button) findViewById(R.id.btnThem);
        spinnercate = (Spinner) findViewById(R.id.spnCategoty);
        spListView = (ListView) findViewById(R.id.listSP);
        mToolBar = (Toolbar) findViewById(R.id.toolbarSP);
        setSupportActionBar(mToolBar);

        //hiển thị dữ liệu khi chạy chương trình
        qlSanPham = new QLSanPham(QLSanPhamActivity.this);
        list = qlSanPham.getAllSanPhamToString();
        ArrayAdapter adapter = new ArrayAdapter(QLSanPhamActivity.this, android.R.layout.simple_list_item_1, list);
        spListView.setAdapter(adapter);

        //SET ONCLICKITEMLISTENER CỦA LISTVIEW ACCOUNT
        spListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getSanPham = list.get(position).split("\n")[0].split(" ")[3].trim();
                // Truy vấn cơ sở dữ liệu để lấy thông tin chi tiết của sản phẩm
                Toast.makeText(getApplicationContext(), getSanPham, Toast.LENGTH_SHORT).show();
                Cursor cursor = dbHelper.GetData("SELECT* FROM SANPHAM WHERE SANPHAM.MASP = '" + getSanPham + "'");
                if (!cursor.moveToFirst()) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    cursor.moveToFirst();
                    // Hiển thị thông tin chi tiết sản phẩm lên các EditText và Spinner
                    edtMa.setText(cursor.getString(cursor.getColumnIndex("MASP")));
                    edtTen.setText(cursor.getString(cursor.getColumnIndex("TENSP")));
                    int pos = 0;
                    for (int i = 0; i < dataList.size(); i++){
                        if (dataList.get(i).equals(cursor.getString(2))){
                            pos = i;
                            break;
                        }
                    }
                    spinnercate.setSelection(pos);
                    edtSL.setText(cursor.getString(cursor.getColumnIndex("SOLUONG")));
                    edtNN.setText(cursor.getString(cursor.getColumnIndex("NOINHAP")));
                    edtND.setText(cursor.getString(cursor.getColumnIndex("NOIDUNG")));
                    edtGia.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("DONGIA"))));
                    edtHA.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("HINHANH"))));
                    imageViewSP.setImageResource(cursor.getInt(7));
                    cursor.close();
                }
            }
        });
        //sk ấn nút chọn ảnh
        btnchonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một dialog cho phép người dùng chọn ảnh từ tệp drawable
                AlertDialog.Builder builder = new AlertDialog.Builder(QLSanPhamActivity.this);
                builder.setTitle("CHỌN ẢNH");

                // Lấy danh sách tên của các tệp drawable
                Field[] drawables = R.drawable.class.getFields();
                final ArrayList<String> drawableNames = new ArrayList<>();

                for (Field f : drawables) {
                    try {
                        if (f.getName().substring(0, 6).equals("imgpro")){
                            drawableNames.add(f.getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Tạo danh sách chọn ảnh từ danh sách tên tệp drawable
                PickImageAdapter adapter = new PickImageAdapter(QLSanPhamActivity.this,
                        R.layout.item_pick_image, drawableNames);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lấy ID của ảnh được chọn và hiển thị nó trên ImageView
                        int resourceId = getResources().getIdentifier(drawableNames.get(which), "drawable", getPackageName());
                        imageViewSP.setImageResource(resourceId);
                        // Truyền ID của ảnh vào TextView
                        edtHA.setText(String.valueOf(resourceId));
                    }
                });

                builder.show();
            }
        });
        //hiển thị Category trong spinner
        dbHelper = new Database(this, "DBDienThoai.sqlite", null, 1);
        db = dbHelper.getWritableDatabase();//cho phép ghi dữ liệu vào database
        dataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM CATEGORY", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex("NAME"));
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        ArrayAdapter<String> adaptercate = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);
        adaptercate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercate.setAdapter(adaptercate);//lưu dữ liệu chọn từ spinner vào database
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //đưa dữ liệu vào đối tượng
                if (!edtMa.getText().toString().isEmpty() && !edtTen.getText().toString().isEmpty() && !edtSL.getText().toString().isEmpty()
                        && !edtGia.getText().toString().isEmpty() && !edtHA.getText().toString().isEmpty() && !edtNN.getText().toString().isEmpty()) {
                    SanPham s = new SanPham();// tạo đối tượng chứa dữ liệu người dùng nhập
                    s.setSOLUONG(Integer.parseInt(edtSL.getText().toString()));
                    s.setDONGIA(Double.parseDouble(edtGia.getText().toString()));
                    s.setHINHANH(Integer.parseInt(edtHA.getText().toString()));
                    s.setMASP(edtMa.getText().toString());
                    s.setTENSP(edtTen.getText().toString());
                    s.setNOIDUNG(edtND.getText().toString());
                    s.setNOINHAP(edtNN.getText().toString());
                    s.setPHANLOAI(spinnercate.getSelectedItem().toString());
                    int kq = qlSanPham.ThemSanPham(s);
                    if (kq == -1) {
                        Toast.makeText(QLSanPhamActivity.this, "Thêm thất bại", Toast.LENGTH_LONG).show();
                    }
                    if (kq == 1) {
                        Toast.makeText(QLSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                        //update listview
                        qlSanPham = new QLSanPham(QLSanPhamActivity.this);
                        list = qlSanPham.getAllSanPhamToString();
                        ArrayAdapter adapter = new ArrayAdapter(QLSanPhamActivity.this, android.R.layout.simple_list_item_1, list);
                        spListView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(QLSanPhamActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_LONG).show();
                }
            }
        });
        //Button Sửa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtMa.getText().toString().isEmpty() && !edtSL.getText().toString().isEmpty()
                        && !edtGia.getText().toString().isEmpty()) {
                    SanPham s = new SanPham();// tạo đối tượng chứa dữ liệu người dùng nhập
                    //đưa dữ liệu vào đối tượng
                    s.setMASP(edtMa.getText().toString());
                    s.setTENSP(edtTen.getText().toString());
                    s.setDONGIA(Double.parseDouble(edtGia.getText().toString()));
                    s.setSOLUONG(Integer.parseInt(edtSL.getText().toString()));
                    s.setNOIDUNG(edtND.getText().toString());
                    s.setNOINHAP(edtNN.getText().toString());
                    s.setPHANLOAI(spinnercate.getSelectedItem().toString());
                    s.setHINHANH(Integer.parseInt(edtHA.getText().toString()));
                    //gọi hàm Sửa
                    int kq = qlSanPham.SuaSanPham(s);
                    if (kq == -1) {
                        Toast.makeText(QLSanPhamActivity.this, "Sửa thất bại", Toast.LENGTH_LONG).show();
                    }
                    if (kq == 1) {
                        Toast.makeText(QLSanPhamActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                        //update listview
                        qlSanPham = new QLSanPham(QLSanPhamActivity.this);
                        list = qlSanPham.getAllSanPhamToString();
                        ArrayAdapter adapter = new ArrayAdapter(QLSanPhamActivity.this, android.R.layout.simple_list_item_1, list);
                        spListView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(QLSanPhamActivity.this, "Vui lòng nhập Mã SP, số lượng or đơn giá!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MASP = edtMa.getText().toString();
                if(MASP.equals("")){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đúng mã sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor cursor = dbHelper.GetData("Select* from SANPHAM where SANPHAM.MASP = '" + MASP + "'");
                if (!cursor.moveToFirst()) {
                    Toast.makeText(getApplicationContext(), "Sản Phẩm Không Tồn Tại", Toast.LENGTH_SHORT).show();
                } else {
                    cursor.moveToFirst();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(QLSanPhamActivity.this);
                    builder.setTitle("Xác nhận")
                            .setMessage("Thông tin sản phẩm:" +
                                    "\nMã sản phẩm: " + cursor.getString(0) +
                                    "\nTên sản phẩm: " + cursor.getString(1) +
                                    "\nPhân loại: " + cursor.getString(2) +
                                    "\nSố lượng: " + cursor.getString(3) +
                                    "\nNơi nhập: " + cursor.getString(4) +
                                    "\nNội dung: " + cursor.getString(5) +
                                    "\nGiá: " + cursor.getDouble(6) +
                                    "\nMã ảnh: " + cursor.getInt(7))
                            .setPositiveButton("Đồng ý xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //gọi hàm Xóa
                                    int kq = qlSanPham.XoaSanPham(MASP);
                                    if(kq==-1){
                                        Toast.makeText(QLSanPhamActivity.this,"Xóa thất bại",Toast.LENGTH_LONG).show();
                                    }
                                    if (kq==1){
                                        Toast.makeText(QLSanPhamActivity.this,"Xóa thành công",Toast.LENGTH_LONG).show();
                                        //Hiển thị lại danh sách các tài khoản
                                        qlSanPham = new QLSanPham(QLSanPhamActivity.this);
                                        list = qlSanPham.getAllSanPhamToString();
                                        ArrayAdapter adapter = new ArrayAdapter(QLSanPhamActivity.this, android.R.layout.simple_list_item_1, list);
                                        spListView.setAdapter(adapter);
                                    }
                                }
                            })
                            .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadmin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.KH:
                Intent KH = new Intent(QLSanPhamActivity.this, QLAccountActivity.class);
                startActivity(KH);
                return true;
            case R.id.QL:
                Intent QL = new Intent(QLSanPhamActivity.this, AdminActivity.class);
                startActivity(QL);
                return true;
            case R.id.SanPham:
                Intent HD = new Intent(QLSanPhamActivity.this, QLSanPhamActivity.class);
                startActivity(HD);
                return true;
            case R.id.Logout:
                Intent Logout = new Intent(QLSanPhamActivity.this, DangNhapActivity.class);
                startActivity(Logout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}