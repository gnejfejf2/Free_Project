package com.example.myaccount.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.example.myaccount.Contract.DdayContract;
import com.example.myaccount.R;


public class DdayAdapter extends CursorAdapter {


    public DdayAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView titleText=view.findViewById(R.id.id_listitem);
        TextView contentText=view.findViewById(R.id.comment_listitem);
        TextView dayText=view.findViewById(R.id.day_listitem);
        titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DdayContract.MemoEntry.COLUMN_NAME_TITLE)));
        contentText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DdayContract.MemoEntry.COLUMN_NAME_CONTENTS)));
        dayText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DdayContract.MemoEntry.COLUMN_NAME_DAY)));


    }//내가 설정한위치에 값을 셋팅


}

