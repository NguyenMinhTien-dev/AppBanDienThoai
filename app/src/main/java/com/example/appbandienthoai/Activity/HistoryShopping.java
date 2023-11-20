package com.example.appbandienthoai.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.example.appbandienthoai.Adapter.HistoryShoppingAdapter;
import com.example.appbandienthoai.Class.HSProduct;
import com.example.appbandienthoai.Class.StatusLogin;
import com.example.appbandienthoai.Database;
import com.example.appbandienthoai.R;

import java.util.ArrayList;

public class HistoryShopping extends AppCompatActivity {
    RecyclerView lvHS;
    ArrayList<HSProduct> hsProducts;
    HistoryShoppingAdapter historyShoppingAdapter;
    StatusLogin statusLogin;
    Database databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_shopping);
        AnhXa();
        SetData();
    }
    public void AnhXa(){
        lvHS = findViewById(R.id.lvHS);
        databaseHelper = new Database(this, "DBDienThoai.sqlite", null, 1);
        statusLogin = (StatusLogin) getApplication();
        hsProducts = new ArrayList<>();

    }
    public void SetData(){
        Cursor cursor = databaseHelper.GetData("Select BILLDETAIL.IDVoucher, BILLDETAIL.MASP, BILLDETAIL.UNITPRICE, BILL.DATEORDER, BILLDETAIL.QUANTITY " +
                "From BILL, BILLDETAIL " +
                "WHERE BILL.ID = BILLDETAIL.IDORDER " +
                "AND BILL.TAIKHOANCUS = '"+statusLogin.getUser()+"'");
        if (hsProducts != null){
            hsProducts.removeAll(hsProducts);
        }
        while (cursor.moveToNext()){
            HSProduct hsProduct = new HSProduct();
            hsProduct.setMaSP(cursor.getString(1));
            hsProduct.setQuantity(cursor.getInt(4));
            hsProduct.setIdVoucher(cursor.getString(0));
            hsProduct.setDate(cursor.getString(3));
            hsProduct.setUnitPrice(cursor.getDouble(2));
            hsProducts.add(hsProduct);
        }
        historyShoppingAdapter = new HistoryShoppingAdapter(HistoryShopping.this, hsProducts);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        lvHS.setAdapter(historyShoppingAdapter);
        lvHS.setLayoutManager(layoutManager);

    }
}