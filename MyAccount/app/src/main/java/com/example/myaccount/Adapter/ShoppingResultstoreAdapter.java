package com.example.myaccount.Adapter;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.ShoppingContract;
import com.example.myaccount.Contract.Shoppingresultcontract;
import com.example.myaccount.R;


public class ShoppingResultstoreAdapter extends CursorAdapter {


    public ShoppingResultstoreAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shoppingresult, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView title = view.findViewById(R.id.textView_title);
        TextView price = view.findViewById(R.id.textView_price);
        TextView number = view.findViewById(R.id.textView_itemnumber);


        int totalmoney=Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMPRICE)))*
                Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMPRICE)));



        title.setText(cursor.getString(cursor.getColumnIndexOrThrow(ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMTITLE)));
        price.setText("총금액 : " + totalmoney+"개별 금액 : "+ cursor.getString(cursor.getColumnIndexOrThrow(ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMTITLE)));
        number.setText("갯수 : "+cursor.getString(cursor.getColumnIndexOrThrow(ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMCOUNTER)));


        //2. 리스너 등록
    }//내가 설정한위치에 값을 셋팅


}

