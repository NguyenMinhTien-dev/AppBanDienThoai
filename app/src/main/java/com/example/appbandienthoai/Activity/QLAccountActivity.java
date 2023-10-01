package com.example.appbandienthoai.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appbandienthoai.Class.Account;
import com.example.appbandienthoai.Class.QLAccount;
import com.example.appbandienthoai.Database;
import com.example.appbandienthoai.R;

import java.util.ArrayList;
import java.util.List;

public class QLAccountActivity extends AppCompatActivity {
android.widget.ListView ListView;
    Toolbar mToolBar;
    EditText edtTK, edtMK, edtTenAC, edtSDT, edtGM, edtADR;
    String edtRole;
    Button btnXoa,btnSua,btnThem;
    QLAccount qlAccount;
    Database db;
    Spinner spinnerRole;
    List<String> list = new ArrayList<>();
    ArrayAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlaccount);
        AnhXa();
        //hiển thị danh sách tài khoản hiện có
        list = qlAccount.getAllAccountToString();
        adapter = new ArrayAdapter(QLAccountActivity.this, android.R.layout.simple_list_item_1,list);
        ListView.setAdapter(adapter);

        //SET ONCLICKITEMLISTENER CỦA LISTVIEW ACCOUNT
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Lấy ra tài khoản từ một chuỗi dài trong ListView
                String getTaikhoan = list.get(position).split("\n")[0].split(" ")[2].trim();
                Cursor cursor = db.GetData("Select* from ACCOUNT where ACCOUNT.TAIKHOAN = '"+ getTaikhoan +"'");
                cursor.moveToFirst();
                edtTK.setText(cursor.getString(0));
                edtMK.setText(cursor.getString(1));
                spinnerRole.setSelection(cursor.getString(2).equals("admin") ? 0: 1);
                edtTenAC.setText(cursor.getString(3));
                edtSDT.setText(cursor.getString(4));
                edtGM.setText(cursor.getString(5));
                edtADR.setText(cursor.getString(6));
                return;
            }
        });

        //button thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNull() == false) return;
                String gmail = edtGM.getText().toString();
                if (!gmail.equals("")){
                    boolean check = false;
                    for (int i = 0; i < gmail.length(); i++){
                        if (gmail.charAt(i) == '@'){
                            check = true;
                        }
                    }
                    if (!check){
                        Toast.makeText(getApplicationContext(), "Gmail phải có '@', sai định dạng!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                boolean check =  db.AddAccount(edtTK.getText().toString()
                        , edtMK.getText().toString()
                        , edtRole
                        , edtTenAC.getText().toString()
                        , edtSDT.getText().toString()
                        , edtGM.getText().toString()
                        , edtADR.getText().toString());
                if (check){
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    list = qlAccount.getAllAccountToString();
                    adapter = new ArrayAdapter(QLAccountActivity.this, android.R.layout.simple_list_item_1,list);
                    ListView.setAdapter(adapter);
                }
                else
                    Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        //Button Xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TAIKHOAN = edtTK.getText().toString();
                if (TAIKHOAN.equals("")){
                    Toast.makeText(QLAccountActivity.this, "Nhập tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor cursor = db.GetData("Select* from ACCOUNT where ACCOUNT.TAIKHOAN = '"+ TAIKHOAN +"'");
                if (cursor == null){
                    Toast.makeText(getApplicationContext(), "Not Exist Account", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    cursor.moveToFirst();
                    AlertDialog.Builder builder = new AlertDialog.Builder(QLAccountActivity.this);
                    builder.setTitle("Xác nhận")
                            .setMessage("Thông tin tài khoản:" +
                                    "\nTài khoản: " + cursor.getString(0) +
                                    "\nMật khẩu: " + cursor.getString(1) +
                                    "\nQuyền hạn: " + cursor.getString(2) +
                                    "\nTên: " + cursor.getString(3) +
                                    "\nSố điện thoại: " + cursor.getString(4) +
                                    "\nGmail: " + cursor.getString(5) +
                                    "\nĐịa chỉ: " + cursor.getString(6))
                            .setPositiveButton("Đồng ý xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int kq = qlAccount.XoaAccount(TAIKHOAN);
                                    if(kq==-1){
                                        Toast.makeText(QLAccountActivity.this,"Xóa thất bại",Toast.LENGTH_LONG).show();
                                    }
                                    if (kq==1){
                                        Toast.makeText(QLAccountActivity.this,"Xóa thành công",Toast.LENGTH_LONG).show();
                                        //Hiển thị lại danh sách các tài khoản
                                        qlAccount = new QLAccount(QLAccountActivity.this);
                                        list = qlAccount.getAllAccountToString();
                                        ArrayAdapter adapter =new ArrayAdapter(QLAccountActivity.this, android.R.layout.simple_list_item_1,list);
                                        ListView.setAdapter(adapter);
                                        edtADR.setText("");
                                        edtTK.setText("");
                                        edtMK.setText("");
                                        edtTenAC.setText("");
                                        edtSDT.setText("");
                                        edtGM.setText("");
                                        edtTK.requestFocus();
                                    }
                                }
                            })
                            .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        //Button Sửa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNull() == false) return;
                String gmail = edtGM.getText().toString();
                if (!gmail.equals("")){
                    boolean check = false;
                    for (int i = 0; i < gmail.length(); i++){
                        if (gmail.charAt(i) == '@'){
                            check = true;
                        }
                    }
                    if (!check){
                        Toast.makeText(getApplicationContext(), "Gmail phải có '@', sai định dạng!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Account s = new Account();// tạo đối tượng chứa dữ liệu người dùng nhập
                //đưa dữ liệu vào đối tượng
                s.setTAIKHOAN(edtTK.getText().toString());
                s.setMATKHAU(edtMK.getText().toString());
                s.setQUYENHAN(edtRole);
                s.setSDT(edtSDT.getText().toString());
                s.setTEN(edtTenAC.getText().toString());
                s.setGMAIL(edtGM.getText().toString());
                s.setDIACHI(edtADR.getText().toString());
                //gọi hàm Sửa
                int kq = qlAccount.SuaAccount(s);
                if(kq==-1){
                    Toast.makeText(QLAccountActivity.this,"Sửa thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLAccountActivity.this,"Sửa thành công",Toast.LENGTH_LONG).show();
                    //Hiển thị lại danh sách các tài khoản
                    qlAccount = new QLAccount(QLAccountActivity.this);
                    list=qlAccount.getAllAccountToString();
                    ArrayAdapter adapter =new ArrayAdapter(QLAccountActivity.this, android.R.layout.simple_list_item_1,list);
                    ListView.setAdapter(adapter);
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
                Intent KH = new Intent(QLAccountActivity.this, QLAccountActivity.class);
                startActivity(KH);
                return true;
            case R.id.QL:
                Intent QL = new Intent(QLAccountActivity.this, AdminActivity.class);
                startActivity(QL);
                return true;
            case R.id.SanPham:
                Intent HD = new Intent(QLAccountActivity.this, QLSanPhamActivity.class);
                startActivity(HD);
                return true;
            case R.id.Logout:
                Intent Logout = new Intent(QLAccountActivity.this, MainActivity.class);
                startActivity(Logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean checkNull(){
        if (edtRole.equals("admin")){
            if (edtTK.getText().toString().equals("")
                    || edtMK.getText().toString().equals("")
                    || edtTenAC.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Username, password, name are required for admin", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            if (edtTK.getText().toString().equals("")
                    || edtMK.getText().toString().equals("")
                    || edtADR.getText().toString().equals("")
                    || edtTenAC.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Username, password, name are required for customer", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (!edtRole.equals("admin")){
            //region Kiểm tra chuỗi sdt có phải số không

            String sdt = edtSDT.getText().toString();
            String[] token = sdt.split("\\.");

            //Kiểm tra độ dài chuỗi sđt
            int checkLength = 0;
            for (int i = 0; i < token.length; i++){
                checkLength += token[i].length();
            }
            if (checkLength != 10){
                Toast.makeText(getApplicationContext(), "Phone number must have 10 numbers.", Toast.LENGTH_SHORT).show();
                return false;
            }

            //Kiểm tra chuỗi sdt có phải số không
            for (int i = 0; i < token.length; i++){
                if (!isNumber(token[i])){
                    Toast.makeText(getApplicationContext(), "Phone number is invalid.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            edtSDT.setText(toPhoneNumber(token));
            //endregion
        }
        return true;
    }
    private boolean isNumber(String x){
        return x.chars().allMatch( Character::isDigit );
    }
    private String toPhoneNumber(String[] x){
        String originalString = "";
        for (int i = 0 ; i < x.length; i++)
            originalString += x[i];
        String phoneNumber = new String();
        for (int i = 0; i < originalString.length(); i++){
            phoneNumber += originalString.charAt(i);
            if (i == 1 || i == 5)
                phoneNumber += ".";
        }
        return phoneNumber;
    }
    private void AnhXa(){
        qlAccount = new QLAccount(QLAccountActivity.this);
        db = new Database(this, "DBDienThoai.sqlite", null, 1);
        edtTK = (EditText) findViewById(R.id.edtTK);
        edtMK= (EditText) findViewById(R.id.edtMK);
        spinnerRole = findViewById(R.id.spinnerRole);
        // Tạo dữ liệu cho Spinner
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = db.GetData("Select NOIDUNG from [ROLE]");
        while (cursor.moveToNext()){
            data.add(cursor.getString(0));
        }
        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data);
        // Đặt giao diện của Spinner

        spinnerRole.setAdapter(adapter);
        // Xử lý sự kiện khi một mục được chọn
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi một mục được chọn

                Cursor cursor =  db.GetData("select* from [ROLE]");
                while (cursor.moveToNext()){
                    if (data.get(position).equals(cursor.getString(1))){
                        edtRole = cursor.getString(0);break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có mục nào được chọn
            }
        });
        edtTenAC= (EditText) findViewById(R.id.edtTentk);
        edtSDT= (EditText) findViewById(R.id.edtSDT);
        edtGM= (EditText) findViewById(R.id.edtGmail);
        edtADR= (EditText) findViewById(R.id.edtAddress);
        btnXoa=(Button)findViewById(R.id.btnXoa);
        btnSua=(Button)findViewById(R.id.btnSua);
        btnThem=(Button)findViewById(R.id.btnThem);
        ListView = (ListView) findViewById(R.id.listAC);
        mToolBar =(Toolbar) findViewById(R.id.toolbarAC);
        setSupportActionBar(mToolBar);
    }
}