package com.example.myaccount.Contract;

import android.provider.BaseColumns;

public class DdayContract {
    public static class MemoEntry implements BaseColumns {
        public static final String TABLE_NAME = "newmemo";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENTS = "contents";
        public static final String COLUMN_NAME_DAY = "day";

    }
}
