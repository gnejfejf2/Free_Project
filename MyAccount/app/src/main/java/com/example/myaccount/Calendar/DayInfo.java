package com.example.myaccount.Calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayInfo {
    private boolean inMonth;
    private Date date;


    public String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
        return sdf.format(date);
    }

    public boolean isInMonth() {
        return inMonth;
    }
// 선택한 달이 보여주기위해 트루펄스로 해당하는 달만 트루폴스로 출력
    public void setInMonth(boolean inMonth) {
        this.inMonth = inMonth;
    }
//트루면 돌려주기위해 그달의값을
    public Date getDate(){
        return date;
    }
//데이터가져오기
    public void setDate(Date date){
        this.date = date;
    }
//데이터 세팅하기
    public boolean isSameDay(Date date1){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(this.date);

        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

        return sameDay;
    }
    //달력에 연도와 달의 위치를 찾기위해 추가적인 공부 필요
}


