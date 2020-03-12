package com.example.myaccount.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.myaccount.Activity.MainActivity;
import com.example.myaccount.Activity.MemoActivity;
import com.example.myaccount.Adapter.CalendarAdapter;
import com.example.myaccount.Helper.ProfilDBHelper;
import com.example.myaccount.Contract.ProfilContract;
import com.example.myaccount.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyGridViewCalendar extends DialogFragment {

    private TextView tvCalendarTitle;
    private TextView tvSelectedDate;
    private GridView gvCalendar;
    private int resultrot;
    private ArrayList<DayInfo> arrayListDayInfo;
    Calendar mThisMonthCalendar;
    CalendarAdapter mCalendarAdapter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd(EEE)", Locale.getDefault());//데이터포멧지정
    private String selectQuery = "SELECT  * FROM " + ProfilContract.ProfilEntry.TABLE_NAME;
    Date selectedDate;//변수지정


    public void setSelectedDate(Date date, int memo) {
        selectedDate = date;
        resultrot = memo;
        if (mCalendarAdapter != null) {
            mCalendarAdapter.selectedDate = date;
        }//어뎁터안에 선택된 값이있다면그걸 데이터에 넣어준다
    }

    @Override//다이얼로그식으로 만들기위해 적혀있는것
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//다이얼로그 생성 현재 엑티비티에서
        LayoutInflater inflater = getActivity().getLayoutInflater();//동기적 getLayoutInflater메소드를 지원(레이아웃 인플레이터를 쉽게 이용가능) 프래그먼트에서 많이사용
        //LayoutInflater란 음 엑티비티단에서 setcontView 같은존재 레이아웃위에 새로운 것을 만들수있음
        View dialog = inflater.inflate(R.layout.grid_view_calendar, null, false);//저것을 가져올것이다.
        Button btnPreviousCalendar = dialog.findViewById(R.id.btn_previous_calendar);
        Button btnNextCalendar = dialog.findViewById(R.id.btn_next_calendar);

        tvCalendarTitle = dialog.findViewById(R.id.tv_calendar_title);
        tvSelectedDate = dialog.findViewById(R.id.tv_selected_date);
        gvCalendar = dialog.findViewById(R.id.gv_calendar);

        btnPreviousCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThisMonthCalendar.add(Calendar.MONTH, -1);

                getCalendar(mThisMonthCalendar.getTime());
            }
        });//달에서 한달을 뺀다.
        btnNextCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThisMonthCalendar.add(Calendar.MONTH, +1);

                getCalendar(mThisMonthCalendar.getTime());
            }
        });//달에서 한달을 추가하는것

        gvCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectedDate(((DayInfo) view.getTag()).getDate(), resultrot);

                tvSelectedDate.setText(sdf.format(mCalendarAdapter.selectedDate));

                mCalendarAdapter.notifyDataSetChanged();
            }
        });//날자를 클릭을 할수 있게하는것

        Button btnConfirm = dialog.findViewById(R.id.btn_confirm);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyGridViewCalendar.this.getDialog().cancel();
                if (resultrot == 1000) {
                    ((MemoActivity) getActivity()).selectedDate = selectedDate;
                    ((MemoActivity) getActivity()).mdayEditText.setText(sdf.format(selectedDate));
                }//선택된 데이터를 tvSelcetedDate에 넣는다.



                else if (resultrot == 2000) {//메인화면에서 들어올시 이코드가 실행됨
                    String wantday = removeStringNumber(sdf.format(selectedDate));//데이터를 숫자만 남기고정규식을이용
                    int year = Integer.parseInt(wantday.substring(0, 4));//년도추출
                    int month = Integer.parseInt(wantday.substring(4, 6));//달추출
                    int day = Integer.parseInt(wantday.substring(6));//월추출
                    String dday = "♥"+String.valueOf(countdday(year, month, day))+"일♥";//오픈코드 이용 D-day 구하기
                    ((MainActivity) getActivity()).TextView_Dday.setText(dday);//선택된 데이터를 tvSelcetedDate에 넣는다.


                    SQLiteDatabase db = ProfilDBHelper.getInstance(((MainActivity) getActivity()).getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언


                    ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하는값을 집어넣는다
                    contentValues.put(ProfilContract.ProfilEntry.COLUMN_NAME_DAY, dday);

                    //특정위치 확인하시고!
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    cursor.moveToFirst();
                    db.update(ProfilContract.ProfilEntry.TABLE_NAME, contentValues, ProfilContract.ProfilEntry._ID + "=" + 1, null);
                    Log.d("왜이거지", "안녕");


                }
            }
        });//확인

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyGridViewCalendar.this.getDialog().cancel();
            }
        });//캔슬버튼

        arrayListDayInfo = new ArrayList<>();//새로운 어레이리스트 생성

        builder.setView(dialog);//다이얼로그에 셋한다
        return builder.create();//
    }

    @Override
    public void onResume() {
        super.onResume();

        mThisMonthCalendar = Calendar.getInstance();
        getCalendar(mThisMonthCalendar.getTime());
    }//인스텐스를가져와서 캘린더에 넣어준다

    private void getCalendar(Date dateForCurrentMonth) {
        int dayOfWeek;
        int thisMonthLastDay;

        arrayListDayInfo.clear();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForCurrentMonth);//현재 달을 가져와서 출력

        calendar.set(Calendar.DATE, 1);//1일로 변경
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//1일의 요일 구하기
        Log.d("CalendarTest", "dayOfWeek = " + dayOfWeek + "");

        if (dayOfWeek == Calendar.SUNDAY) { //현재 달의 1일이 무슨 요일인지 검사
            dayOfWeek += 7;
        }

        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        setCalendarTitle();

        DayInfo day;

        calendar.add(Calendar.DATE, -1 * (dayOfWeek - 1)); //현재 달력화면에서 보이는 지난달의 시작일
        for (int i = 0; i < dayOfWeek - 1; i++) {
            day = new DayInfo();
            day.setDate(calendar.getTime());
            day.setInMonth(false);
            arrayListDayInfo.add(day);

            calendar.add(Calendar.DATE, +1);
        }

        for (int i = 1; i <= thisMonthLastDay; i++) {
            day = new DayInfo();
            day.setDate(calendar.getTime());
            day.setInMonth(true);
            arrayListDayInfo.add(day);

            calendar.add(Calendar.DATE, +1);
        }

        for (int i = 1; i < 42 - (thisMonthLastDay + dayOfWeek - 1) + 1; i++) {
            day = new DayInfo();
            day.setDate(calendar.getTime());
            day.setInMonth(false);
            arrayListDayInfo.add(day);

            calendar.add(Calendar.DATE, +1);
        }

        mCalendarAdapter = new CalendarAdapter(arrayListDayInfo, selectedDate);
        gvCalendar.setAdapter(mCalendarAdapter);

        tvSelectedDate.setText(sdf.format(selectedDate));
        //이번달이아닌 달력에남는공간에 전달과 그다음달의 날자를 표시해주기위해
    }

    private void setCalendarTitle() {
        StringBuilder sb = new StringBuilder();

        sb.append(mThisMonthCalendar.get(Calendar.YEAR))
                .append("년 ")
                .append((mThisMonthCalendar.get(Calendar.MONTH) + 1))
                .append("월");
        tvCalendarTitle.setText(sb.toString());
    }

    public int countdday(int myear, int mmonth, int mday) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar todaCal = Calendar.getInstance(); //오늘날자 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날자를 가져와 변경시킴

            mmonth -= 1;// 받아온날자에서 -1을 해줘야함// .
            mday -= 2;
            ddayCal.set(myear, mmonth, mday);// D-dayd의 날짜를 입력
            Log.e("테스트", simpleDateFormat.format(todaCal.getTime()) + "");
            Log.e("테스트", simpleDateFormat.format(ddayCal.getTime()) + "");

            long today = todaCal.getTimeInMillis() / 86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            long dday = ddayCal.getTimeInMillis() / 86400000;


            long count = today - dday; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int) count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static String removeStringNumber(String str) {
        return str.replaceAll("[^0-9]", "");
    }//정규식이용 스트링에서 숫자만 남기기

}
