package com.example.barmsss;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */
//Gllobal. String encoded="";
public class ClothesList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Clothes> list;
    ClothesListAdapter adapter = null;
    public static String encoded;
    public static String type;
    SQLiteHelper OpenHelper = new SQLiteHelper(this, "CLOTHESDB.sqlite", null, 1);
    SQLiteHelper OpenHelper2 = new SQLiteHelper(this, "CLOTHESDB.sqlite", null, 1);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothes_list_activity);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new ClothesListAdapter(this, R.layout.clothes_items, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
//        SQLiteHelper myOpenHelper = new SQLiteHelper(SQliteHelper);
//        sQLite db = myOpenHelper.getReadableDatabase();
        Cursor cursor =OpenHelper.getData("SELECT * FROM CLOTHES");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
//            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            list.add(new Clothes(name, type, image, id));

        }
        adapter.notifyDataSetChanged();
        //  }
//}
        //m awl hna ll a5r bytshal w uncomment el } li fo2
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(ClothesList.this);
                Cursor c = OpenHelper.getData("SELECT * FROM CLOTHES");
                int temp=0;

                while (c.moveToNext()) {
                    int idd = c.getInt(0);
                    if (idd == position+1) {
//            int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = c.getString(1);
                        type = c.getString(2);
                        byte[] image = c.getBlob(3);
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                        encoded = Base64.encodeToString(image, Base64.DEFAULT);
                        Toast.makeText(getApplicationContext(), "item selected successfully :)", Toast.LENGTH_SHORT).show();
                        temp = 1;
                        break;
                    }
                }
                if (temp==0)
                {
                    Toast.makeText(getApplicationContext(), "item not selected :(", Toast.LENGTH_SHORT).show();
                }

//                if (encoded == "") {
//                    Toast.makeText(getApplicationContext(), "failed!!!", Toast.LENGTH_SHORT).show();
//                }
                return true;
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
//            Uri uri = data.getData();
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(uri);
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                imageViewClothes.setImageBitmap(bitmap);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
    }
}
