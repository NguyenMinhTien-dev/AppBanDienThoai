package com.example.appbandienthoai.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbandienthoai.Class.HSProduct;
import com.example.appbandienthoai.Database;
import com.example.appbandienthoai.R;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HistoryShoppingAdapter extends RecyclerView.Adapter<HistoryShoppingAdapter.MyViewHolder>{
    Context context;
    ArrayList<HSProduct> hsProducts;
    Database databaseHelper;

    public HistoryShoppingAdapter(Context context, ArrayList<HSProduct> hsProducts) {
        this.context = context;
        this.hsProducts = hsProducts;
        this.databaseHelper = new Database(context, "DBDienThoai.sqlite", null, 1);
        ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history_shopping, parent,false);
        return new HistoryShoppingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HSProduct hsProduct = hsProducts.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Cursor cursor = databaseHelper.GetData("Select GIAM from VOUCHER where MAVOUCHER = '"+hsProduct.getIdVoucher()+"'");
        Cursor cursor1 = databaseHelper.GetData("Select* from SANPHAM where MASP = '"+ hsProduct.getMaSP()+"'");
        cursor1.moveToFirst();
        String total;
        if (cursor.moveToFirst()){
            holder.HS_oldPrice.setText("đ"+ decimalFormat.format(hsProduct.getUnitPrice()));
            holder.HS_newPrice.setText("đ"+ decimalFormat.format(hsProduct.getUnitPrice()*(1 - cursor.getDouble(0))));
            total = decimalFormat.format((hsProduct.getUnitPrice()*(1 - cursor.getDouble(0))* hsProduct.getQuantity()));
        } else {
            holder.HS_oldPrice.setText("đ"+ decimalFormat.format(hsProduct.getUnitPrice()));
            holder.HS_newPrice.setText("đ"+ decimalFormat.format(hsProduct.getUnitPrice()));
            total = decimalFormat.format(hsProduct.getUnitPrice()* hsProduct.getQuantity());
        }
        holder.HS_oldPrice.setPaintFlags(holder.HS_oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate inputDate = LocalDate.parse(hsProduct.getDate(), inputFormat);
        String outputDate = inputDate.format(outputFormat);
        holder.HS_Date.setText("Ngày: "  + outputDate);
        holder.HS_SL.setText("x" + hsProduct.getQuantity());
        holder.HS_img.setImageResource(Integer.parseInt(cursor1.getString(7)));
        holder.HS_Name.setText(cursor1.getString(1));
        holder.HS_Total.setText("Thành tiền: đ" + total);
    }

    @Override
    public int getItemCount() {
        return hsProducts.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView HS_img;
        TextView HS_Date, HS_Name, HS_SL, HS_newPrice, HS_oldPrice, HS_Total;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            HS_img = itemView.findViewById(R.id.HS_img);
            HS_Name = itemView.findViewById(R.id.HS_name);
            HS_SL = itemView.findViewById(R.id.HS_SL);
            HS_newPrice = itemView.findViewById(R.id.HS_newPrice);
            HS_oldPrice = itemView.findViewById(R.id.HS_oldPrice);
            HS_Total = itemView.findViewById(R.id.HS_Total);
            HS_Date = itemView.findViewById(R.id.HS_Date);
        }
    }
}
