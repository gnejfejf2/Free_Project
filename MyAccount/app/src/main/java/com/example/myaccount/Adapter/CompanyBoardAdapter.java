package com.example.myaccount.Adapter;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.bumptech.glide.Glide;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.R;


public class CompanyBoardAdapter extends CursorAdapter {


    public CompanyBoardAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_companyboard, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView board_number=view.findViewById(R.id.companyboard_number);
        TextView titleText=view.findViewById(R.id.companyboard_title);
        TextView userID=view.findViewById(R.id.companyboard_userID);
        ImageView titleimage=view.findViewById(R.id.title_imageView);
        board_number.setText(cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry._ID)));
        titleText.setText("  가게명 : "+cursor.getString(cursor.getColumnIndexOrThrow(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_TITLE)));
        userID.setText("  주인장 ID  : "+cursor.getString(cursor.getColumnIndexOrThrow(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_COMPANYID)));


       ///////////////////////////////////그위치에있는스트링값 가져와서 글라이드 함수로 그려주기
        Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_PHOTOJSON)));
        Glide.with(context).load(uri).into(titleimage);
//////////////////////////////////////////////////////////////////////////////////////////////
        //2. 리스너 등록
    }//내가 설정한위치에 값을 셋팅

    ////추후에 수정해야함
}

