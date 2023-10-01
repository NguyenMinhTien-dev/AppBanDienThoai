package com.example.appbandienthoai.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbandienthoai.R;

import java.util.ArrayList;

public class PickImageAdapter extends BaseAdapter {
    Context context;
    int idLayout;
    ArrayList<String> listImg;
    public PickImageAdapter(@NonNull Context context, int idLayout, ArrayList<String> listImg) {
        this.context = context;
        this.idLayout = idLayout;
        this.listImg = listImg;
    }
    @Override
    public int getCount() {
        return listImg.size();
    }

    @Override
    public Object getItem(int position) {
        return listImg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_pick_image, parent, false);
        } else {
            view = convertView;
        }
        ImageView imageView = view.findViewById(R.id.imgPickimg);
        TextView textView = view.findViewById(R.id.tvnameImg);
        Resources res = context.getResources();
        int resID = res.getIdentifier(listImg.get(position), "drawable", context.getPackageName());
        imageView.setImageResource(resID);
        textView.setText(listImg.get(position));
        return view;
    }
}
