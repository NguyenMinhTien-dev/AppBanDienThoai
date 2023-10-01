package com.example.appbandienthoai.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appbandienthoai.R;

public class AdminActivity extends AppCompatActivity {
    Toolbar mToolBar;
    ImageView khButton,spButton,hdButton,vchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        khButton =  findViewById(R.id.btnKH);
        spButton =  findViewById(R.id.btnSP);
        hdButton =  findViewById(R.id.btnHD);
        vchButton =  findViewById(R.id.btnVC);
        mToolBar = (Toolbar) findViewById(R.id.toolbarSP);
        setSupportActionBar(mToolBar);
        khButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this,QLAccountActivity.class);
                startActivity(i);
            }
        });
        spButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, QLSanPhamActivity.class);
                startActivity(i);
            }
        });
        hdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, QLHoaDonActivity.class);
                startActivity(i);
            }
        });
        vchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, QLVoucherActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadmin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case R.id.KH:
                Intent KH = new Intent(AdminActivity.this, QLAccountActivity.class);
                startActivity(KH);
                return true;
            case R.id.QL:
                Intent QL = new Intent(AdminActivity.this, AdminActivity.class);
                startActivity(QL);
                return true;
            case R.id.SanPham:
                Intent HD = new Intent(AdminActivity.this, QLSanPhamActivity.class);
                startActivity(HD);
                return true;

            case R.id.Logout:
                Intent Logout = new Intent(AdminActivity.this, DangNhapActivity.class);
                startActivity(Logout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}