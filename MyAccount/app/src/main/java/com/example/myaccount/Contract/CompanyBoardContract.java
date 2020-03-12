package com.example.myaccount.Contract;
import android.provider.BaseColumns;

public class CompanyBoardContract {
    private CompanyBoardContract() {

    }
    public static class ComponyBoardEntry implements BaseColumns{
        public static final String TABLE_NAME="ComponyBoard";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_CONTENTS="contents";
        public static final String COLUMN_NAME_COMPANYID="companyid";
        public static final String COLUMN_NAME_DAY="day";
        public static final String COLUMN_NAME_PHOTOJSON="photojson";
        public static final String COLUMN_NAME_ITEMLIST="itemlist";
        public static final String COLUMN_NAME_LATITUDE="latitude";
        public static final String COLUMN_NAME_LONGITUDE="longitude";
        public static final String COLUMN_NAME_STREETID="streetid";
    }
}
