package com.example.myaccount.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cursoradapter.widget.CursorAdapter;

import com.bumptech.glide.Glide;
import com.example.myaccount.Contract.Shoppingbasketcontract;
import com.example.myaccount.Helper.ShoppingbasketDBHelper;
import com.example.myaccount.R;


public class ShoppingbasketAdapter extends CursorAdapter {

    int checkC[];


    public ShoppingbasketAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        checkC = new int[c.getCount()];
        for (int k = 0; k < c.getCount(); k++) {
            checkC[k] = 0;
        }

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shoppingbasket, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {

        TextView shoppingbasket_textView_sellid = view.findViewById(R.id.shoppingbasket_textView_sellid);
        TextView shoppingbasket_textView_content = view.findViewById(R.id.shoppingbasket_textView_content);
        TextView shoppingbasket_textView_price = view.findViewById(R.id.shoppingbasket_textView_price);

        ImageView shoppingbasket_imageView_titleimage = view.findViewById(R.id.shoppingbasket_imageView_titleimage);
        ImageView ImageView_upnum=view.findViewById(R.id.ImageView_upnum);
        ImageView ImageView_downnum=view.findViewById(R.id.ImageView_downnum);

        shoppingbasket_textView_sellid.setText("  판매자 : : " + cursor.getString(cursor.getColumnIndexOrThrow(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_SELLUSERID)));
        shoppingbasket_textView_content.setText("  상품명 : " + cursor.getString(cursor.getColumnIndexOrThrow(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_TITLE)));
        shoppingbasket_textView_price.setText("  상품 가격  : " + cursor.getString(cursor.getColumnIndexOrThrow(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_PRICE)));

        CheckBox checkBox = view.findViewById(R.id.shoppingbasket_ckeckbox);
        checkBox.setChecked((checkC[cursor.getPosition()]) == 0 ? false : true);
        final int position = cursor.getPosition();
        final int itemnumber=cursor.getInt(cursor.getColumnIndexOrThrow(Shoppingbasketcontract.Shoppingbasketentry._ID));

        ImageView_upnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView_itembuynumber=view.findViewById(R.id.textView_itembuynumber);
                final int number=Integer.valueOf(textView_itembuynumber.getText().toString());
                if(number>=10){
                    Toast.makeText(context,"최대 갯수 입니다.",Toast.LENGTH_SHORT).show();
                }else {
                    String plusnumber = String.valueOf(number + 1);
                    textView_itembuynumber.setText(plusnumber);
                    SQLiteDatabase itemshoppingdb= ShoppingbasketDBHelper.getInstance(context).getWritableDatabase();
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_BUYNUMBER,number + 1);
                    int dbString=itemnumber;
                    itemshoppingdb.update(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME, contentValues, Shoppingbasketcontract.Shoppingbasketentry._ID + "=" + dbString, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문

                }
            }
        });
        ImageView_downnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView_itembuynumber=view.findViewById(R.id.textView_itembuynumber);
                final int number=Integer.valueOf(textView_itembuynumber.getText().toString());
                if(number<=1){
                    Toast.makeText(context,"최저 갯수 입니다.",Toast.LENGTH_SHORT).show();
                }else {
                    String minusnumber = String.valueOf(number - 1);
                    textView_itembuynumber.setText(minusnumber);
                    SQLiteDatabase itemshoppingdb= ShoppingbasketDBHelper.getInstance(context).getWritableDatabase();
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_BUYNUMBER,number - 1);
                    int dbString=itemnumber;
                    itemshoppingdb.update(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME, contentValues, Shoppingbasketcontract.Shoppingbasketentry._ID + "=" + dbString, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문

                }
            }
        });


        final String aString = String.valueOf(position);
        checkBox.setTag(aString);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox c = (CheckBox) v.findViewById(R.id.shoppingbasket_ckeckbox);

                if (c.isChecked()) {
                    checkC[Integer.parseInt((String) v.getTag())] = 1;
                    SQLiteDatabase shoppingdb= ShoppingbasketDBHelper.getInstance(context).getWritableDatabase();
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_ISSELECTED,"true");
                    int dbString=itemnumber;
                    shoppingdb.update(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME, contentValues, Shoppingbasketcontract.Shoppingbasketentry._ID + "=" + dbString, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문




                } else {
                    checkC[Integer.parseInt((String) v.getTag())] = 0;
                    SQLiteDatabase shoppingdb = ShoppingbasketDBHelper.getInstance(context).getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    int dbString=itemnumber;
                    contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_ISSELECTED, false);
                    shoppingdb.update(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME, contentValues, Shoppingbasketcontract.Shoppingbasketentry._ID + "=" + dbString, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문
                }
            }
        });

        ///////////////////////////////////그위치에있는스트링값 가져와서 글라이드 함수로 그려주기
        Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_PHOTOJSON)));
        Glide.with(context).load(uri).into(shoppingbasket_imageView_titleimage);
//////////////////////////////////////////////////////////////////////////////////////////////
        //2. 리스너 등록

    }//내가 설정한위치에 값을 셋팅





}

