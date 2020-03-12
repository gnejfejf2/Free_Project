package com.example.myaccount.Adapter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myaccount.Calendar.DayInfo;
import com.example.myaccount.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends BaseAdapter {
    private ArrayList<DayInfo> arrayListDayInfo;
    public Date selectedDate;
//셀렉데이터라는 데이터타입 만들기

    public CalendarAdapter(ArrayList<DayInfo> arrayLIstDayInfo, Date date) {
        this.arrayListDayInfo = arrayLIstDayInfo;
        this.selectedDate = date;
    }//추가공부

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayListDayInfo.size();
    }//크기를 리턴함

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayListDayInfo.get(position);
    }//그위치에있는 값을 리턴함

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DayInfo day = arrayListDayInfo.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.day, parent, false);
        }//아무런값이 없을시 기본 셋팅

        if(day != null){
            TextView tvDay = convertView.findViewById(R.id.day_cell_tv_day);
            tvDay.setText(day.getDay());

            ImageView ivSelected = convertView.findViewById(R.id.iv_selected);//선택되었을시 그 달력을노란색으로 바꿔주는 공간 셀렉데이터라면 색상을 바꿔준다
            if(day.isSameDay(selectedDate)){
                ivSelected.setVisibility(View.VISIBLE);
            }else{
                ivSelected.setVisibility(View.INVISIBLE);
            }

            if(day.isInMonth()){//달에있는 붉은날을 표시해주기위해 주말은 레드
                if((position%7 + 1) == Calendar.SUNDAY){
                    tvDay.setTextColor(Color.RED);
                }else if((position%7 + 1) == Calendar.SATURDAY){
                    tvDay.setTextColor(Color.BLUE);
                }else{
                    tvDay.setTextColor(Color.BLACK);
                }
            }else{
                tvDay.setTextColor(Color.GRAY);
            }
        }
        convertView.setTag(day);

        return convertView;//컨벌트뷰란 파트장님이말씀하신 재사용하는뷰
    }

}