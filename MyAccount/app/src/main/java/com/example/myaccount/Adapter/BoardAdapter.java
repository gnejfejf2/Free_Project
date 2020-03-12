package com.example.myaccount.Adapter;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.R;


public class BoardAdapter extends CursorAdapter {


    public BoardAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_board, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView board_number=view.findViewById(R.id.board_number);
        TextView titleText=view.findViewById(R.id.board_title);
        TextView userID=view.findViewById(R.id.board_userID);
        TextView dayText=view.findViewById(R.id.board_day);
        board_number.setText(cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry._ID)));
        titleText.setText("제목 : "+cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_TITLE)));
        userID.setText("유저명 : "+cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_USERID)));
        dayText.setText("작성일 : "+cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_DAY)));//수정해야함


         //2. 리스너 등록
    }//내가 설정한위치에 값을 셋팅


}

