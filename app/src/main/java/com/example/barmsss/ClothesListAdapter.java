package com.example.barmsss;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class ClothesListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Clothes> clothesList;

    public ClothesListAdapter(Context context, int layout, ArrayList<Clothes> clothesList) {
        this.context = context;
        this.layout = layout;
        this.clothesList = clothesList;
    }

    @Override
    public int getCount() {
        return clothesList.size();
    }

    @Override
    public Object getItem(int position) {
        return clothesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtType;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtType = (TextView) row.findViewById(R.id.txtType);
            holder.imageView = (ImageView) row.findViewById(R.id.imgClothes);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Clothes clothes = clothesList.get(position);

        holder.txtName.setText(clothes.getName());
        holder.txtType.setText(clothes.getType());

        byte[] clothesImage = clothes.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(clothesImage, 0, clothesImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}

