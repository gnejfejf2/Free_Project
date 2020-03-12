package com.example.myaccount.Contract;
import android.provider.BaseColumns;

public class EntrepreneurContract {
    private EntrepreneurContract() {

    }
    public static class EntrepreneurEntry implements BaseColumns{
        public static final String TABLE_NAME="EntrepreneurBoard";
        public static final String COLUMN_NAME_ENTREPRENEURID="entrepreneurid";
        public static final String COLUMN_NAME_ENTREPRENEURPLACE="entrepreneurplace";
        public static final String COLUMN_NAME_ENTREPRENEURNUMBER="entrepreneurnumber";
        public static final String COLUMN_NAME_ENTREPRENEURBOARD="entrepreneurboard";
    }
}
