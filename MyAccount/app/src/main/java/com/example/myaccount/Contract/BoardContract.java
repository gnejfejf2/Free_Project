package com.example.myaccount.Contract;
import android.provider.BaseColumns;

public class BoardContract {
    private BoardContract() {

    }
    public static class BoardEntry implements BaseColumns{
        public static final String TABLE_NAME="Board";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_CONTENTS="contents";
        public static final String COLUMN_NAME_USERID="id";
        public static final String COLUMN_NAME_DAY="day";
        public static final String COLUMN_NAME_BOARDNUM="boardnum";
        public static final String COLUMN_NAME_PHOTOJSON="photojson";
    }
}
